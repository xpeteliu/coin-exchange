package io.github.xpeteliu.service;

import io.github.xpeteliu.dto.UserDto;
import io.github.xpeteliu.entity.User;
import io.github.xpeteliu.entity.UserAuthAuditRecord;
import io.github.xpeteliu.mapper.UserDtoMapper;
import io.github.xpeteliu.model.AuthAccountParam;
import io.github.xpeteliu.model.UpdatePasswordParam;
import io.github.xpeteliu.model.UpdatePhoneParam;
import io.github.xpeteliu.repository.UserRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAuthAuditRecordService userAuthAuditRecordService;

    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        Byte seniorAuthStatus = null;
        String seniorAuthDesc = "";
        Integer reviewsStatus = user.getReviewsStatus();
        if (reviewsStatus == null) {
            seniorAuthStatus = 3;
            seniorAuthDesc = "Documents not submitted";
        } else {
            seniorAuthStatus = reviewsStatus.byteValue();
            switch (reviewsStatus) {
                case 0:
                    seniorAuthDesc = "Waiting for the authentication to be completed";
                    break;
                case 1:
                    seniorAuthDesc = "Senior authentication passed";
                    break;
                case 2:
                    seniorAuthDesc = "Senior authentication passed";
                    List<UserAuthAuditRecord> authAuditRecords = userAuthAuditRecordService.findAllById(user.getId());
                    if (!CollectionUtils.isEmpty(authAuditRecords)) {
                        UserAuthAuditRecord latestRecord = authAuditRecords.get(0);
                        seniorAuthDesc = latestRecord.getRemark();
                    }
                    break;
            }
        }
        user.setSeniorAuthStatus(seniorAuthStatus);
        user.setSeniorAuthDesc(seniorAuthDesc);
        return user;
    }

    public Page<User> findWithPaging(String mobile, String username, String realName, Long userId, Byte status, Integer reviewStatus, int page, int size) {
        List<User> resultList = userRepository.findWithPaging(mobile, username, realName, userId, status, reviewStatus, page * size, size);
        Long cnt = userRepository.countQualified(mobile, username, realName, userId, status, reviewStatus);
        PageRequest request = PageRequest.of(page, size, Sort.Direction.DESC, "lastUpdateTime");
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        User target = userRepository.findById(user.getId()).get();
        SupplementaryUtils.copyBeanIgnoringNullProps(user, target);
        userRepository.save(target);
    }

    public void updateStatus(Long id, Integer status) {
        userRepository.updateStatusById(id, status);
    }

    public void updateReviewStatus(Long id, Integer reviewStatus) {
        userRepository.updateReviewStatusById(id, reviewStatus);
    }

    public Page<User> findInviteeWithPaging(Long userId, int page, int size) {
        List<User> resultList = userRepository.findInviteeWithPaging(userId, page * size, size);
        Long cnt = userRepository.countInvitee(userId);
        PageRequest request = PageRequest.of(page, size, Sort.Direction.DESC, "lastUpdateTime");
        return new PageImpl<>(resultList, request, cnt);
    }

    @Transactional
    public void updateAuthStatus(Long id, Byte authStatus, Long authCode, String remark) {
        User user = findById(id);
        user.setReviewsStatus(authStatus.intValue());
        saveUser(user);

        UserAuthAuditRecord userAuthAuditRecord = new UserAuthAuditRecord();
        userAuthAuditRecord.setUserId(id);
        userAuthAuditRecord.setStatus(authStatus);
        userAuthAuditRecord.setAuthCode(authCode);
        userAuthAuditRecord.setRemark(remark);
        String auditorIdStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userAuthAuditRecord.setAuditUserId(Long.valueOf(auditorIdStr));
        userAuthAuditRecordService.saveUserAuthAuditRecord(userAuthAuditRecord);
    }


    public boolean verifyAndUpdateAuthInfo(AuthAccountParam authAccountParam) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        User user = findById(userId);

        // TODO: Verify person info here

        user.setAuthtime(new Timestamp(System.currentTimeMillis()));
        user.setAuthStatus((byte) 1);
        user.setRealName(authAccountParam.getRealName());
        user.setIdCardType(authAccountParam.getIdCardType());
        user.setIdCard(authAccountParam.getIdCard());
        userRepository.save(user);

        return true;
    }

    public void updatePhone(UpdatePhoneParam updatePhoneParam) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        userRepository.updateMobileById(updatePhoneParam.getCountryCode(), updatePhoneParam.getNewMobilePhone(), userId);
    }

    public boolean isPhoneNumberAvailable(String mobile, String countryCode) {
        return userRepository.countByMobileAndCountryCode(mobile, countryCode) == 0;
    }

    public void updatePassword(UpdatePasswordParam updatePasswordParam) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(updatePasswordParam.getOldPassword(), userRepository.findPasswordById(userId))) {
            throw new BadCredentialsException("Old password is wrong");
        }

        String encodedNewPassword = passwordEncoder.encode(updatePasswordParam.getNewPassword());
        userRepository.updatePasswordById(userId, encodedNewPassword);
    }

    public void updatePayPassword(UpdatePasswordParam updatePayPasswordParam) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(updatePayPasswordParam.getOldPassword(), userRepository.findPayPasswordById(userId))) {
            throw new BadCredentialsException("Old pay password is wrong");
        }

        String encodedNewPassword = passwordEncoder.encode(updatePayPasswordParam.getNewPassword());
        userRepository.updatePayPasswordById(userId, encodedNewPassword);
    }

    public void resetPayPassword(Map<String, String> setPayPasswordParam) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        String newPayPassword = setPayPasswordParam.get("payPassword");
        if (newPayPassword != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userRepository.updatePayPasswordById(userId, passwordEncoder.encode(newPayPassword));
        } else {
            throw new InvalidParameterException("Property 'payPassword' is needed");
        }
    }

    public List<User> findInvitees() {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return userRepository.findInvitees(userId);
    }

    public Map<Long, UserDto> findAllBasics(List<Long> ids, String username, String mobile) {
        List<User> resultList = null;
        if (ids != null) {
            resultList = userRepository.findAllBasicsWithId(ids, username, mobile);
        } else {
            resultList = userRepository.findAllBasicsWithoutId(username, mobile);
        }
        return resultList.stream()
                .map(UserDtoMapper.INSTANCE::entity2Dto)
                .collect(Collectors.toMap(UserDto::getId, userDto -> userDto));
    }
}

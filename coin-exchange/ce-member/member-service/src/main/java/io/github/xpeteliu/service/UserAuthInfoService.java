package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.UserAuthInfo;
import io.github.xpeteliu.repository.UserAuthInfoRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthInfoService {

    @Autowired
    UserAuthInfoRepository userAuthInfoRepository;

    public UserAuthInfo findById(Long id){
        return userAuthInfoRepository.findById(id).get();
    }

    public Page<UserAuthInfo> findWithPaging(Long userId, int page, int size) {
        List<UserAuthInfo> resultList = userAuthInfoRepository.findWithPaging(userId,  page * size, size);
        Long cnt = userAuthInfoRepository.countQualified(userId);
        PageRequest request = PageRequest.of(page, size, Sort.Direction.DESC, "lastUpdateTime");
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveUserAuthInfo(UserAuthInfo userAuthInfo) {
        userAuthInfoRepository.save(userAuthInfo);
    }

    public void updateUserAuthInfo(UserAuthInfo userAuthInfo) {
        UserAuthInfo target = userAuthInfoRepository.findById(userAuthInfo.getId()).get();
        SupplementaryUtils.copyBeanIgnoringNullProps(userAuthInfo,target);
        userAuthInfoRepository.save(target);
    }

    public List<UserAuthInfo> findAuthInfoByCode(Long authCode) {
        return userAuthInfoRepository.findByAuthCode(authCode);
    }
}

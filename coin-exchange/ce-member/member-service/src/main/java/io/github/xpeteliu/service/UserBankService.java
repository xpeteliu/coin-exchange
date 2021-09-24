package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.UserBank;
import io.github.xpeteliu.repository.UserBankRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBankService {

    @Autowired
    UserBankRepository userBankRepository;

    public UserBank findById(Long id){
        return userBankRepository.findById(id).get();
    }

    public Page<UserBank> findWithPaging(Long userId, int page, int size) {
        List<UserBank> resultList = userBankRepository.findWithPaging(userId,  page * size, size);
        Long cnt = userBankRepository.countQualified(userId);
        PageRequest request = PageRequest.of(page, size, Sort.Direction.DESC, "lastUpdateTime");
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveUserBank(UserBank userBank) {
        userBankRepository.save(userBank);
    }

    public void updateUserBank(UserBank userBank) {
        UserBank target = userBankRepository.findById(userBank.getId()).get();
        SupplementaryUtils.copyBeanIgnoringNullProps(userBank,target);
        userBankRepository.save(target);
    }

    public void updateStatus(Long id, Integer status) {
        userBankRepository.updateStatusById(id, status);
    }

    public UserBank findOneAvailableByUserId(){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return userBankRepository.findOneAvailableByUserId(userId).orElse(null);
    }
}

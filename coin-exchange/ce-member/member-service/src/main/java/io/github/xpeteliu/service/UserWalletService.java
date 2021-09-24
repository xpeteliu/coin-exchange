package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.UserWallet;
import io.github.xpeteliu.repository.UserWalletRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWalletService {

    @Autowired
    UserWalletRepository userWalletRepository;

    public UserWallet findById(Long id){
        return userWalletRepository.findById(id).get();
    }

    public Page<UserWallet> findWithPaging(Long userId, int page, int size) {
        List<UserWallet> resultList = userWalletRepository.findWithPaging(userId,  page * size, size);
        Long cnt = userWalletRepository.countQualified(userId);
        PageRequest request = PageRequest.of(page, size, Sort.Direction.DESC, "lastUpdateTime");
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveUserWallet(UserWallet userWallet) {
        userWalletRepository.save(userWallet);
    }

    public void updateUserWallet(UserWallet userWallet) {
        UserWallet target = userWalletRepository.findById(userWallet.getId()).get();
        SupplementaryUtils.copyBeanIgnoringNullProps(userWallet,target);
        userWalletRepository.save(target);
    }

    public void updateStatus(Long id, Integer status) {
//        userWalletRepository.updateStatusById(id, status);
    }

}

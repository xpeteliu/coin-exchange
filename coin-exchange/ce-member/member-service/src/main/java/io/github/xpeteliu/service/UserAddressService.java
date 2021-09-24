package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.UserAddress;
import io.github.xpeteliu.repository.UserAddressRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressService {

    @Autowired
    UserAddressRepository userAddressRepository;

    public UserAddress findById(Long id){
        return userAddressRepository.findById(id).get();
    }

    public Page<UserAddress> findWithPaging(Long userId, int page, int size) {
        List<UserAddress> resultList = userAddressRepository.findWithPaging(userId,  page * size, size);
        Long cnt = userAddressRepository.countQualified(userId);
        PageRequest request = PageRequest.of(page, size, Sort.Direction.DESC, "lastUpdateTime");
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveUserAddress(UserAddress userAddress) {
        userAddressRepository.save(userAddress);
    }

    public void updateUserAddress(UserAddress userAddress) {
        UserAddress target = userAddressRepository.findById(userAddress.getId()).get();
        SupplementaryUtils.copyBeanIgnoringNullProps(userAddress,target);
        userAddressRepository.save(target);
    }

    public void updateStatus(Long id, Integer status) {
//        userAddressRepository.updateStatusById(id, status);
    }

}

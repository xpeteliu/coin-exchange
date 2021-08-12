package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.AdminAddress;
import io.github.xpeteliu.repository.AdminAddressRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@Service
public class AdminAddressService {

    @Autowired
    AdminAddressRepository adminAddressRepository;

    public Page<AdminAddress> findWithPaging(Long coinId, Pageable pageable) {
        AdminAddress adminAddress = new AdminAddress();
        adminAddress.setCoinId(coinId);
        return adminAddressRepository.findAll(Example.of(adminAddress), pageable);
    }

    public void save(AdminAddress adminAddress) {
        adminAddressRepository.save(adminAddress);
    }

    public void update(AdminAddress adminAddress) {
        AdminAddress target = adminAddressRepository.findById(adminAddress.getId()).get();
        SupplementaryUtils.copyBeanIgnoringNullProps(adminAddress,target);
        adminAddressRepository.save(target);
    }
}

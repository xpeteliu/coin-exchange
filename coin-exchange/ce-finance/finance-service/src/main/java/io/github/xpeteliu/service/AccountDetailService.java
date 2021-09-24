package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.AccountDetail;
import io.github.xpeteliu.repository.AccountDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDetailService {

    @Autowired
    AccountDetailRepository accountDetailRepository;

    public void save(AccountDetail accountDetail) {
        accountDetailRepository.save(accountDetail);
    }

    public void savaAll(List<AccountDetail> accountDetails) {
        accountDetailRepository.saveAll(accountDetails);
    }
}

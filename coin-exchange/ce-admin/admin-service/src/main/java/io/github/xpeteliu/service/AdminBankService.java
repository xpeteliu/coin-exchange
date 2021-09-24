package io.github.xpeteliu.service;

import io.github.xpeteliu.dto.AdminBankDto;
import io.github.xpeteliu.entity.AdminBank;
import io.github.xpeteliu.mapper.AdminBankDtoMapper;
import io.github.xpeteliu.repository.AdminBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBankService {

    @Autowired
    AdminBankRepository adminBankRepository;

    boolean isSuperAdmin(Long userId) {
        return "ROLE_ADMIN".equals(adminBankRepository.findCodeByUserId(userId));
    }

    public Page<AdminBank> findByBankCardWithPaging(String bankCard, PageRequest request) {
        List<AdminBank> resultList = adminBankRepository.findByBankCardContaining(bankCard, request);
        Long cnt = adminBankRepository.countByBankCardContaining(bankCard);
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveBankCard(AdminBank adminBank) {
        adminBankRepository.save(adminBank);
    }

    public void updateStatus(Long id, Integer status) {
        adminBankRepository.updateStatusById(id, status);
    }

    public List<AdminBankDto> findAllAvailable() {
        List<AdminBank> resultList=adminBankRepository.findByStatus((byte)1);
        return AdminBankDtoMapper.INSTANCE.entity2Dto(resultList);
    }
}

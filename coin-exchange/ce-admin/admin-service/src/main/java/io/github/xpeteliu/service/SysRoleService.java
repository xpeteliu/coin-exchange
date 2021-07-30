package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.SysRole;
import io.github.xpeteliu.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleService {

    @Autowired
    SysRoleRepository sysRoleRepository;

    boolean isSuperAdmin(Long userId) {
        return "ROLE_ADMIN".equals(sysRoleRepository.findCodeByUserId(userId));
    }

    public Page<SysRole> findByNameWithPaging(String name, PageRequest request) {
        // TODO: return value of findByName shall be directly returned in a future framework
        List<SysRole> resultList = sysRoleRepository.findByNameContaining(name, request);
        Long cnt = sysRoleRepository.countByNameContaining(name);
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveRole(SysRole sysRole) {
        sysRoleRepository.save(sysRole);
    }

    public void deleteRoles(String[] ids) {
        // TODO: replace the loop with a call to deleteAllById
        for (String id : ids) {
            sysRoleRepository.deleteById(Long.valueOf(id));
        }
    }
}

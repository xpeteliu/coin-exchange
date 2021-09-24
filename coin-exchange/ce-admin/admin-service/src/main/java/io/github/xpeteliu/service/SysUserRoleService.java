package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.SysUserRole;
import io.github.xpeteliu.repository.SysUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserRoleService {

    @Autowired
    SysUserRoleRepository sysUserRoleRepository;

    public void saveUserRole(SysUserRole sysUserRole) {
        sysUserRoleRepository.save(sysUserRole);
    }

    public String findRoleStrByUserId(Long userId) {
        List<SysUserRole> sysUserRoles = sysUserRoleRepository.findByUserId(userId);
        return sysUserRoles
                .stream()
                .map(sysUserRole -> sysUserRole.getRoleId().toString())
                .collect(Collectors.joining(","));
    }

    public void deleteUserRoleByUserId(Long userId) {
        sysUserRoleRepository.deleteByUserId(userId);
    }

    public void deleteAllUserRoleByUserId(List<Long> ids) {
        sysUserRoleRepository.deleteAllByUserId(ids);
    }
}

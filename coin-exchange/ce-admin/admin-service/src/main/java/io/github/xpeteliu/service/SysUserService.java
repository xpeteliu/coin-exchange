package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.SysUser;
import io.github.xpeteliu.entity.SysUserRole;
import io.github.xpeteliu.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SysUserService {
    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    SysUserRoleService sysUserRoleService;

    public SysUser findById(Long id) {
        return sysUserRepository.findById(id).orElse(null);
    }

    public Page<SysUser> findByNameAndMobileWithPaging(String fullname, String mobile, PageRequest request) {
        // TODO: return value of find() shall be directly returned in a future framework
        List<SysUser> resultList = sysUserRepository.findByFullnameContainingAndMobileContaining(fullname, mobile, request);
        Long cnt = sysUserRepository.countByFullnameContainingAndMobileContaining(fullname, mobile);
        for (SysUser user : resultList) {
            user.setRoleStr(sysUserRoleService.findRoleStrByUserId(user.getId()));
        }
        return new PageImpl<>(resultList, request, cnt);
    }

    @Transactional
    public void addUser(SysUser sysUser) {
        sysUserRepository.save(sysUser);

        String[] roleIds = sysUser.getRoleStr().split(",");
        SysUserRole temp = new SysUserRole();
        for (String roleId : roleIds) {
            temp.setId(null);
            temp.setRoleId(Long.valueOf(roleId));
            temp.setUserId(sysUser.getId());
            sysUserRoleService.saveUserRole(temp);
        }
    }

    @Transactional
    public void updateUser(SysUser sysUser) {
        sysUserRepository.save(sysUser);

        sysUserRoleService.deleteUserRoleByUserId(sysUser.getId());

        String[] roleIds = sysUser.getRoleStr().split(",");
        SysUserRole temp = new SysUserRole();
        for (String roleId : roleIds) {
            temp.setId(null);
            temp.setRoleId(Long.valueOf(roleId));
            temp.setUserId(sysUser.getId());
            sysUserRoleService.saveUserRole(temp);
        }
    }

    @Transactional
    public void deleteUsers(List<Long> ids) {
        sysUserRepository.deleteAllById(ids);
        sysUserRoleService.deleteAllUserRoleByUserId(ids);
    }
}

package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.SysMenu;
import io.github.xpeteliu.repository.SysMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMenuService {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysMenuRepository sysMenuRepository;

    public Iterable<SysMenu> findAllMenus() {
        return sysMenuRepository.findAll();
    }

    public Iterable<SysMenu> findMenusByUserId(Long userId) {
        if (sysRoleService.isSuperAdmin(userId)) {
            return sysMenuRepository.findAll();
        }
        return sysMenuRepository.findAllByUserId(userId);
    }
}


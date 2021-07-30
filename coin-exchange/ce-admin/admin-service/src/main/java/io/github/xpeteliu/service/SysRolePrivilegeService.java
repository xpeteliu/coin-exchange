package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.SysMenu;
import io.github.xpeteliu.entity.SysRolePrivilege;
import io.github.xpeteliu.model.RolePrivilegesParam;
import io.github.xpeteliu.repository.SysRolePrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SysRolePrivilegeService {

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysPrivilegeService sysPrivilegeService;

    @Autowired
    SysRolePrivilegeRepository sysRolePrivilegeRepository;

    public List<SysMenu> findSysMenu(Long roleId) {
        Iterable<SysMenu> menus = sysMenuService.findAllMenus();
        if (menus == null) {
            return Collections.emptyList();
        }

        List<SysMenu> rootMenus = StreamSupport
                .stream(menus.spliterator(), false)
                .filter(sysMenu -> sysMenu.getParentId() == null)
                .collect(Collectors.toList());

        List<SysMenu> subMenus = new ArrayList<>();
        for (SysMenu rootMenu : rootMenus) {
            subMenus.addAll(getChildMenus(rootMenu.getId(), roleId, menus));
        }
        return subMenus;
    }

    public List<SysMenu> getChildMenus(Long parentId, Long roleId, Iterable<SysMenu> menus) {
        ArrayList<SysMenu> children = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (parentId.equals(menu.getParentId())) {
                children.add(menu);
                menu.setChildren(getChildMenus(menu.getId(), roleId, menus));
                menu.setPrivileges(sysPrivilegeService.findPrivilegeByMenuIdAndMarkPossession(menu.getId(), roleId));
            }
        }
        return children;
    }

    public Set<Long> findPrivilegeIdByRoleId(Long roleID) {
        return sysRolePrivilegeRepository.findAllPrivilegeIdByRoleId(roleID).collect(Collectors.toSet());
    }

    @Transactional
    public void grantPrivilege(RolePrivilegesParam param) {
        Long roleId = param.getRoleId();
        sysRolePrivilegeRepository.deleteByRoleId(roleId);
        SysRolePrivilege temp = new SysRolePrivilege();
        temp.setRoleId(roleId);
        for (Long privilegeId : param.getPrivilegeIds()) {
            temp.setId(null);
            temp.setPrivilegeId(privilegeId);
            sysRolePrivilegeRepository.save(temp);
        }
    }
}

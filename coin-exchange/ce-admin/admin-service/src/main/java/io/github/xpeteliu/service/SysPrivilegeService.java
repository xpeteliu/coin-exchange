package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.SysPrivilege;
import io.github.xpeteliu.repository.SysPrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class SysPrivilegeService {

    @Autowired
    SysPrivilegeRepository sysPrivilegeRepository;

    @Autowired
    SysRolePrivilegeService sysRolePrivilegeService;

    public List<SysPrivilege> findPrivilegeByMenuIdAndMarkPossession(Long menuId, Long roleId) {
        List<SysPrivilege> privileges = sysPrivilegeRepository.findByMenuId(menuId);
        if (CollectionUtils.isEmpty(privileges)) {
            return Collections.emptyList();
        }
        Set<Long> possessedPrivilegeId = sysRolePrivilegeService.findPrivilegeIdByRoleId(roleId);
        for (SysPrivilege privilege : privileges) {
            if (possessedPrivilegeId.contains(privilege.getId())) {
                privilege.setOwn(1);
            }
        }
        return privileges;
    }

    public Page<SysPrivilege> findAllWithPaging(PageRequest request) {
        return sysPrivilegeRepository.findAll(request);
    }

    public void savePrivilege(SysPrivilege sysPrivilege) {
        sysPrivilegeRepository.save(sysPrivilege);
    }
}

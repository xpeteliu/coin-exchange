package io.github.xpeteliu.callback;


import io.github.xpeteliu.entity.SysUserRole;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

//@Component
public class UserRoleAutoFillingCallback implements BeforeConvertCallback<SysUserRole> {

    @Override
    public SysUserRole onBeforeConvert(SysUserRole sysUserRole) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (sysUserRole.getId() == null || sysUserRole.getId() == 0) {
            sysUserRole.setCreateBy(userId);
            sysUserRole.setCreated(currentTime);
        } else {
            sysUserRole.setModifyBy(userId);
        }
        sysUserRole.setLastUpdateTime(currentTime);
        return sysUserRole;
    }
}
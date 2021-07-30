package io.github.xpeteliu.callback;

import io.github.xpeteliu.entity.SysPrivilege;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

//@Component
public class PrivilegeAutoFillingCallback implements BeforeConvertCallback<SysPrivilege> {

    @Override
    public SysPrivilege onBeforeConvert(SysPrivilege sysPrivilege) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (sysPrivilege.getId() == null || sysPrivilege.getId() == 0) {
            sysPrivilege.setCreateBy(userId);
            sysPrivilege.setCreated(currentTime);
        } else {
            sysPrivilege.setModifyBy(userId);
        }
        sysPrivilege.setLastUpdateTime(currentTime);
        return sysPrivilege;
    }
}

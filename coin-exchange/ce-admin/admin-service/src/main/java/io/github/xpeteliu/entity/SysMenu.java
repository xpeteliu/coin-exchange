package io.github.xpeteliu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_menu")
public class SysMenu {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "parent_id")
    private Long parentId;

    @Column(value = "parent_key")
    private String parentKey;

    @Column(value = "type")
    private Byte type;

    @Column(value = "name")
    private String name;

    @Column(value = "desc")
    private String desc;

    @Column(value = "target_url")
    private String targetUrl;

    @Column(value = "sort")
    private Integer sort;

    @Column(value = "status")
    private Byte status;

    @Column(value = "create_by")
    private Long createBy;

    @Column(value = "modify_by")
    private Long modifyBy;

    @Column(value = "created")
    private java.sql.Timestamp created;

    @Column(value = "last_update_time")
    private java.sql.Timestamp lastUpdateTime;

    @Transient
    private List<SysPrivilege> privileges = Collections.emptyList();

    @Transient
    private List<SysMenu> children = Collections.emptyList();

    @Transient
    String menuKey = "";

    public String getMenuKey() {
        String parentKey = getParentKey();
        Long id = getId();
        if (StringUtils.hasLength(parentKey)) {
            return parentKey + "." + id;
        } else {
            return id.toString();
        }
    }

}

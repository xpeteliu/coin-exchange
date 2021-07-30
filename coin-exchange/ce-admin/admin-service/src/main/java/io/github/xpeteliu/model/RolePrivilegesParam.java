package io.github.xpeteliu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@ApiModel("Received info of a role with privileges, used for io.github.xpeteliu.controller.SysRolePrivilegeController.grantPrivilege")
public class RolePrivilegesParam {

    @ApiModelProperty("The role's ID")
    private Long roleId = 0L;

    @ApiModelProperty("The role's privileges")
    private List<Long> privilegeIds = Collections.emptyList();
}

package io.github.xpeteliu.model;

import io.github.xpeteliu.entity.User;
import io.github.xpeteliu.entity.UserAuthAuditRecord;
import io.github.xpeteliu.entity.UserAuthInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("User authentication information encapsulated as a response to users/auths/info")
public class UserAuthInfoResult implements Serializable {

    @ApiModelProperty("User info")
    User user;

    @ApiModelProperty("Key auth info")
    List<UserAuthInfo> userAuthInfoList;

    @ApiModelProperty("User audit record")
    List<UserAuthAuditRecord> authAuditRecordList;
}

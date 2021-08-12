package io.github.xpeteliu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Submitted user login form")
public class LoginFormParameter {

    @ApiModelProperty("Country code of mobile number")
    private String countryCode;

    @ApiModelProperty("Username")
    private String username;

    @ApiModelProperty("Password")
    private String password;

    @ApiModelProperty("UUID of user")
    private String uuid;

}

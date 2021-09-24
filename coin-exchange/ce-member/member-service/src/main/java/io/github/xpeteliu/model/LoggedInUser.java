package io.github.xpeteliu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Class representing a logged-in user")
public class LoggedInUser {

    @ApiModelProperty(value = "Username")
    private String username;

    @ApiModelProperty(value = "Time before token expiry")
    private Long expire;

    @ApiModelProperty(value = "Access token retrieved after login")
    private String access_token;

    @ApiModelProperty(value = "Refresh token retrieved after login")
    private String refresh_token;

}

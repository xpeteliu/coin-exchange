package io.github.xpeteliu.model;

import io.github.xpeteliu.entity.SysMenu;
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
@ApiModel(value = "Result of login")
public class SysLoginResult {

    @ApiModelProperty(value = "Token retrieved after login")
    private String token;

    @ApiModelProperty(value = "Menus accessible for the user")
    private List<SysMenu> menus;

    @ApiModelProperty(value = "Authorities granted for the user")
    private List<SimpleGrantedAuthority> authorities;
}

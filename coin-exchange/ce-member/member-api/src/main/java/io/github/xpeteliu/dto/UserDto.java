package io.github.xpeteliu.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank
    @NotBlank
    private String username;

    private String countryCode;

    @NotBlank
    @NotBlank
    private String mobile;

    private String email;

    private String realName;

}
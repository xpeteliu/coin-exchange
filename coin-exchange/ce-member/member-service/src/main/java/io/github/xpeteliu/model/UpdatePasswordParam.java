package io.github.xpeteliu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatePasswordParam {

    @ApiModelProperty("Old password")
    @JsonProperty("oldpassword")
    @NotBlank
    private String oldPassword;

    @ApiModelProperty("New password")
    @JsonProperty("newpassword")
    @NotBlank
    private String newPassword;

}

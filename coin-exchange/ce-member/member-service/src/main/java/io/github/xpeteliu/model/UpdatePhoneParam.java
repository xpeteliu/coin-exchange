package io.github.xpeteliu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("Info provided to update phone number")
public class UpdatePhoneParam {

    @ApiModelProperty("Country code of phone number")
    private String countryCode;

    @ApiModelProperty("New mobile number")
    private String newMobilePhone;
}

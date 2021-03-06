package io.github.xpeteliu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user")
public class User {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "type")
    private Byte type;

    @NotBlank
    @Column(value = "username")
    @NotBlank
    private String username;

    @Column(value = "country_code")
    private String countryCode;

    @NotBlank
    @Column(value = "mobile")
    @NotBlank
    private String mobile;

    @Column(value = "password")
    private String password;

    @Column(value = "paypassword")
    private String paypassword;

    @Column(value = "paypass_setting")
    private Byte paypassSetting;

    @Column(value = "email")
    private String email;

    @Column(value = "real_name")
    private String realName;

    @Column(value = "id_card_type")
    private Byte idCardType;

    @Column(value = "auth_status")
    private Byte authStatus;

    @Column(value = "ga_secret")
    private String gaSecret;

    @Column(value = "ga_status")
    private Byte gaStatus;

    @Column(value = "id_card")
    private String idCard;

    @Column(value = "level")
    private Integer level;

    @Column(value = "authtime")
    private java.sql.Timestamp authtime;

    @Column(value = "logins")
    private Integer logins;

    @Column(value = "status")
    private Byte status;

    @Column(value = "invite_code")
    private String inviteCode;

    @Column(value = "invite_relation")
    private String inviteRelation;

    @Column(value = "direct_inviteid")
    private String directInviteid;

    @Column(value = "is_deductible")
    private Integer isDeductible;

    @Column(value = "reviews_status")
    private Integer reviewsStatus;

    @Column(value = "agent_note")
    private String agentNote;

    @Column(value = "access_key_id")
    private String accessKeyId;

    @Column(value = "access_key_secret")
    private String accessKeySecret;

    @Column(value = "refe_auth_id")
    private Long refeAuthId;

    @Column(value = "last_update_time")
    @LastModifiedDate
    private java.sql.Timestamp lastUpdateTime;

    @Column(value = "created")
    @CreatedDate
    private java.sql.Timestamp created;

    @Transient
    private Byte seniorAuthStatus;

    @Transient
    private String seniorAuthDesc;

}

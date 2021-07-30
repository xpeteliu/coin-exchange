package io.github.xpeteliu.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "admin_bank")
public class AdminBank {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "name")
    @NotBlank
    private String name;

    @Column(value = "bank_name")
    @NotBlank
    private String bankName;

    @Column(value = "bank_card")
    @NotBlank
    private String bankCard;

    @Column(value = "coin_id")
    private Long coinId;

    @Column(value = "coin_name")
    private String coinName;

    @Column(value = "status")
    private Byte status;

}

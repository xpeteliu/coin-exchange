package io.github.xpeteliu.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "user_account_freeze")
public class UserAccountFreeze implements Serializable {

    

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "freeze")
    private BigDecimal freeze;

}

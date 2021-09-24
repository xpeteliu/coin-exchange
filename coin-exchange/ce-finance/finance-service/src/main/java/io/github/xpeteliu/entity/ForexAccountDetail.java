package io.github.xpeteliu.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "forex_account_detail")
@EntityListeners(AuditingEntityListener.class)
public class ForexAccountDetail implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "remark")
    private String remark;

    @Column(name = "created")
    @CreatedDate
    private Date created ;

}

package io.github.xpeteliu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_detail")
@EntityListeners(AuditingEntityListener.class)
public class AccountDetail implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coin_id", nullable = false)
    private Long coinId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "ref_account_id", nullable = false)
    private Long refAccountId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "direction", nullable = false)
    private Integer direction;

    @Column(name = "business_type", nullable = false)
    private String businessType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "fee")
    private BigDecimal fee;

    @Column(name = "remark", nullable = false)
    private String remark;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created;

}

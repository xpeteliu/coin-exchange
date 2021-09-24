package io.github.xpeteliu.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "forex_account")
@EntityListeners(AuditingEntityListener.class)
public class ForexAccount implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "market_id")
    private Long marketId;

    @Column(name = "market_name")
    private String marketName;

    @Column(name = "type")
    private Integer type;

    @Column(name = "amount")
    private BigDecimal amount ;

    @Column(name = "lock_amount")
    private BigDecimal lockAmount ;

    @Column(name = "status")
    private Integer status;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime ;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created ;

}

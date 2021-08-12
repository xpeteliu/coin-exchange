package io.github.xpeteliu.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "trade_area")
@EntityListeners({AuditingEntityListener.class})
public class TradeArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "code")
    @NotBlank
    private String code;

    @Column(name = "type")
    private Integer type;

    @Column(name = "coin_id")
    private Long coinId;

    @Column(name = "coin_name")
    private String coinName;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "status")
    private Integer status;

    @Column(name = "base_coin")
    private Long baseCoin;

    @Column(name = "last_update_time")
    @LastModifiedDate
    private Date lastUpdateTime;

    @Column(name = "created")
    @CreatedDate
    private Date created;

}

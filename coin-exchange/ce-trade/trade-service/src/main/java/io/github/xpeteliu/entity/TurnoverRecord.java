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
@Table(name = "turnover_record")
@EntityListeners({AuditingEntityListener.class})
public class TurnoverRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "market_id", nullable = false)
    private Long marketId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "volume", nullable = false)
    private BigDecimal volume;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created;

}

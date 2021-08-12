package io.github.xpeteliu.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "coin_server")
@EntityListeners(AuditingEntityListener.class)
public class CoinServer implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rpc_ip", nullable = false)
    private String rpcIp ;

    @Column(name = "rpc_port", nullable = false)
    private String rpcPort ;

    @Column(name = "running", nullable = false)
    private Integer running;

    @Column(name = "wallet_number", nullable = false)
    private Long walletNumber;

    @Column(name = "coin_name")
    private String coinName;

    @Column(name = "mark")
    private String mark;

    @Column(name = "real_number")
    private Long realNumber;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime ;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created ;

}

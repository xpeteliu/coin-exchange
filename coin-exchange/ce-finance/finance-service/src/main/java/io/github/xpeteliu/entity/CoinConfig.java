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
@Table(name = "coin_config")
public class CoinConfig implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "coin_type", nullable = false)
    private String coinType ;

    @Column(name = "credit_limit")
    private BigDecimal creditLimit;

    @Column(name = "credit_max_limit")
    private BigDecimal creditMaxLimit;

    @Column(name = "rpc_ip")
    private String rpcIp;

    @Column(name = "rpc_port")
    private String rpcPort;

    @Column(name = "rpc_user")
    private String rpcUser;

    @Column(name = "rpc_pwd")
    private String rpcPwd;

    @Column(name = "last_block")
    private String lastBlock ;

    @Column(name = "wallet_user")
    private String walletUser;

    @Column(name = "wallet_pass")
    private String walletPass;

    @Column(name = "contract_address")
    private String contractAddress ;

    @Column(name = "context")
    private String context;

    @Column(name = "min_confirm")
    private Integer minConfirm ;

    @Column(name = "task")
    private String task;

    @Column(name = "status", nullable = false)
    private Integer status ;

    @Column(name = "auto_draw_limit")
    private BigDecimal autoDrawLimit;

    @Column(name = "auto_draw")
    private Integer autoDraw;

}

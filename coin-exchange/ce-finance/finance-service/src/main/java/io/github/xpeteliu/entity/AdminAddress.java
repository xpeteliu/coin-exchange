package io.github.xpeteliu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "admin_address")
public class AdminAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coin_id")
    private Long coinId;

    @Column(name = "keystore")
    private String keystore;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private Integer status;

    @Column(name = "coin_type", nullable = false)
    private String coinType;

}

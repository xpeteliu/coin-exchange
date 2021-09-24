package io.github.xpeteliu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "address_pool")
public class AddressPool implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coin_id", nullable = false)
    private Long coinId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "keystore")
    private String keystore;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "coin_type", nullable = false)
    private String coinType;

}

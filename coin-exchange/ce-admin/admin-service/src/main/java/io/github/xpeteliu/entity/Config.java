package io.github.xpeteliu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "config")
public class Config {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "type")
    @NotBlank
    private String type;

    @Column(value = "code")
    @NotBlank
    private String code;

    @Column(value = "name")
    @NotBlank
    private String name;

    @Column(value = "desc")
    @NotBlank
    private String desc;

    @Column(value = "value")
    @NotBlank
    private String value;

    @Column(value = "created")
    @CreatedDate
    private java.sql.Timestamp created;

}

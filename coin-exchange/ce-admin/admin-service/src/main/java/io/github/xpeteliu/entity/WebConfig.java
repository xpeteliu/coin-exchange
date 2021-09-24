package io.github.xpeteliu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "web_config")
public class WebConfig {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "type")
    private String type;

    @Column(value = "name")
    private String name;

    @Column(value = "value")
    private String value;

    @Column(value = "sort")
    private Integer sort;

    @Column(value = "created")
    @CreatedDate
    private java.sql.Timestamp created;

    @Column(value = "url")
    private String url;

    @Column(value = "status")
    private Byte status;

}

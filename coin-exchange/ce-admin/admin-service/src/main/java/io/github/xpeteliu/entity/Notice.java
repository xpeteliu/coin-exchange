package io.github.xpeteliu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "notice")
public class Notice {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "title")
    @NotBlank
    private String title;

    @Column(value = "description")
    @NotBlank
    private String description;

    @Column(value = "author")
    @NotBlank
    private String author;

    @Column(value = "status")
    private Integer status;

    @Column(value = "sort")
    @NotNull
    private Integer sort;

    @Column(value = "content")
    @NotBlank
    private String content;

    @Column(value = "last_update_time")
    @LastModifiedDate
    private java.sql.Timestamp lastUpdateTime;

    @Column(value = "created")
    @CreatedDate
    private java.sql.Timestamp created;

}

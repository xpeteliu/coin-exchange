package io.github.xpeteliu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "work_order")
public class WorkOrder {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "answer_user_id")
    @LastModifiedBy
    private Long answerUserId;

    @Column(value = "answer_name")
    private String answerName;

    @Column(value = "question")
    private String question;

    @Column(value = "answer")
    private String answer;

    @Column(value = "status")
    private Byte status;

    @Column(value = "last_update_time")
    @LastModifiedDate
    private java.sql.Timestamp lastUpdateTime;

    @Column(value = "created")
    @CreatedDate
    private java.sql.Timestamp created;

    @Transient
    private String username="Test user";

    @Transient
    private String realName="Test name";
}

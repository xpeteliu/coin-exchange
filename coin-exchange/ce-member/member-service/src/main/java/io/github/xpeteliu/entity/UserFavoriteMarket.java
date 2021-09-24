package io.github.xpeteliu.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_favorite_market")
public class UserFavoriteMarket {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "type")
    private Integer type;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "market_id")
    private Long marketId;

}

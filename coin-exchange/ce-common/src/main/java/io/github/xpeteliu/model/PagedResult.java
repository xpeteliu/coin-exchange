package io.github.xpeteliu.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class PagedResult<T> implements Serializable {

    private static final long serialVersionUID = 8545996863226528798L;

    private List<T> records = Collections.emptyList();

    private long total = 0;

    private long size = 10;

    private long current = 1;

    public PagedResult(Page<T> page) {
        this.total = page.getTotalElements();
        this.size = page.getSize();
        this.current = page.getNumber() + 1;
        this.records = page.getContent();
    }
}

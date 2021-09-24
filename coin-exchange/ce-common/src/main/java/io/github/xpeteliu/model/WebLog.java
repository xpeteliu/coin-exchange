package io.github.xpeteliu.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class WebLog {

    private String description;

    private String username;

    private Integer timeSpent;

    private String basePath;

    private String uri;

    private String url;

    private String method;

    private String ip;

    private Map<String,Object> parameter;

    private Object result;
}


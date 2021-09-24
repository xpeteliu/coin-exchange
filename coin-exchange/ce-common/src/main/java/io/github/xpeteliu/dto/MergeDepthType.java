package io.github.xpeteliu.dto;

import org.springframework.util.StringUtils;

public enum MergeDepthType {

    DEFAULT("step0", 0),
    LOW("step1", 1),
    HIGH("step2", 2);

    /**
     * 代码
     */
    private String code;

    /**
     * 值
     */
    private int value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    MergeDepthType(String code, int value) {
        this.code = code;
        this.value = value;
    }

    public static MergeDepthType getByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            return null;
        }
        for (MergeDepthType MergeDepthType : MergeDepthType.values()) {
            if (MergeDepthType.getCode().equals(code)) {
                return MergeDepthType;
            }
        }
        return null;
    }

    public static MergeDepthType getByValue(int value) {
        for (MergeDepthType MergeDepthType : MergeDepthType.values()) {
            if (MergeDepthType.getValue() == value) {
                return MergeDepthType;
            }
        }
        return null;
    }
}


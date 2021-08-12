package io.github.xpeteliu.model;

public enum OrderDirection {

    BUY(1, "buy"),
    SELL(2, "sell");

    private int code;

    private String desc;


    OrderDirection(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderDirection getOrderDirectionByCode(int code) {
        OrderDirection[] values = OrderDirection.values();
        for (OrderDirection value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    public static OrderDirection getReverseOrderDirection(OrderDirection od) {
        return od == BUY ? SELL : BUY;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}



package com.os.yerinial.metrics;

public enum ReservationMetricOperation {
    CREATE("create"),
    CANCEL("cancel");

    private final String code;

    ReservationMetricOperation(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}

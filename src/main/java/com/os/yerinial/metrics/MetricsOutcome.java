package com.os.yerinial.metrics;

public enum MetricsOutcome {
    SUCCESS("success"),
    REJECTED("rejected"),
    ERROR("error");

    private final String code;

    MetricsOutcome(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}

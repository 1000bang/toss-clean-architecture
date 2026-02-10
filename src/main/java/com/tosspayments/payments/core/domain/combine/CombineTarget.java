package com.tosspayments.payments.core.domain.combine;

public class CombineTarget {

    private final String targetId;
    private final String data;

    public CombineTarget(String targetId, String data) {
        this.targetId = targetId;
        this.data = data;
    }

    public String getTargetId() {
        return targetId;
    }

    public String getData() {
        return data;
    }
}

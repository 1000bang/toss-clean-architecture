package com.tosspayments.payments.core.domain.mobile;

public class MobilePaymentResult {

    private final String paymentId;
    private final String status;

    public MobilePaymentResult(String paymentId, String status) {
        this.paymentId = paymentId;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getStatus() {
        return status;
    }
}

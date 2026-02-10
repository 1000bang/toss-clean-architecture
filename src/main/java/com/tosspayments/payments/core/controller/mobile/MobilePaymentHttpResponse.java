package com.tosspayments.payments.core.controller.mobile;

public class MobilePaymentHttpResponse {

    private final String paymentId;
    private final String status;

    public MobilePaymentHttpResponse(String paymentId, String status) {
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

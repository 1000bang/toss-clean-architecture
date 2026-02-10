package com.tosspayments.payments.core.domain.card;

public class CardPaymentResult {

    private final String paymentId;
    private final String status;
    private final long amount;

    public CardPaymentResult(String paymentId, String status, long amount) {
        this.paymentId = paymentId;
        this.status = status;
        this.amount = amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getStatus() {
        return status;
    }

    public long getAmount() {
        return amount;
    }
}

package com.tosspayments.payments.core.controller.card;

/**
 * Presentation Layer 전용 HTTP 요청 DTO
 * - 이 객체는 Controller 안에서만 사용되고, domain 패키지로 전파되지 않는다.
 */
public class CardPaymentHttpRequest {

    private String orderId;
    private String cardNumber;
    private long amount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}

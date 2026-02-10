package com.tosspayments.payments.core.domain.card;

/**
 * 도메인 레벨의 카드 결제 요청 VO
 * - Controller의 HttpRequest DTO가 아닌, 비즈니스 로직용 도메인 객체
 */
public class CardPaymentRequest {

    private final String orderId;
    private final String cardNumber;
    private final long amount;

    public CardPaymentRequest(String orderId, String cardNumber, long amount) {
        this.orderId = orderId;
        this.cardNumber = cardNumber;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public long getAmount() {
        return amount;
    }
}

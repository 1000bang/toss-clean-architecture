package com.tosspayments.payments.core.domain.card;

public class CustomerCard {

    private final String customerId;
    private final Card card;

    public CustomerCard(String customerId, Card card) {
        this.customerId = customerId;
        this.card = card;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Card getCard() {
        return card;
    }
}

package com.tosspayments.payments.core.domain.card;

/**
 * 카드 도메인 객체
 * - 같은 '카드' 개념에 속하는 클래스들이 하나의 패키지에 응집
 */
public class Card {

    private final String cardNumber;
    private final String cardCompany;
    private final String expirationDate;

    public Card(String cardNumber, String cardCompany, String expirationDate) {
        this.cardNumber = cardNumber;
        this.cardCompany = cardCompany;
        this.expirationDate = expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardCompany() {
        return cardCompany;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}

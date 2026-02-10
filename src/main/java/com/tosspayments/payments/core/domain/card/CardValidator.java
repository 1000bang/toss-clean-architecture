package com.tosspayments.payments.core.domain.card;

import org.springframework.stereotype.Component;

/**
 * 카드 유효성 검증 - '카드' 개념 패키지 안에 위치
 * (잘못된 예: component 패키지에 분리)
 */
@Component
public class CardValidator {

    public void validate(Card card) {
        if (card.getCardNumber() == null || card.getCardNumber().isBlank()) {
            throw new IllegalArgumentException("카드 번호는 필수입니다.");
        }
        if (card.getExpirationDate() == null || card.getExpirationDate().isBlank()) {
            throw new IllegalArgumentException("유효기간은 필수입니다.");
        }
    }
}

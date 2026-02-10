package com.tosspayments.payments.core.domain.card;

import org.springframework.stereotype.Component;

/**
 * 카드 결제 처리 - '카드' 개념 패키지 안에 위치
 * (잘못된 예: component 패키지에 분리)
 */
@Component
public class CardPaymentProcessor {

    public CardPaymentResult process(Card card, long amount) {
        // 실제로는 PG사 연동 등 복잡한 로직
        String paymentId = "PAY-" + System.currentTimeMillis();
        return new CardPaymentResult(paymentId, "SUCCESS", amount);
    }
}

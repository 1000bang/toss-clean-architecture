package com.tosspayments.payments.core.infrastructure.persistence;

import com.tosspayments.payments.core.domain.card.Card;
import com.tosspayments.payments.core.domain.card.CardReader;
import com.tosspayments.payments.core.domain.card.CustomerCard;
import org.springframework.stereotype.Component;

/**
 * CardReader 인터페이스의 JPA 구현체
 * - 도메인은 CardReader 인터페이스만 알고, JPA 의존성은 여기에 격리
 */
@Component
public class JpaCardReader implements CardReader {

    @Override
    public Card readByCardNumber(String cardNumber) {
        // 실제로는 JPA Repository를 통해 DB 조회
        return new Card(cardNumber, "VISA", "2026-12");
    }

    @Override
    public CustomerCard readCustomerCard(String customerId, String cardNumber) {
        Card card = readByCardNumber(cardNumber);
        return new CustomerCard(customerId, card);
    }
}

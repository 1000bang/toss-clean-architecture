package com.tosspayments.payments.core.domain.card;

// ============================================================
// [Package 원칙] 올바른 import 예시
// - 모든 import가 같은 'card' 개념 패키지 내부를 참조
// - 역할별(service, component, vo, dataaccess)로 흩어지지 않음
// ============================================================
// 같은 패키지이므로 별도 import 불필요!
// Card, CustomerCard, CardPaymentRequest, CardValidator,
// CardPaymentProcessor, CardReader 모두 같은 패키지

import com.tosspayments.payments.core.domain.store.StoreReader;
import org.springframework.stereotype.Service;

/**
 * [올바른 예시 - Package]
 *
 * 잘못된 예:
 *   import com.tosspayments.payments.core.component.CardPaymentProcessor;
 *   import com.tosspayments.payments.core.component.CardValidator;
 *   import com.tosspayments.payments.core.dataaccess.CardReader;
 *   import com.tosspayments.payments.core.vo.Card;
 *   → 역할별로 흩어진 패키지에서 import
 *
 * 올바른 예 (현재):
 *   같은 card 패키지 안에 모두 응집 → import 자체가 불필요
 *   외부 개념(store)만 명시적 import
 */
@Service
public class CardService {

    private final CardValidator cardValidator;
    private final CardReader cardReader;
    private final CardPaymentProcessor cardPaymentProcessor;
    private final StoreReader storeReader;

    public CardService(CardValidator cardValidator,
                       CardReader cardReader,
                       CardPaymentProcessor cardPaymentProcessor,
                       StoreReader storeReader) {
        this.cardValidator = cardValidator;
        this.cardReader = cardReader;
        this.cardPaymentProcessor = cardPaymentProcessor;
        this.storeReader = storeReader;
    }

    public CardPaymentResult pay(CardPaymentRequest request) {
        // 1. 카드 조회
        Card card = cardReader.readByCardNumber(request.getCardNumber());

        // 2. 카드 유효성 검증
        cardValidator.validate(card);

        // 3. 가맹점 확인
        storeReader.readByOrderId(request.getOrderId());

        // 4. 결제 처리
        return cardPaymentProcessor.process(card, request.getAmount());
    }
}

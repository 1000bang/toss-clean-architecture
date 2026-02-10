package com.tosspayments.payments.core.controller.card;

// ============================================================
// [Layer 원칙] Controller → Domain 방향 참조 (순방향)
// Presentation Layer → Business Layer (올바른 방향)
// ============================================================
import com.tosspayments.payments.core.domain.card.CardPaymentRequest;
import com.tosspayments.payments.core.domain.card.CardPaymentResult;
import com.tosspayments.payments.core.domain.card.CardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * [올바른 예시 - Layer]
 *
 * Controller(Presentation)가 Service(Business)를 참조 → 순방향 OK
 * HttpRequest DTO를 도메인 객체(CardPaymentRequest)로 변환하여 전달
 * Service는 Controller의 존재를 전혀 모른다
 */
@RestController
public class CardPaymentController {

    private final CardService cardService;

    public CardPaymentController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/payments/card")
    public CardPaymentHttpResponse addCardPayment(@RequestBody CardPaymentHttpRequest request) {
        // [핵심] HTTP DTO → 도메인 객체로 변환 (Presentation → Business 경계)
        CardPaymentRequest domainRequest = new CardPaymentRequest(
                request.getOrderId(),
                request.getCardNumber(),
                request.getAmount()
        );

        // 도메인 서비스 호출 (도메인 객체로 전달)
        CardPaymentResult result = cardService.pay(domainRequest);

        // [핵심] 도메인 결과 → HTTP 응답 DTO로 변환
        return new CardPaymentHttpResponse(
                result.getPaymentId(),
                result.getStatus(),
                result.getAmount()
        );
    }
}

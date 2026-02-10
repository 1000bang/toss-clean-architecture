package com.tosspayments.payments.core.controller.mobile;

// ============================================================
// [Layer 원칙] Controller → Domain 방향 참조 (순방향)
// ============================================================
import com.tosspayments.payments.core.domain.mobile.MobilePaymentRequest;
import com.tosspayments.payments.core.domain.mobile.MobilePaymentResult;
import com.tosspayments.payments.core.domain.mobile.MobileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * [올바른 예시 - Layer]
 *
 * 잘못된 예:
 *   MobileService가 이 Controller의 MobilePaymentHttpRequest를 import
 *   → Business Layer가 Presentation Layer를 역류 참조
 *
 * 올바른 예 (현재):
 *   Controller가 HttpRequest를 도메인 객체로 변환하여 Service에 전달
 *   Service는 Controller 패키지를 전혀 모른다
 */
@RestController
public class MobilePaymentController {

    private final MobileService mobileService;

    public MobilePaymentController(MobileService mobileService) {
        this.mobileService = mobileService;
    }

    @PostMapping("/payments/mobile")
    public MobilePaymentHttpResponse addMobilePayment(@RequestBody MobilePaymentHttpRequest request) {
        // [핵심] HTTP DTO → 도메인 객체로 변환
        MobilePaymentRequest domainRequest = new MobilePaymentRequest(
                request.getPhoneNumber(),
                request.getCarrier(),
                request.getAmount()
        );

        MobilePaymentResult result = mobileService.addPayment(domainRequest);

        // [핵심] 도메인 결과 → HTTP 응답으로 변환
        return new MobilePaymentHttpResponse(
                result.getPaymentId(),
                result.getStatus()
        );
    }
}

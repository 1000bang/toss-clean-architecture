package com.tosspayments.payments.core.domain.mobile;

// ============================================================
// [Layer 원칙] 올바른 import 예시
// - Business Layer(domain)는 Presentation Layer(controller)를 참조하지 않음!
// ============================================================

// 잘못된 예 (역류 참조):
// import com.tosspayments.payments.core.controller.MobilePaymentHttpRequest;  ← X 금지!
// import com.tosspayments.payments.core.controller.MobilePaymentHttpResponse; ← X 금지!

// 올바른 예 (현재):
// 같은 패키지의 도메인 객체만 사용 → import 불필요

import org.springframework.stereotype.Service;

/**
 * [올바른 예시 - Layer]
 *
 * 참조 방향: Controller → Service → Domain (단방향)
 * MobileService는 controller 패키지를 전혀 모른다.
 * Controller가 HttpRequest를 MobilePaymentRequest(도메인 객체)로 변환하여 넘겨준다.
 */
@Service
public class MobileService {

    public MobilePaymentResult addPayment(MobilePaymentRequest request) {
        // 비즈니스 로직: 도메인 객체만 사용
        String paymentId = "MOBILE-" + System.currentTimeMillis();
        return new MobilePaymentResult(paymentId, "SUCCESS");
    }
}

package com.tosspayments.payments.core.infrastructure.http;

// ============================================================
// [Module 원칙] 기술 구현체는 infrastructure에 격리
// Feign, RestTemplate, WebClient 등 특정 기술은 여기서만 사용
// ============================================================

// 실제 프로젝트에서는 여기에 외부 라이브러리를 import:
// import feign.Feign;
// import feign.httpclient.ApacheHttpClient;

import com.tosspayments.payments.core.domain.combine.ExternalApiClient;
import org.springframework.stereotype.Component;

/**
 * ExternalApiClient 인터페이스의 구현체
 *
 * 현재: 간단한 스텁 구현 (예시용)
 * 실제: Feign Client, RestTemplate 등으로 구현
 *       → 이 파일만 수정하면 되고, CombineService는 변경 없음!
 */
@Component
public class RestTemplateExternalApiClient implements ExternalApiClient {

    @Override
    public String call(String endpoint, String payload) {
        // 실제로는 Feign이나 RestTemplate으로 외부 API 호출
        return "response-from-" + endpoint;
    }
}

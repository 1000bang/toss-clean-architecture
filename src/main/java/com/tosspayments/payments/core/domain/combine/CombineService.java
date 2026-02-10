package com.tosspayments.payments.core.domain.combine;

// ============================================================
// [Module 원칙] 올바른 import 예시
// - 비즈니스 로직이 특정 라이브러리에 직접 의존하지 않음!
// ============================================================

// 잘못된 예 (기술 직접 의존):
// import com.amazonaws.cache.Cache;           ← X 금지!
// import com.amazonaws.cache.KeyConverter;    ← X 금지!
// import feign.Feign;                         ← X 금지!
// import feign.httpclient.ApacheHttpClient;   ← X 금지!

// 올바른 예 (현재):
// 같은 패키지의 인터페이스만 사용 → 외부 라이브러리 import 없음!

import org.springframework.stereotype.Service;

/**
 * [올바른 예시 - Module]
 *
 * 잘못된 예:
 *   CombineService가 AWS Cache, Feign 등 외부 라이브러리를 직접 import
 *   → 라이브러리 업데이트/교체 시 비즈니스 로직 수정 필요
 *
 * 올바른 예 (현재):
 *   도메인이 정의한 인터페이스(CacheClient, ExternalApiClient)만 사용
 *   실제 구현은 infrastructure 모듈에서 담당
 *   → 라이브러리가 바뀌어도 비즈니스 로직은 변경 없음
 */
@Service
public class CombineService {

    private final CacheClient cacheClient;
    private final ExternalApiClient externalApiClient;

    public CombineService(CacheClient cacheClient, ExternalApiClient externalApiClient) {
        this.cacheClient = cacheClient;
        this.externalApiClient = externalApiClient;
    }

    public void combine(CombineTarget target) {
        // 1. 캐시 조회 (어떤 캐시 기술인지 모름)
        String cached = cacheClient.get(target.getTargetId());

        if (cached == null) {
            // 2. 외부 API 호출 (어떤 HTTP 클라이언트인지 모름)
            String result = externalApiClient.call("/combine", target.getData());

            // 3. 캐시 저장
            cacheClient.put(target.getTargetId(), result);
        }
    }
}

package com.tosspayments.payments.core.domain.combine;

/**
 * [Module 원칙] 외부 API 호출 추상화 인터페이스
 *
 * 도메인이 "외부 API를 호출해야 한다"는 것만 알고,
 * 어떤 기술(Feign, RestTemplate, WebClient 등)로 구현되는지는 모른다.
 */
public interface ExternalApiClient {

    String call(String endpoint, String payload);
}

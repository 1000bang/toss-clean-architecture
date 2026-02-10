package com.tosspayments.payments.core.domain.combine;

/**
 * [Module 원칙] 캐시 추상화 인터페이스
 *
 * 도메인이 "캐시가 필요하다"는 것만 알고,
 * 어떤 기술(AWS ElastiCache, Redis, Caffeine 등)로 구현되는지는 모른다.
 */
public interface CacheClient {

    void put(String key, String value);

    String get(String key);
}

package com.tosspayments.payments.core.infrastructure.cache;

// ============================================================
// [Module 원칙] 기술 구현체는 infrastructure에 격리
// AWS Cache, Redis 등 특정 기술 의존성은 여기서만 발생
// ============================================================

// 실제 프로젝트에서는 여기에 외부 라이브러리를 import:
// import com.amazonaws.cache.Cache;
// import com.amazonaws.cache.KeyConverter;

import com.tosspayments.payments.core.domain.combine.CacheClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CacheClient 인터페이스의 구현체
 *
 * 현재: 인메모리 구현 (예시용)
 * 실제: AWS ElastiCache, Redis 등으로 교체 가능
 *       → 이 파일만 수정하면 되고, CombineService는 변경 없음!
 */
@Component
public class InMemoryCacheClient implements CacheClient {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    @Override
    public void put(String key, String value) {
        store.put(key, value);
    }

    @Override
    public String get(String key) {
        return store.get(key);
    }
}

package com.tosspayments.payments.core.infrastructure.persistence;

import com.tosspayments.payments.core.domain.store.Store;
import com.tosspayments.payments.core.domain.store.StoreReader;
import org.springframework.stereotype.Component;

@Component
public class JpaStoreReader implements StoreReader {

    @Override
    public Store readByOrderId(String orderId) {
        // 실제로는 JPA Repository를 통해 DB 조회
        return new Store("STORE-001", "테스트 가맹점");
    }
}

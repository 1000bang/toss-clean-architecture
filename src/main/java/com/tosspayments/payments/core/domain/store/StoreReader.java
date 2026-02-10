package com.tosspayments.payments.core.domain.store;

public interface StoreReader {

    Store readByOrderId(String orderId);
}

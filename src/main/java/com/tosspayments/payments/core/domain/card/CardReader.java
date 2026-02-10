package com.tosspayments.payments.core.domain.card;

/**
 * 카드 조회 포트 (인터페이스)
 * - 도메인이 인터페이스를 소유하고, 구현은 infrastructure에서 담당
 */
public interface CardReader {

    Card readByCardNumber(String cardNumber);

    CustomerCard readCustomerCard(String customerId, String cardNumber);
}

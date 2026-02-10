package com.tosspayments.payments.core.domain.mobile;

/**
 * [Layer 원칙] 도메인 레벨 요청 객체
 *
 * 잘못된 예:
 *   MobileService가 controller의 MobilePaymentHttpRequest를 직접 사용
 *   → Business Layer가 Presentation Layer를 참조 (역류!)
 *
 * 올바른 예 (현재):
 *   도메인 고유의 요청 객체를 사용
 *   Controller가 HttpRequest → 이 도메인 객체로 변환하여 전달
 */
public class MobilePaymentRequest {

    private final String phoneNumber;
    private final String carrier;
    private final long amount;

    public MobilePaymentRequest(String phoneNumber, String carrier, long amount) {
        this.phoneNumber = phoneNumber;
        this.carrier = carrier;
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCarrier() {
        return carrier;
    }

    public long getAmount() {
        return amount;
    }
}

package com.tosspayments.payments.core.controller.mobile;

/**
 * Presentation Layer 전용 HTTP 요청 DTO
 * - domain 패키지는 이 클래스의 존재를 모른다
 */
public class MobilePaymentHttpRequest {

    private String phoneNumber;
    private String carrier;
    private long amount;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}

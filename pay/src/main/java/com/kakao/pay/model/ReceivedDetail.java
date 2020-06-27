package com.kakao.pay.model;

public class ReceivedDetail {

    private long receivedMoney;
    private String userId;

    public long getReceivedMoney() {
        return receivedMoney;
    }

    public void setReceivedMoney(long receivedMoney) {
        this.receivedMoney = receivedMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

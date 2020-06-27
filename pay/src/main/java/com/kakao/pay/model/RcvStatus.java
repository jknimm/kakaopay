package com.kakao.pay.model;

import java.util.Date;
import java.util.List;

public class RcvStatus {

    /*뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료된 정보 ([받은 금액, 받은
            사용자 아이디] 리스트)*/
    private Date sendDate;
    private long sendMoney;
    private long totalReceivedMoney;
    private List<ReceivedDetail> receivedDetail;

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public long getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(long sendMoney) {
        this.sendMoney = sendMoney;
    }

    public long getTotalReceivedMoney() {
        return totalReceivedMoney;
    }

    public void setTotalReceivedMoney(long totalReceivedMoney) {
        this.totalReceivedMoney = totalReceivedMoney;
    }

    public List<ReceivedDetail> getReceivedDetail() {
        return receivedDetail;
    }

    public void setReceivedDetail(List<ReceivedDetail> receivedDetail) {
        this.receivedDetail = receivedDetail;
    }
}

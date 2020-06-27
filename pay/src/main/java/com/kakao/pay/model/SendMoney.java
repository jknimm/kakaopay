package com.kakao.pay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class SendMoney {

    public SendMoney() {
    }

    public SendMoney(String token, String userId, String roomId, long sendMoney, int sendCount, Date sendDate) {
        this.token = token;
        this.userId = userId;
        this.roomId = roomId;
        this.sendMoney = sendMoney;
        this.sendCount = sendCount;
        this.sendDate = sendDate;
    }

    @Id
    @Column(name="token")
    private String token;

    @Column(name="user_id")
    private String userId;

    @Column(name="room_id")
    private String roomId;

    @Column(name="send_money")
    private long sendMoney;

    @Column(name="send_count")
    private int sendCount;

    @Column(name="send_date")
    private Date sendDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public long getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(long sendMoney) {
        this.sendMoney = sendMoney;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

}

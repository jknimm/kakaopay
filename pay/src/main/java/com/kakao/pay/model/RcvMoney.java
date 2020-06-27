package com.kakao.pay.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RcvMoney {


    public RcvMoney(){

    }

    public RcvMoney(String token, String roomId, long rcv_money) {
        this.token = token;
        this.roomId = roomId;
        this.rcvMoney = rcv_money;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="seq")
    private long seq;

    @Column(name="token")
    private String token;

    @Column(name="room_id")
    private String roomId;

    @Column(name="user_id")
    private String userId;

    @Column(name="rcv_money")
    private long rcvMoney;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getRcvMoney() {
        return rcvMoney;
    }

    public void setRcvMoney(long rcvMoney) {
        this.rcvMoney = rcvMoney;
    }
}
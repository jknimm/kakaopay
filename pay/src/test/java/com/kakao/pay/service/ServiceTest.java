package com.kakao.pay.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class ServiceTest {

    public static final String TOKEN_1 = "testtoken01";
    public static final String TOKEN_2 = "testtoken02";
    public static final String NOT_EXIST_TOKEN = "notexisttoken";
    public static final String SEND_USER_1 = "senduser01";
    public static final String RCV_USER_1 = "rcvuser01";
    public static final String NOT_REV_USER = "notrecuser";
    public static final String ROOM_1 = "1";


    @Autowired ReceiveService receiveService;

    @Test
    void isDuplicatedUser(){
        boolean isDuplicate = receiveService.isDuplicatedUser(TOKEN_1, RCV_USER_1);
        Assert.assertEquals(true, isDuplicate);
    }

    @Test
    void isMemberOfRoom()
    {
        boolean isMemberOfRoom = receiveService.isMemberOfRoom(TOKEN_1, ROOM_1);
        Assert.assertEquals(true, isMemberOfRoom);

    }


    @Test
    void isAvailableHistory() {
        boolean isAvailableHistory = receiveService.isAvailableHistory(TOKEN_1);
        Assert.assertEquals(true, isAvailableHistory);
    }

    @Test
    void isMyMoney() {
        boolean isMyMoney = receiveService.isMyMoney(TOKEN_1, SEND_USER_1);
        Assert.assertEquals(true, isMyMoney);

    }

    @Test
    void isNotExpired() {
        boolean isNotExpired = receiveService.isNotExpired(TOKEN_2);
        Assert.assertEquals(false, isNotExpired);

    }

    @Test
    void isExistToken() {
        boolean isExistToken_true = receiveService.isExistToken(TOKEN_1);
        Assert.assertEquals(true, isExistToken_true);

        boolean isExistToken_false = receiveService.isExistToken(NOT_EXIST_TOKEN);
        Assert.assertEquals(false, isExistToken_false);


    }

}
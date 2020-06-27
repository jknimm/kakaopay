package com.kakao.pay.repository;

import com.kakao.pay.model.RcvMoney;
import com.kakao.pay.model.SendMoney;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository Test
 *
 * 실행을 위해서는 'test' 프로파일로 실행해주세요
 *  (application.yaml 설정파일에서 active: test 로 설정)
 * 테스트를 위한 사전 데이터는 메모리 해당 프로파일로 실행 시 메모리 DB에 자동을 생성됩니다.
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTest {

    @Autowired private SendRepository sendRepository ;
    @Autowired private RcvRepository rcvRepository;

    public static final String TOKEN_1 = "testtoken01";
    public static final String SEND_USER_1 = "senduser01";
    public static final String RCV_USER_1 = "rcvuser01";
    public static final String NOT_REV_USER = "notrecuser";

    @Test
    void save_send_money_test() {
        final SendMoney sendMoney = new SendMoney("testtoken", "testuser", "5", 10000, 5, new Date());
        final SendMoney savedSendMoney = sendRepository.save(sendMoney);
        Assert.assertEquals( sendMoney.getToken(), savedSendMoney.getToken());
    }

    @Test
    void get_send_money_by_token() {
        Optional<SendMoney> sendMoney = sendRepository.findById(TOKEN_1);
        Assert.assertEquals(SEND_USER_1, sendMoney.get().getUserId());
    }

    @Test
    void get_rcv_money_by_token_and_user_id_null() {
        List<RcvMoney> rcvMoneyList = rcvRepository.findByTokenAndUserIdNullOrderBySeqAsc(TOKEN_1);
        Assert.assertEquals(1, rcvMoneyList.size());
    }

    @Test
    void get_rcv_money_by_token_and_user_id_not_null() {
        List<RcvMoney> rcvMoneyList = rcvRepository.findByTokenAndUserIdNotNull(TOKEN_1);
        Assert.assertEquals(2, rcvMoneyList.size());
    }

    @Test
    void count_by_token_and_user_id_when_not_rec_user(){

    }

    @Test
    void count_by_token_and_user_id_null(){
        long count = rcvRepository.countByTokenAndUserIdNull(TOKEN_1);
        Assert.assertEquals(1,count);
    }
}
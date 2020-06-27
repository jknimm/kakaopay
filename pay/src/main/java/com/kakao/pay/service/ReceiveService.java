package com.kakao.pay.service;

import com.kakao.pay.exception.APIValidationException;
import com.kakao.pay.model.*;
import com.kakao.pay.repository.RcvRepository;
import com.kakao.pay.repository.SendRepository;
import com.kakao.pay.utility.APIErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReceiveService {

    private final SendRepository sendRepository;
    private final RcvRepository rcvRepository;

    @Autowired
    public ReceiveService(SendRepository sendRepository, RcvRepository rcvRepository) {
        this.sendRepository = sendRepository;
        this.rcvRepository = rcvRepository;
    }

    public String sendMoney(String token, String userId, String roomId, long money, int numberOfPeople){

        Date date = new Date();
        SendMoney sendMoney = new SendMoney(token, userId, roomId, money, numberOfPeople, date);
        sendRepository.save(sendMoney);

        distributeMoney(token, roomId, money, numberOfPeople);

        return token;
    }

    public boolean isDuplicatedUser(String token, String userId){

        //뿌리기 당한 사용자는 한번만 받을 수 있습니다.
        int count = rcvRepository.countByTokenAndUserId(token, userId);
        return count > 0;
    }

    public boolean isMemberOfRoom(String token, String roomId){

        Optional<SendMoney> optionalSendMoney = sendRepository.findById(token);
        if(optionalSendMoney.isPresent()) {
            SendMoney sendMoney = optionalSendMoney.get();
            return sendMoney.getRoomId().equals(roomId);
        }
        return false;

    }

    public boolean isAvailableHistory(String token){

        Optional<SendMoney> optionalSendMoney = sendRepository.findById(token);
        // 뿌린 건에 대한 조회는 7일 동안 할 수 있습니다.
        SendMoney sendMoney = optionalSendMoney.get();

        Date date = new Date();
        String nowTime = null;  // 현재시간 String 타입
        String expireTime = null; // 만료시간 String 타입

        // 보낸시간 + 10분 -> 만료시간 구하기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 포맷변경 ( 년월일 시분초)
        Calendar cal = Calendar.getInstance();
        cal.setTime(sendMoney.getSendDate());
        cal.add(Calendar.DATE, 7); // 7일 더하기
        expireTime =  simpleDateFormat.format(cal.getTime());

        // 현재시간
        nowTime = simpleDateFormat.format(date);

        // 현재시간 > 만료시간
        if(nowTime.compareTo(expireTime) > 0 ) {
            return false;
        }
        return true;
    }

    public boolean isMyMoney(String token, String userId ){

        Optional<SendMoney> optionalSendMoney = sendRepository.findById(token);
        if(optionalSendMoney.isPresent()) {
            SendMoney sendMoney = optionalSendMoney.get();
            return sendMoney.getUserId().equals(userId);
        }
        return false;
    }


    public boolean isNotExpired(String token){
        Optional<SendMoney> optionalSendMoney = sendRepository.findById(token);
        if(optionalSendMoney.isPresent()) {

            // 뿌린 건은 10분간만 유효합니다.
            SendMoney sendMoney = optionalSendMoney.get();

            Date date = new Date();
            String nowTime = null;  // 현재시간 String 타입
            String expireTime = null; // 만료시간 String 타입

            // 보낸시간 + 10분 -> 만료시간 구하기
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 포맷변경 ( 년월일 시분초)
            Calendar cal = Calendar.getInstance();
            cal.setTime(sendMoney.getSendDate());
            cal.add(Calendar.MINUTE, 10); // 10분 더하기
            expireTime =  simpleDateFormat.format(cal.getTime());

            // 현재시간
            nowTime = simpleDateFormat.format(date);

            // 현재시간 > 만료시간
            if(nowTime.compareTo(expireTime) > 0 ) {
                return false;
            }
            return true;
        }

        return true;
    }

    public boolean isExistToken(String token){

        Optional<SendMoney> optionalSendMoney = sendRepository.findById(token);
        return optionalSendMoney.isPresent();
    }

    public void checkRcvValidation( String token, String userId, String roomId){

        // token 이 존재하지 않습니다.
        if(!isExistToken(token)){
            throw new APIValidationException(APIErrorType.NOT_EXIST_TOKEN);

        }

        // 뿌리기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수있습니다.
        if(!isMemberOfRoom(token, roomId)) {
            throw new APIValidationException(APIErrorType.NOT_MEMBER);
        }

        // 한번만 받을수 있습니다.
        if(isDuplicatedUser(token, userId)) {
            throw new APIValidationException(APIErrorType.DUPLICATED_RCV);
        }

        //자신이 뿌리기한 건은 자신이 받을 수 없습니다.
        if(isMyMoney(token, userId)) {
            throw new APIValidationException(APIErrorType.MY_MONEY);
        }

        //뿌린 건은 10분간만 유효합니다. 뿌린지 10분이 지난 요청에 대해서는 받기실패 응답이 내려가야 합니다.
        if(isNotExpired(token)){
            throw new APIValidationException(APIErrorType.EXPIRED);
        }

    }

    public RcvMoney rcvMoney(String token, String userId){

        if(rcvRepository.countByTokenAndUserIdNull(token)==0){
            throw new APIValidationException(APIErrorType.INVALID_REQUEST);
        }

        List<RcvMoney> rcvMoneyList = rcvRepository.findByTokenAndUserIdNullOrderBySeqAsc(token);

        RcvMoney rcvMoney = rcvMoneyList.get(0);
        rcvMoney.setUserId(userId);

        rcvRepository.save(rcvMoney);

        return rcvMoney;
    }

    public void distributeMoney(String token, String roomId, long money, int numberOfPeople){
        long moneySum = 0;
        long restMoney = money;

        for (int i=1; i<=numberOfPeople; i++){
            long distributeMoney = 0;

            if(numberOfPeople == i) { // 마지막 사람 금액Set (뿌린금액 - 랜덤금액 합계)
                distributeMoney = money - moneySum;
            } else {
                double randomNumber = Math.random();
                int randomInt = (int) (randomNumber * 100); // 랜덤 2자리 숫자 (금액분배 비율)
                distributeMoney = (long) Math.floor(restMoney * 0.8 * (randomInt * 0.01)); // 총금액의 80%에서 랜덤비율로 금액 배분
                moneySum += distributeMoney; // 마지막 사람의 금액Set 위한, 이전회차 분배금액 합계
                restMoney -= distributeMoney; // 다음사람 분배 위한, 잔여금액
            }
            RcvMoney rcvMoney = new RcvMoney(token, roomId, distributeMoney);
            rcvRepository.save(rcvMoney);
        }

    }

    public void checkReceiveStatusValidation(String token, String userId){

        //뿌린 건은 10분간만 유효합니다. 뿌린지 10분이 지난 요청에 대해서는 받기실패 응답이 내려가야 합니다.
        if(!isMyMoney(token,userId)){
            throw new APIValidationException(APIErrorType.NOT_MY_NOMEY);
        }

        if(!isAvailableHistory(token)){
            throw new APIValidationException(APIErrorType.BEFORE_WEEKS);

        }

    }

    public RcvStatus getReceiveStatus(String token, String userId){

        RcvStatus rcvStatus = new RcvStatus();

        checkReceiveStatusValidation(token, userId);

        Optional<SendMoney> sendMoneyOptional = sendRepository.findById(token);
        if(!sendMoneyOptional.isPresent()){
            throw new APIValidationException(APIErrorType.NOT_EXIST_TOKEN);
        }

        SendMoney sendMoney = sendMoneyOptional.get();

        rcvStatus.setSendDate(sendMoney.getSendDate());
        rcvStatus.setSendMoney(sendMoney.getSendMoney());

        setReceiveDetail(rcvStatus, token);

        return rcvStatus;

    }

    private void setReceiveDetail(RcvStatus rcvStatus, String token){

        List<RcvMoney> rcvMoneyList = rcvRepository.findByTokenAndUserIdNotNull(token);
        List<ReceivedDetail> receivedDetails = new ArrayList<>();
        long totalReceivedMoney=0;

        for(RcvMoney rcvMoney : rcvMoneyList){
            ReceivedDetail receivedDetail = new ReceivedDetail();
            receivedDetail.setUserId(rcvMoney.getUserId());
            receivedDetail.setReceivedMoney(rcvMoney.getRcvMoney());
            receivedDetails.add(receivedDetail);
            totalReceivedMoney += rcvMoney.getRcvMoney();
        }

        rcvStatus.setTotalReceivedMoney(totalReceivedMoney);
        rcvStatus.setReceivedDetail(receivedDetails);

    }

}

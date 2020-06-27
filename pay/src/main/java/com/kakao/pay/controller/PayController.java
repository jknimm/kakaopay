package com.kakao.pay.controller;

import com.kakao.pay.exception.APIValidationException;
import com.kakao.pay.model.APIError;
import com.kakao.pay.model.RcvStatus;
import com.kakao.pay.service.ReceiveService;
import com.kakao.pay.utility.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PayController {

    private ReceiveService receiveService;

    @Autowired
    public PayController(ReceiveService receiveService) {
        this.receiveService = receiveService;
    }

    /**
     *
     * @param request
     * @param money
     * @param numberOfPeople
     * @return
     */

    @PostMapping(value="/api/v1/money/{money}/{numberOfPeople}")
    public String sendMoney(HttpServletRequest request, @PathVariable long money, @PathVariable int numberOfPeople){

        // 3자리 문자열 토큰 생성
        String token = TokenGenerator.generateToken();

        String userId = request.getHeader("X-USER-ID");
        String roomId = request.getHeader("X-ROOM-ID");


        receiveService.sendMoney(token, userId, roomId, money, numberOfPeople);

        System.out.println("User ID:" + userId);
        System.out.println("Room ID:" + roomId);

        return token;

    }

    /**
     *
     * @param request
     * @return
     */
    @GetMapping(value="/api/v1/money/{token}")
    public long receiveMoney(HttpServletRequest request, @PathVariable String token){

        String userId = request.getHeader("X-USER-ID");
        String roomId = request.getHeader("X-ROOM-ID");

        receiveService.checkRcvValidation(token, userId, roomId);

        return receiveService.rcvMoney(token, userId).getRcvMoney();

    }

    /**
     *
     * @param request
     * @param token
     * @return
     */
    @GetMapping(value="/api/v1/money/history/{token}")
    public RcvStatus getMoneyHistory(HttpServletRequest request, @PathVariable String token){

        String userId = request.getHeader("X-USER-ID");
        return receiveService.getReceiveStatus(token, userId);

    }

    @ExceptionHandler({APIValidationException.class})
    public ResponseEntity<APIError> handleException(APIValidationException ex) {
        APIError errorResponse = new APIError(ex.getApiErrorType().getErrorCode(),ex.getApiErrorType().toString(), ex.getApiErrorType().getErrorDescription());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

}

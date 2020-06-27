package com.kakao.pay.utility;

public enum APIErrorType {

    /**
     * Error code 1XX : 1번 API Error
     */

    NOT_EXIST_TOKEN("1001", "The token which you request not exist."),

    /**
     * Error code 2XX : 2번 API Error
     */
    // 뿌리기 당한 사용자는 한번만 받을 수 있습니다.
    DUPLICATED_RCV("2001", "받기는 한번만 가능합니다."),

    // 자신이 뿌리기한 건은 자신이 받을 수 없습니다.
    MY_MONEY("2002", "자신의 뿌리기 건은 받을 수 없습니다."),

    // 뿌리기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수있습니다.
    NOT_MEMBER("2003","뿌리기된 대화방의 사용자가 아닙니다."),

    // 뿌린 건은 10분간만 유효합니다. 뿌린지 10분이 지난 요청에 대해서는 받기실패 응답이 내려가야 합니다.
    EXPIRED("2004", "뿌린 후 10분 이내만 유효 합니다."),

    /**
     * Error code 3XX : 3번 API Error
     */
    NOT_MY_NOMEY("3001", "뿌린 사람 자신만 조회 가능 합니다."),
    BEFORE_WEEKS("3002", "뿌린 후 7일간만 조회 가능 합니다."),

    INVALID_REQUEST("9999","잘못된 요청입니다.");

    private String errorCode;
    private String errorDescription;

    APIErrorType(String errorCode, String errorDescription){
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}

package com.kakao.pay.exception;

import com.kakao.pay.utility.APIErrorType;

public class APIValidationException extends RuntimeException{

    private APIErrorType apiErrorType;

    public APIValidationException(APIErrorType apiErrorType)
    {

        super(apiErrorType.getErrorDescription());
        this.apiErrorType = apiErrorType;
    }

    public APIErrorType getApiErrorType() {
        return apiErrorType;
    }

    public void setApiErrorType(APIErrorType apiErrorType) {
        this.apiErrorType = apiErrorType;
    }
}

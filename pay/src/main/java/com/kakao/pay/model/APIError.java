package com.kakao.pay.model;

import java.util.Date;

public class APIError {

    /**
     * IETF  RFC 7807 https://tools.ietf.org/html/rfc7807
     *
     */


    Date timestamp;

    // 에러를 분류하기 위한 URI 식별자
    String type;

    // 사람이 읽을 수 있는 간단한 에러에 대한 메세지
    String title;

    //  detail - 사람이 읽을 수 있는 에러에 대한 설명
    String detail;

    // intance - 에러가 발생한 URI
    String instance;

    public APIError(String type, String title, String detail){
        this.timestamp = new Date();
        this.type = type;
        this.title = title;
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }
}

package me.migachev.accountmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import me.migachev.accountmanager.enums.ResponseCode;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response<T> {
    ResponseCode resopnseCode;
    T payload;
    String resultText;

    public Response(ResponseCode resopnseCode) {
        this.resopnseCode = resopnseCode;
    }

    public ResponseCode getResopnseCode() {
        return resopnseCode;
    }

    public void setResopnseCode(ResponseCode resopnseCode) {
        this.resopnseCode = resopnseCode;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }
}

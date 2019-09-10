package com.smartwizard.vos;

public class ErrorVO {
    public enum TYPE {
        ERROR, NEUTRAL, POSITIVE, DIALOG
    }
    //close Type 1 for positive , 0 for negative
    private Object errorMsg = "";
    private TYPE errorType ;

    public ErrorVO(Object msg, TYPE errorType) {
        this.errorMsg = msg;
        this.errorType = errorType;
    }

    public ErrorVO() {
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public TYPE getErrorType() {
        return errorType;
    }

    public void setErrorType(TYPE errorType) {
        this.errorType = errorType;
    }
}

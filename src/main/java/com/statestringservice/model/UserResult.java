package com.statestringservice.model;

public class UserResult {

    private String userId;

    private String result;

    public UserResult(String userId, String result) {
        this.userId = userId;
        this.result = result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getresult() {
        return result;
    }

    public void setresult(String result) {
        this.result = result;
    }

}
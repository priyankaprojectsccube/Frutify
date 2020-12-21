package com.app.fr.fruiteefy.Util;

public class MessageClass {


    Long date;
    String user_one;
    String message;


    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getUser_one() {
        return user_one;
    }

    public void setUser_one(String user_one) {
        this.user_one = user_one;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageClass() {
    }

    public MessageClass(Long date, String user_one, String message) {
        this.date = date;
        this.user_one = user_one;
        this.message = message;
    }
}

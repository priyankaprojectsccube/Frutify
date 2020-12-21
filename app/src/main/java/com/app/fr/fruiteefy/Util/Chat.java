package com.app.fr.fruiteefy.Util;

public class Chat {


    private Long date;
    private String message;
    private String user_one;


    public Chat() {
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_one() {
        return user_one;
    }

    public void setUser_one(String user_one) {
        this.user_one = user_one;
    }

    public Chat(Long date, String message, String user_one) {
        this.date = date;
        this.message = message;
        this.user_one = user_one;
    }
}


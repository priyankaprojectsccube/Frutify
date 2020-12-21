package com.app.fr.fruiteefy.Util;

public class UserChatlist implements Comparable<UserChatlist> {

    public String userId;
    public String username;
    public String token;
    public String userprofile;
    private Long datetime;

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public String getUserprofile() {
        return userprofile;
    }

    public void setUserprofile(String userprofile) {
        this.userprofile = userprofile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public int compareTo(UserChatlist o) {
        return getDatetime().compareTo(o.getDatetime());
    }
}

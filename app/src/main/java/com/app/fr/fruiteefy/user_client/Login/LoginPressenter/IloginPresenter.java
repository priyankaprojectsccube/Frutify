package com.app.fr.fruiteefy.user_client.Login.LoginPressenter;

public interface IloginPresenter {
    public void doLogin(String email,String pass,String fcmtoken);
    public void validateData(String email,String Pass);
}

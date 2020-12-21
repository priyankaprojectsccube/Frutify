package com.app.fr.fruiteefy.user_client.Login.LoginModel;

import android.content.Context;

public interface IloginModel {

    public  void doLogin(String email, String pass,String fcmtoken, onLoginListner onLoginListner, Context  context);

    interface onLoginListner{
        void onLoginSucess(String status);
        void onLoginFailure(Throwable t);
    }
}

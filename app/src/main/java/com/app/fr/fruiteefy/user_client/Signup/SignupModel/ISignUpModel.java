package com.app.fr.fruiteefy.user_client.Signup.SignupModel;

import android.content.Context;

public interface ISignUpModel {

    public  void doSignUp(final String fname,final String lname,final String email,final String address,final String lat,final String lon,final String city,final String country,final String state,final String phono,final String pass,final String conpass,final String zip,final String type, onSignUpListner onsignUpListner, Context context);

    interface onSignUpListner{
        void onSignUpSucess(String status);
        void onSignUpFailure(Throwable t);
    }
}

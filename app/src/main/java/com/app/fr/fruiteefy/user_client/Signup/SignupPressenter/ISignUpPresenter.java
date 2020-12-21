package com.app.fr.fruiteefy.user_client.Signup.SignupPressenter;

public interface ISignUpPresenter {
    public void doSignUp(final String fname, final String lname, final String email,final String address, final String lat,final String lon,final String city,final String country,final String state,final String phono,final String pass,final String conpass,final String zip,final String type);
    public void validateData(final String fname, final String lname, final String email,final String address, final String lat,final String lon,final String city,final String country,final String state,final String phono,final String pass,String conpass,String type);
}



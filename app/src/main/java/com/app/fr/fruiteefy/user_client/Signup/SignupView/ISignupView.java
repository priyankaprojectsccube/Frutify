package com.app.fr.fruiteefy.user_client.Signup.SignupView;

public interface ISignupView {

    public void getSignupResult(String status);
    public void setProgressbar(int Visibility);
    public void onValidatedata(int position);
    public void  onSignUpFailure(Throwable t);
}

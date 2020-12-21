package com.app.fr.fruiteefy.user_client.Login.LoginView;

public interface IloginView {

    public void getLoginResult(String status);
    public void setProgressbar(int Visibility);
    public void onValidatedata(int position);
    public void  onLoginFailure(Throwable t);
}

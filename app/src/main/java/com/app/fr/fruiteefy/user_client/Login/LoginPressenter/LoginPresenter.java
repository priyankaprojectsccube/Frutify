package com.app.fr.fruiteefy.user_client.Login.LoginPressenter;

import android.content.Context;

import com.app.fr.fruiteefy.user_client.Login.LoginModel.IloginModel;
import com.app.fr.fruiteefy.user_client.Login.LoginModel.LoginModel;
import com.app.fr.fruiteefy.user_client.Login.LoginView.IloginView;

public class LoginPresenter implements IloginPresenter,IloginModel.onLoginListner
{

    Context context;
    IloginView iloginView;
    IloginModel iloginModel;

    public LoginPresenter(Context context, IloginView iloginView) {
        this.context = context;
        this.iloginView = iloginView;
        iloginModel=new LoginModel();
    }

    @Override
    public void doLogin(String email, String pass,String fcmtoken) {
        iloginView.setProgressbar(1);
        iloginModel.doLogin(email,pass,fcmtoken,this,context);
    }

    @Override
    public void validateData(String email, String Pass) {
        String email_pattern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if(email.isEmpty()){
            iloginView.onValidatedata(1);
        }

        else if(!email.matches(email_pattern)){
            iloginView.onValidatedata(2);
        }

        else if(Pass.isEmpty()){
            iloginView.onValidatedata(3);
        }
        else if(Pass.length()<5){
            iloginView.onValidatedata(4);
        }
        else {
            iloginView.onValidatedata(5);
        }

    }

    @Override
    public void onLoginSucess(String status) {

        iloginView.setProgressbar(2);
        iloginView.getLoginResult(status);
    }

    @Override
    public void onLoginFailure(Throwable t) {
        iloginView.setProgressbar(2);
        iloginView.onLoginFailure(t);

    }
}

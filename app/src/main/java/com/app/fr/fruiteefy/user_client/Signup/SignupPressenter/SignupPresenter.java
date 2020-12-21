package com.app.fr.fruiteefy.user_client.Signup.SignupPressenter;

import android.content.Context;

import com.app.fr.fruiteefy.user_client.Signup.SignupModel.ISignUpModel;
import com.app.fr.fruiteefy.user_client.Signup.SignupModel.SignupModel;
import com.app.fr.fruiteefy.user_client.Signup.SignupView.ISignupView;

public class SignupPresenter implements ISignUpPresenter,ISignUpModel.onSignUpListner{

    Context context;
    ISignupView iSignupView;
    ISignUpModel iSignUpModel;

    public SignupPresenter( ISignupView iSignupView, Context context) {
        this.iSignupView = iSignupView;
        this.context = context;
        iSignUpModel=new SignupModel();
    }



    @Override
    public void doSignUp(String fname, String lname, String email, String address, String lat, String lon, String city, String country, String state, String phono, String pass,String conpass,String zip,String type) {
        iSignUpModel.doSignUp( fname,lname,  email, address, lat, lon,  city, country,  state,  phono,  pass,conpass, zip, type,this,context);

    }

    @Override
    public void validateData(String fname, String lname, String email, String address, String lat, String lon, String city, String country, String state, String phono, String pass,String conpass,String type) {


        String email_pattern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if(fname.isEmpty()){
            iSignupView.onValidatedata(1);
        }
        else if(lname.isEmpty()){
            iSignupView.onValidatedata(2);
        }
        else if(email.isEmpty()){
            iSignupView.onValidatedata(3);
        }
        else if(!email.matches(email_pattern)){
            iSignupView.onValidatedata(4);
        }
        else if(phono.isEmpty()){
            iSignupView.onValidatedata(5);
        }
        else if(address.isEmpty()){
            iSignupView.onValidatedata(6);
        }
        else if(pass.isEmpty()){
            iSignupView.onValidatedata(7);
        }
        else if(conpass.isEmpty()){
            iSignupView.onValidatedata(8);
        }
        else if(!pass.equals(conpass)){
            iSignupView.onValidatedata(9);
        }
        else if(type.equals("")) {
            iSignupView.onValidatedata(10);
        }
        else{
            iSignupView.onValidatedata(11);
        }



    }

    @Override
    public void onSignUpSucess(String status) {
        iSignupView.getSignupResult(status);
    }

    @Override
    public void onSignUpFailure(Throwable t) {
        iSignupView.onSignUpFailure(t);
    }
}

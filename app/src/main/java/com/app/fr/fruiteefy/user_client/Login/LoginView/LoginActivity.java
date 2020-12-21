package com.app.fr.fruiteefy.user_client.Login.LoginView;

import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.app.fr.fruiteefy.SetBankAccounttype;
import com.app.fr.fruiteefy.user_client.profile.EditprofileActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Validation;
import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;
import com.app.fr.fruiteefy.user_client.Login.LoginPressenter.IloginPresenter;
import com.app.fr.fruiteefy.user_client.Login.LoginPressenter.LoginPresenter;
import com.app.fr.fruiteefy.user_client.Signup.RegisterAdditionalFieldActivity;
import com.app.fr.fruiteefy.user_client.Signup.SignupView.SignUpActivity;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;
import com.app.fr.fruiteefy.user_picorear.UserPicorearHomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements IloginView{
TextView tv_new_user,skip;
EditText edit_mail,edit_pass;
Button btn_login,loginbygmail;
View parentLayout;
    LoginManager loginManager;
    CallbackManager mCallbackManager;
GoogleSignInClient mGoogleSignInClient;
IloginPresenter iloginPresenter;
    List<String> permissionNeeds;
Button fblogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.login));
        initView();


        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            int versioncode=pInfo.versionCode;

            RequestQueue requestQueue=Volley.newRequestQueue(LoginActivity.this);

            StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("app_version"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("dfdfs",response);

                    try {
                        JSONObject jsonObject=new JSONObject(response);
                       if(jsonObject.getInt("app_version")>versioncode){

                           final Dialog dialog = new Dialog(LoginActivity.this); // Context, this, etc.
                           dialog.setContentView(R.layout.layout_versiondialog);
                           dialog.setCancelable(false);
                           dialog.show();

                           TextView updateapp;
                           updateapp=dialog.findViewById(R.id.updateapp);

                           updateapp.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.app.fr.fruiteefy"));
                                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                                   startActivity(intent);
                               }
                           });

                       }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(stringRequest);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        FacebookSdk.sdkInitialize(getApplicationContext());


            Log.d("fdsfdfdf",PrefManager.getUserId(LoginActivity.this));




        if(PrefManager.IsLogin(LoginActivity.this)){

            if(PrefManager.getAddress(LoginActivity.this).equals("") || PrefManager.getdob(LoginActivity.this).equals("null")){
                Intent intent = new Intent(LoginActivity.this, EditprofileActivity.class);
               intent.putExtra("activity","home");
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                intent.putExtra("homeact", "home");
                startActivity(intent);
            }
        }


        iloginPresenter=new LoginPresenter(LoginActivity.this,this);
        tv_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iloginPresenter.validateData(edit_mail.getText().toString(),edit_pass.getText().toString());

            }
        });

        permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "public_profile");

        fblogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginManager.getInstance().logOut();
                PrefManager.LogOut(LoginActivity.this);
                mCallbackManager = CallbackManager.Factory.create();
                loginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY);


                loginManager.getInstance().logInWithReadPermissions(LoginActivity.this, permissionNeeds);

                loginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResults) {

                                Log.d("fdfsfdf", String.valueOf(loginResults.getAccessToken()));



                                GraphRequest request = GraphRequest.newMeRequest(
                                        loginResults.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(
                                                    JSONObject object,
                                                    GraphResponse response) {
                                                // Application co());

                                                Log.d("sddadad", object.toString());



                                                CustomUtil.ShowDialog(LoginActivity.this,true);
                                                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                                                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("facebook_login"), new com.android.volley.Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Log.d("dsdadada", response);

                                                        CustomUtil.DismissDialog(LoginActivity.this);

                                                        try {
                                                            JSONObject jsonObject=new JSONObject(response);
                                                            Log.d("sdasdas", String.valueOf(jsonObject.getInt("bank_acc_type")));
                                                            if(jsonObject.getInt("bank_acc_type")==1){


                                                                PrefManager.setIsLogin(LoginActivity.this,true);
                                                                JSONObject jsonObject1=jsonObject.getJSONObject("user_details");

                                                                if(jsonObject1.getString("address").equals("") || jsonObject1.getString("birthday").equals("null")) {
                                                                    Intent intent = new Intent(LoginActivity.this, EditprofileActivity.class);
                                                                    intent.putExtra("activity","home");
                                                                    startActivity(intent);
                                                                }
                                                                else{
                                                                    Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                                                                    intent.putExtra("homeact", "home");
                                                                    startActivity(intent);
                                                                }

                                                                PrefManager.setdob(jsonObject1.getString("birthday"), LoginActivity.this);

                                                                PrefManager.setMobile(jsonObject1.getString("phoneno"),LoginActivity.this);

                                                                PrefManager.setUserId(jsonObject1.getString("userid"),LoginActivity.this);
                                                                PrefManager.setFirstName(jsonObject1.getString("firstname"),LoginActivity.this);
                                                                PrefManager.setLastName(jsonObject1.getString("lastname"),LoginActivity.this);
                                                                PrefManager.setEmailId(jsonObject1.getString("email"),LoginActivity.this);
                                                                PrefManager.setMobile(jsonObject1.getString("phoneno"),LoginActivity.this);
                                                                PrefManager.setAddress(jsonObject1.getString("address"),LoginActivity.this);
                                                                PrefManager.setLAT(jsonObject1.getString("lat"),LoginActivity.this);
                                                                PrefManager.setLNG(jsonObject1.getString("lng"),LoginActivity.this);
                                                                PrefManager.setProfilePic(jsonObject1.getString("profile_pic"),LoginActivity.this);
                                                                PrefManager.setIsPico(jsonObject1.getString("is_pico"),LoginActivity.this);
                                                                PrefManager.setISCliant(jsonObject1.getString("is_client"),LoginActivity.this);
                                                                PrefManager.setSubscribepico(jsonObject1.getString("subscribe_status"),LoginActivity.this);
                                                                PrefManager.setIsAnti(jsonObject1.getString("is_anti"),LoginActivity.this);
                                                                PrefManager.setZip(jsonObject1.getString("zip"),LoginActivity.this);
                                                                PrefManager.setCity(jsonObject1.getString("city"),LoginActivity.this);
                                                                PrefManager.setSTATE(jsonObject1.getString("state"),LoginActivity.this);
                                                                PrefManager.setCOUNTRY(jsonObject1.getString("country"),LoginActivity.this);
                                                                PrefManager.setIam(jsonObject1.getString("i_am"),LoginActivity.this);
                                                                PrefManager.setCompanyname(jsonObject1.getString("companyname"),LoginActivity.this);
                                                                Toast.makeText(LoginActivity.this, jsonObject.getString("companyname"), Toast.LENGTH_SHORT).show();

                                                            }
                                                            else if(jsonObject.getInt("bank_acc_type")==0){


                                                                Intent intent = new Intent(LoginActivity.this, SetBankAccounttype.class);
                                                                intent.putExtra("socialid",jsonObject.getString("social_id"));
                                                                intent.putExtra("email",jsonObject.getString("email"));
                                                                intent.putExtra("userid",jsonObject.getString("user_id"));
                                                                startActivity(intent);

                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }


                                                    }
                                                }, new com.android.volley.Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.d("dsdadada", error.toString());

                                                        NetworkResponse networkResponse=error.networkResponse;
                                                            String str = new String(networkResponse.data);
                                                            Log.d("dfdsfds", str);


                                                        CustomUtil.DismissDialog(LoginActivity.this);
                                                    }
                                                })
                                                {

                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {

                                                        Map<String, String> param = new HashMap<>();
                                                        try {

                                                            param.put("facebook_id", object.getString("id"));
                                                            param.put("email", object.getString("email"));
                                                            param.put("fcm_token",FirebaseInstanceId.getInstance().getToken());

                                                            if (object.has("name")) {
                                                                String[] mStrName = object.getString("name").split(" ");
                                                                if (mStrName.length == 2) {
                                                                    param.put("first_name", mStrName[0]);
                                                                    param.put("last_name", mStrName[1]);
                                                                } else {
                                                                    param.put("first_name", mStrName[0]);
                                                                    param.put("last_name", " ");

                                                                }

                                                            }



                                                            //  Log.d("dsdsd", object.getString("id"));


                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        return param;
                                                    }

                                                };

                                                requestQueue.add(stringRequest);


                                            }
                                        });

                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "email,name");
                                request.setParameters(parameters);
                                request.executeAsync();

                            }

                            @Override
                            public void onCancel() {


                                Log.d("sddadad", "facebook login canceled");

                            }


                            @Override
                            public void onError(FacebookException e) {

                                e.printStackTrace();

                                Log.d("sddadad", "facebook login failed error");

                            }
                        });





            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.EMAIL))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Log.d("sdadasda", String.valueOf(mGoogleSignInClient));


        loginbygmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mGoogleSignInClient.signOut();



                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 33);
            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent().hasExtra("data")){

                    Log.d("dasdasd",getIntent().getStringExtra("data"));
                    if (getIntent().getStringExtra("data").equals("login")) {
                        finish();
                    }

                    if (getIntent().getStringExtra("data").equals("home")) {
                        Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                        intent.putExtra("homeact", "home");
                        startActivity(intent);
                        finish();
                    }

                    if (getIntent().getStringExtra("data").equals("myorder")) {
                        Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                        intent.putExtra("homeact", "home");
                        startActivity(intent);
                        finish();
                    }

                    if (getIntent().getStringExtra("data").equals("basket")) {
                        Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                        intent.putExtra("homeact", "home");
                        startActivity(intent);
                        finish();
                    }



                    if (getIntent().getStringExtra("data").equals("antigaspi")) {
                        Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                        intent.putExtra("homeact", "home");
                        startActivity(intent);;
                        finish();
                    }


                    if (getIntent().getStringExtra("data").equals("picoact")) {
                        Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                        intent.putExtra("homeact", "home");
                        startActivity(intent);
                        finish();
                    }

                }

                else {
                    Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                    intent.putExtra("homeact", "home");
                    startActivity(intent);
                    finish();
                }


            }
        });
    }

    public void  initView()
    {
        tv_new_user=findViewById(R.id.tv_new_user);
        loginbygmail=findViewById(R.id.loginbygmail);
        btn_login=findViewById(R.id.btn_login);
        fblogin=findViewById(R.id.fblogin);
        edit_mail=findViewById(R.id.edt_email);
        edit_pass=findViewById(R.id.edt_pass);
        skip=findViewById(R.id.skip);
        parentLayout = findViewById(android.R.id.content);

    }

    @Override
    public void getLoginResult(String status) {
     CustomUtil.DismissDialog(LoginActivity.this);
          Log.d("asdsd",status);

        try {
            JSONObject jsonObject=new JSONObject(status);
            if(jsonObject.getString("status").equals("1")) {
                PrefManager.setIsLogin(LoginActivity.this, true);
                JSONObject jsonObject1 = jsonObject.getJSONObject("user_details");
                PrefManager.setdob(jsonObject1.getString("birthday"), LoginActivity.this);

                PrefManager.setMobile(jsonObject1.getString("phoneno"), LoginActivity.this);
                PrefManager.setUserId(jsonObject1.getString("userid"), LoginActivity.this);
                PrefManager.setFirstName(jsonObject1.getString("firstname"), LoginActivity.this);
                PrefManager.setLastName(jsonObject1.getString("lastname"), LoginActivity.this);
                PrefManager.setEmailId(jsonObject1.getString("email"), LoginActivity.this);
                PrefManager.setMobile(jsonObject1.getString("phoneno"), LoginActivity.this);
                PrefManager.setAddress(jsonObject1.getString("address"), LoginActivity.this);
                PrefManager.setLAT(jsonObject1.getString("lat"), LoginActivity.this);
                PrefManager.setLNG(jsonObject1.getString("lng"), LoginActivity.this);
                PrefManager.setProfilePic(jsonObject1.getString("profile_pic"), LoginActivity.this);
                PrefManager.setIsPico(jsonObject1.getString("is_pico"), LoginActivity.this);
                PrefManager.setISCliant(jsonObject1.getString("is_client"), LoginActivity.this);
                PrefManager.setSubscribepico(jsonObject1.getString("subscribe_status"), LoginActivity.this);
                PrefManager.setIsAnti(jsonObject1.getString("is_anti"), LoginActivity.this);
                PrefManager.setZip(jsonObject1.getString("zip"), LoginActivity.this);
                PrefManager.setCity(jsonObject1.getString("city"), LoginActivity.this);
                PrefManager.setSTATE(jsonObject1.getString("state"), LoginActivity.this);
                PrefManager.setCOUNTRY(jsonObject1.getString("country"), LoginActivity.this);
                PrefManager.setIam(jsonObject1.getString("i_am"), LoginActivity.this);
                PrefManager.setCompanyname(jsonObject1.getString("companyname"), LoginActivity.this);
                Toast.makeText(this, jsonObject.getString("messge"), Toast.LENGTH_SHORT).show();


                if(jsonObject1.getString("address").equals("") || jsonObject1.getString("birthday").equals("null")){
                    Intent intent = new Intent(LoginActivity.this, EditprofileActivity.class);
                    intent.putExtra("activity","home");
                    startActivity(intent);
                }
                    else{
                    if (getIntent().hasExtra("data")) {

                        if (getIntent().getStringExtra("data").equals("login")) {
                            finish();
                        } else if (getIntent().getStringExtra("data").equals("home")) {

                            Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                            intent.putExtra("homeact", "home");
                            startActivity(intent);
                        } else if (getIntent().getStringExtra("data").equals("basket")) {
                            Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                            intent.putExtra("homeact", "basket");
                            startActivity(intent);
                        } else if (getIntent().getStringExtra("data").equals("picoact")) {


                            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("profile"), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("ssdd", response);

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        if (jsonObject.getString("status").equals("1")) {


                                            JSONObject jsonObject1 = jsonObject.getJSONObject("profiledata");
                                            if (PrefManager.IsLogin(LoginActivity.this)) {

                                                if (jsonObject1.getString("is_pico").equals("0")) {
                                                    Intent intent = new Intent(LoginActivity.this, RegisterAdditionalFieldActivity.class);
                                                    intent.putExtra("type", "picorear");
                                                    startActivity(intent);
                                                } else if (jsonObject1.getString("subscribe_status").equals("0")) {
                                                    //  startActivity(new Intent(LoginActivity.this, SubscriptionActivity.class));

                                                    startActivity(new Intent(LoginActivity.this, UserPicorearHomeActivity.class));

                                                } else if (jsonObject1.getString("is_approve").equals("1")) {
                                                    // Toast.makeText(LoginActivity.this,getResources().getString(R.string.pleaseckecksubmail), Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(LoginActivity.this, UserPicorearHomeActivity.class));

                                                } else if (jsonObject1.getString("is_approve").equals("2")) {
                                                    startActivity(new Intent(LoginActivity.this, UserPicorearHomeActivity.class));

                                                }

                                            } else {
                                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                                intent.putExtra("data", "picoact");
                                                startActivity(intent);

                                            }
                                        } else {
                                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                            intent.putExtra("data", "picoact");
                                            startActivity(intent);

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {


                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> param = new HashMap<>();
                                    param.put("user_id", PrefManager.getUserId(LoginActivity.this));

                                    return param;
                                }
                            };

                            requestQueue.add(stringRequest);


                        } else if (getIntent().getStringExtra("data").equals("antigaspi")) {
                            Log.d("dsdsd", PrefManager.getIsAnti(LoginActivity.this));
                            if (PrefManager.IsLogin(LoginActivity.this)) {
                                if (PrefManager.getIsAnti(LoginActivity.this).equals("1")) {
                                    startActivity(new Intent(LoginActivity.this, UserAntigaspiHomeActivity.class));
                                } else if (PrefManager.getIsAnti(LoginActivity.this).equals("0")) {
                                    Intent intent = new Intent(LoginActivity.this, RegisterAdditionalFieldActivity.class);
                                    intent.putExtra("type", "antigaspi");
                                    startActivity(intent);

                                }
                            } else {
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                intent.putExtra("data", "antigaspi");
                                startActivity(intent);
                            }
                        }
                    } else {


                        Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                        intent.putExtra("homeact", "home");
                        startActivity(intent);
                    }

                }
            }
            if(jsonObject.getString("status").equals("0")){
                Toast.makeText(this, jsonObject.getString("messge"), Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void setProgressbar(int Visibility) {


    }

    @Override
    public void onValidatedata(int position) {
        switch (position) {
            case 1:
                edit_mail.setError(getResources().getString(R.string.fill_field));
                break;
            case 2:
                edit_mail.setError(getResources().getString(R.string.email_error));
                break;
            case 3:
                edit_pass.setError(getResources().getString(R.string.fill_field));
                break;
            case 4:
                edit_pass.setError(getResources().getString(R.string.pass_error));
                break;
            case 5:
                iloginPresenter.doLogin(edit_mail.getText().toString(), edit_pass.getText().toString(),FirebaseInstanceId.getInstance().getToken());
                CustomUtil.ShowDialog(LoginActivity.this,true);
                break;
        }
    }
    @Override
    public void onLoginFailure(Throwable t) {

        CustomUtil.DismissDialog(LoginActivity.this);
        VolleyError volleyError= (VolleyError) t;
        volleyError.printStackTrace();
        Log.d("dasd",t.toString());
        String message = null;
        if (volleyError instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (volleyError instanceof AuthFailureError) {

        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }
        if (message != null) {
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    public void forpass(View view) {

        final Dialog dialog=new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.dialog_forgot_pass);
        dialog.show();

        final EditText dia_edt_mail=dialog.findViewById(R.id.dia_edt_mail);
        TextView txt_title=dialog.findViewById(R.id.txt_title);
        Button btn_submit=dialog.findViewById(R.id.btn_submit);
        TextView txt_header=dialog.findViewById(R.id.txt_header);

        txt_header.setText(getResources().getString(R.string.for_pass));

        txt_title.setText(getResources().getString(R.string.enter_reg_email));


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Validation validation = new Validation();
                if (!validation.edttxtvalidation(dia_edt_mail, LoginActivity.this)) {

                }
                else if (!validation.emailvalidation(dia_edt_mail, LoginActivity.this)) {

                }else {


                    CustomUtil.ShowDialog(LoginActivity.this,true);
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("forget_pass"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            CustomUtil.DismissDialog(LoginActivity.this);

                            Log.d("dsad", response);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equals("1")) {
                                    dialog.dismiss();
                                    Toast.makeText(LoginActivity.this, jsonObject.getString("messge"), Toast.LENGTH_SHORT).show();


                                } else if (jsonObject.getString("status").equals("0")) {
                                    Toast.makeText(LoginActivity.this,  jsonObject.getString("messge"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            Log.d("dsad", volleyError.toString());
                            CustomUtil.DismissDialog(LoginActivity.this);
                            String message = null;
                            if (volleyError instanceof NetworkError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (volleyError instanceof ServerError) {
                                message = "The server could not be found. Please try again after some time!!";
                            } else if (volleyError instanceof AuthFailureError) {

                            } else if (volleyError instanceof ParseError) {
                                message = "Parsing error! Please try again after some time!!";
                            } else if (volleyError instanceof NoConnectionError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (volleyError instanceof TimeoutError) {
                                message = "Connection TimeOut! Please check your internet connection.";
                            }
                            if (message != null) {

                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {

                            }
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("email", dia_edt_mail.getText().toString());
                            return param;
                        }
                    };

                    requestQueue.add(stringRequest);
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


       if (requestCode == 33) {



           Log.d("sdsad", String.valueOf(resultCode));




            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

           try {

               GoogleSignInAccount account = task.getResult(ApiException.class);

               Log.d("sdffds",account.getEmail());


               CustomUtil.ShowDialog(LoginActivity.this,true);
               RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
               StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("google_login"), new com.android.volley.Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       Log.d("dasdasd",response);
                       CustomUtil.DismissDialog(LoginActivity.this);


                       try {
                           JSONObject jsonObject=new JSONObject(response);
                           Log.d("sdasdas", String.valueOf(jsonObject.getInt("bank_acc_type")));
                           if(jsonObject.getInt("bank_acc_type")==1){


                               PrefManager.setIsLogin(LoginActivity.this,true);
                               JSONObject jsonObject1=jsonObject.getJSONObject("user_details");
                               if(jsonObject1.getString("address").equals("") || jsonObject1.getString("birthday").equals("null")) {
                                   Intent intent = new Intent(LoginActivity.this, EditprofileActivity.class);
                                   intent.putExtra("activity","home");
                                   startActivity(intent);
                               }
                               else{
                                   Intent intent = new Intent(LoginActivity.this, UserCliantHomeActivity.class);
                                   intent.putExtra("homeact", "home");
                                   startActivity(intent);
                               }
                                   PrefManager.setdob(jsonObject1.getString("birthday"), LoginActivity.this);
                               PrefManager.setMobile(jsonObject1.getString("phoneno"),LoginActivity.this);
                               PrefManager.setUserId(jsonObject1.getString("userid"),LoginActivity.this);
                               PrefManager.setFirstName(jsonObject1.getString("firstname"),LoginActivity.this);
                               PrefManager.setLastName(jsonObject1.getString("lastname"),LoginActivity.this);
                               PrefManager.setEmailId(jsonObject1.getString("email"),LoginActivity.this);
                               PrefManager.setMobile(jsonObject1.getString("phoneno"),LoginActivity.this);
                               PrefManager.setAddress(jsonObject1.getString("address"),LoginActivity.this);
                               PrefManager.setLAT(jsonObject1.getString("lat"),LoginActivity.this);
                               PrefManager.setLNG(jsonObject1.getString("lng"),LoginActivity.this);
                               PrefManager.setProfilePic(jsonObject1.getString("profile_pic"),LoginActivity.this);
                               PrefManager.setIsPico(jsonObject1.getString("is_pico"),LoginActivity.this);
                               PrefManager.setISCliant(jsonObject1.getString("is_client"),LoginActivity.this);
                               PrefManager.setSubscribepico(jsonObject1.getString("subscribe_status"),LoginActivity.this);
                               PrefManager.setIsAnti(jsonObject1.getString("is_anti"),LoginActivity.this);
                               PrefManager.setZip(jsonObject1.getString("zip"),LoginActivity.this);
                               PrefManager.setCity(jsonObject1.getString("city"),LoginActivity.this);
                               PrefManager.setSTATE(jsonObject1.getString("state"),LoginActivity.this);
                               PrefManager.setCOUNTRY(jsonObject1.getString("country"),LoginActivity.this);
                               PrefManager.setIam(jsonObject1.getString("i_am"),LoginActivity.this);
                               PrefManager.setCompanyname(jsonObject1.getString("companyname"),LoginActivity.this);
                               Toast.makeText(LoginActivity.this, jsonObject.getString("companyname"), Toast.LENGTH_SHORT).show();

                           }
                           else if(jsonObject.getInt("bank_acc_type")==0){


                               Intent intent = new Intent(LoginActivity.this, SetBankAccounttype.class);
                               intent.putExtra("socialid",jsonObject.getString("social_id"));
                               intent.putExtra("email",jsonObject.getString("email"));
                               intent.putExtra("userid",jsonObject.getString("user_id"));
                               startActivity(intent);

                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }



                   }
               }, new com.android.volley.Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Log.d("dsdadada", error.toString());
                       CustomUtil.DismissDialog(LoginActivity.this);
                   }
               }) {


                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {

                       Map<String, String> param = new HashMap<>();

                       Log.d("adsadas", account.getEmail());
                       Log.d("adsadas", account.getId());
                       Log.d("adsadas", account.getDisplayName());

                       try {
                           param.put("email", account.getEmail());
                           param.put("google_id", account.getId());
                           param.put("fcm_token",FirebaseInstanceId.getInstance().getToken());
                           String[] mStrArr = account.getDisplayName().split(" ");
                           if (mStrArr.length == 2) {
                               param.put("first_name", mStrArr[0]);
                               param.put("last_name", mStrArr[1]);

                           } else {
                               param.put("first_name", mStrArr[0]);
                               param.put("last_name", " ");
                           }



                           //  Log.d("dsdsd", object.getString("id"));


                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                       return param;
                   }

               };

               requestQueue.add(stringRequest);


               // Toast.makeText(LoginActivity.this, ""+account.getEmail(), Toast.LENGTH_SHORT).show();
               // Signed in successfully, show authenticated UI.
               //  updateUI(account);
           } catch (ApiException e) {
               // The ApiException status code indicates the detailed failure reason.
               // Please refer to the GoogleSignInStatusCodes class reference for more information.
               Log.w("sandesh", "signInResult:failed code=" + e.getStatusCode());

           }


        }




       else if(mCallbackManager!=null){
           mCallbackManager.onActivityResult(requestCode, resultCode, data);
           return;
       }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this,UserCliantHomeActivity.class));
    }




}

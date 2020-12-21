package com.app.fr.fruiteefy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;
import com.app.fr.fruiteefy.user_client.profile.EditprofileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetBankAccounttype extends AppCompatActivity implements Spinner.OnItemSelectedListener{

    private boolean iam_selstat=false;
    private Button submit;
    private String posiam;
    private Spinner spiner_category;
    private ArrayList<String> categoryArrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_bank_accounttype);
        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        categoryArrList=new ArrayList<>();
        setTitle(getResources().getString(R.string.iam));
        submit=findViewById(R.id.submit);
        spiner_category=findViewById(R.id.spiner_category);

        categoryArrList.add(0,getResources().getString(R.string.select));
        categoryArrList.add(1,getResources().getString(R.string.particular));
        categoryArrList.add(2,getResources().getString(R.string.association));
        spiner_category.setOnItemSelectedListener(this);

        spiner_category.setAdapter(new ArrayAdapter<String>(SetBankAccounttype.this, android.R.layout.simple_spinner_dropdown_item, categoryArrList));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(iam_selstat==false){
                    Toast.makeText(SetBankAccounttype.this, getResources().getString(R.string.select_iam), Toast.LENGTH_SHORT).show();
                }
else {



                    RequestQueue requestQueue = Volley.newRequestQueue(SetBankAccounttype.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("create_mangopay_account"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("bcvbvcbvb", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);





                                PrefManager.setIsLogin(SetBankAccounttype.this,true);
                                JSONObject jsonObject1=jsonObject.getJSONObject("user_details");

                                if(jsonObject1.getString("address").equals("") || jsonObject1.getString("birthday").equals("null")){
                                    Intent intent = new Intent(SetBankAccounttype.this, EditprofileActivity.class);
                                    intent.putExtra("activity","home");
                                    startActivity(intent);
                                }
                                else{
                                    Intent intent = new Intent(SetBankAccounttype.this, UserCliantHomeActivity.class);
                                    intent.putExtra("homeact", "home");
                                    startActivity(intent);
                                }

                                PrefManager.setdob(jsonObject1.getString("birthday"), SetBankAccounttype.this);

                                PrefManager.setMobile(jsonObject1.getString("phoneno"),SetBankAccounttype.this);
                                PrefManager.setUserId(jsonObject1.getString("userid"),SetBankAccounttype.this);
                                PrefManager.setFirstName(jsonObject1.getString("firstname"),SetBankAccounttype.this);
                                PrefManager.setLastName(jsonObject1.getString("lastname"),SetBankAccounttype.this);
                                PrefManager.setEmailId(jsonObject1.getString("email"),SetBankAccounttype.this);
                                PrefManager.setMobile(jsonObject1.getString("phoneno"),SetBankAccounttype.this);
                                PrefManager.setAddress(jsonObject1.getString("address"),SetBankAccounttype.this);
                                PrefManager.setLAT(jsonObject1.getString("lat"),SetBankAccounttype.this);
                                PrefManager.setLNG(jsonObject1.getString("lng"),SetBankAccounttype.this);
                                PrefManager.setProfilePic(jsonObject1.getString("profile_pic"),SetBankAccounttype.this);
                                PrefManager.setIsPico(jsonObject1.getString("is_pico"),SetBankAccounttype.this);
                                PrefManager.setISCliant(jsonObject1.getString("is_client"),SetBankAccounttype.this);
                                PrefManager.setSubscribepico(jsonObject1.getString("subscribe_status"),SetBankAccounttype.this);
                                PrefManager.setIsAnti(jsonObject1.getString("is_anti"),SetBankAccounttype.this);
                                PrefManager.setZip(jsonObject1.getString("zip"),SetBankAccounttype.this);
                                PrefManager.setCity(jsonObject1.getString("city"),SetBankAccounttype.this);
                                PrefManager.setSTATE(jsonObject1.getString("state"),SetBankAccounttype.this);
                                PrefManager.setCOUNTRY(jsonObject1.getString("country"),SetBankAccounttype.this);
                                PrefManager.setIam(jsonObject1.getString("i_am"),SetBankAccounttype.this);
                                PrefManager.setCompanyname(jsonObject1.getString("companyname"),SetBankAccounttype.this);
                                Toast.makeText(SetBankAccounttype.this, jsonObject.getString("companyname"), Toast.LENGTH_SHORT).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("bcvbvcbvb", error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> param = new HashMap<>();

                            param.put("type", posiam);
                            param.put("user_id", getIntent().getStringExtra("userid"));
                            param.put("email",getIntent().getStringExtra("email") );
                            param.put("social_id",getIntent().getStringExtra("socialid"));

                            return param;

                        }
                    };

                    requestQueue.add(stringRequest);
                }



            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.d("sfsdfds", String.valueOf(position));


        if(position==2){
            posiam="Company";
            iam_selstat=true;
        }
        else if(position==1){
            posiam="Individual";
            iam_selstat=true;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

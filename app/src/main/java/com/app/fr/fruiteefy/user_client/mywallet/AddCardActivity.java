package com.app.fr.fruiteefy.user_client.mywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.mywallet.adapter.CommonSpnAdapter;
import com.mangopay.android.sdk.Callback;
import com.mangopay.android.sdk.MangoPayBuilder;
import com.mangopay.android.sdk.model.CardRegistration;
import com.mangopay.android.sdk.model.exception.MangoException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCardActivity extends AppCompatActivity {

    LinearLayout btnvalidate;
    Spinner spinnercarttype;
    EditText edtcardno,edtexpiry,edtcvv;
    ImageView backimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        btnvalidate=findViewById(R.id.btnvalidate);
        spinnercarttype=findViewById(R.id.spinnercarttype);
        edtcardno=findViewById(R.id.edtcardno);
        edtexpiry=findViewById(R.id.edtexpiry);
        edtcvv=findViewById(R.id.edtcvv);
        backimg=findViewById(R.id.backimg);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CommonSpnAdapter commonSpnAdapter = new CommonSpnAdapter(getResources().getStringArray(R.array.cardtype), AddCardActivity.this);
        spinnercarttype.setAdapter(commonSpnAdapter);

        btnvalidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomUtil.ShowDialog(AddCardActivity.this,false);



                RequestQueue requestQueue= Volley.newRequestQueue(AddCardActivity.this);

                StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("create_card_registration"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("dsdsadasd",response);




                        try {

                            JSONObject jsonObject=new JSONObject(response);



                            MangoPayBuilder builder = new MangoPayBuilder(AddCardActivity.this); // android context
//                            builder.baseURL("https://api.sandbox.mangopay.com")   // card pre-registration baseUrl
//                                    .clientId("fruiteefy") // card pre-registration clientId
                              builder.baseURL("https://api.mangopay.com")   // card pre-registration baseUrl
                                 .clientId("fruiteefyprod") // card pre-registration clientId
                                    .accessKey(jsonObject.getString("AccessKey"))   // card pre-registration accessKey
                                    .cardRegistrationURL(jsonObject.getString("card_regis_url"))   // card pre-registration url
                                    .preregistrationData(jsonObject.getString("PreregistrationData"))   // card pre-registration data
                                    .cardPreregistrationId(jsonObject.getString("cardRegisterId"))   // card pre-registration id
                                    .cardNumber(edtcardno.getText().toString()) // credit card number accepted inputs: '123412341234' or '1234-1234-1234-1234' or '1234 1234 1234 1234'
                                    .cardExpirationDate(edtexpiry.getText().toString()) // credit card expiration date e.g '0920' or '11/20' or '02-19'
                                    .cardCvx(edtcvv.getText().toString()) // credit card expiration cvx
                                    .callback(new Callback() {  // callback that returns the sdk success or failure objects
                                        @Override public void success(CardRegistration cardRegistration) {
                                            CustomUtil.DismissDialog(AddCardActivity.this);

                                            Log.d("sdfdsfdsfdfd",cardRegistration.toString());

                                            Toast.makeText(AddCardActivity.this, getResources().getString(R.string.cardaddedsucessfully), Toast.LENGTH_SHORT).show();

                                            Intent data = new Intent();
                                            String text = "card added";
                                            data.setData(Uri.parse(text));
                                            setResult(RESULT_OK, data);

                                            finish();


                                        }




                                        @Override
                                        public void failure(MangoException error) {

                                            Log.d("dsdsad",error.toString());
                                            CustomUtil.DismissDialog(AddCardActivity.this);
                                            Log.d("sdafad",error.toString());

                                            Intent data = new Intent();
                                            String text = "card added";
                                            data.setData(Uri.parse(text));
                                            setResult(RESULT_CANCELED, data);
                                            Toast.makeText(AddCardActivity.this, getResources().getString(R.string.cardnotaddedsucessfully), Toast.LENGTH_SHORT).show();
                                        }

                                    }).start();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> param=new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(AddCardActivity.this));
                        param.put("select_card_type",spinnercarttype.getSelectedItem().toString());

                        Log.d("dfsferdff",spinnercarttype.getSelectedItem().toString());
                        return param;
                    }

                };

                requestQueue.add(stringRequest);

            }
        });



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

package com.app.fr.fruiteefy.user_client.Subscription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionActivity extends AppCompatActivity {

    TextView subscribe,applytxtvew,pricetopay;
    EditText promocode;
    RadioButton selectedbutton;
    private RadioGroup radiopaymentmethodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);




        applytxtvew=findViewById(R.id.applytxtvew);
        promocode=findViewById(R.id.promocode);
        pricetopay=findViewById(R.id.pricetopay);
        radiopaymentmethodGroup=findViewById(R.id.radiopaymentmethodGroup);

        pricetopay.setText("10");



        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);




        applytxtvew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue requestQueue=Volley.newRequestQueue(SubscriptionActivity.this);

                StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("apply_promo_code"),
                        new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d("fffyhg",response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);

                            if(jsonObject.getString("status").equals("1")){

                                pricetopay.setText(jsonObject.getString("amount"));


                            }

                            Toast.makeText(SubscriptionActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("fffyhg",error.toString());
                    }
                }){


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();


                        param.put("user_id",PrefManager.getUserId(SubscriptionActivity.this));
                        param.put("coupon_code",promocode.getText().toString());

                        return param;


                    }

                };

                requestQueue.add(stringRequest);





            }
        });

        setTitle("Subscribe");

        subscribe=findViewById(R.id.subscribe);


        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radiopaymentmethodGroup.getCheckedRadioButtonId();
                selectedbutton = (RadioButton) findViewById(selectedId);


                Log.d("dfdsfs", String.valueOf(selectedId));


             if(selectedbutton==null) {
                 Toast.makeText(SubscriptionActivity.this, "Please select card type", Toast.LENGTH_SHORT).show();
             }
             else
             {
                 RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionActivity.this);


                 StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("subscription"), new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {

                         Log.d("Ffdfdsf", response);

                         try {
                             JSONObject jsonObject = new JSONObject(response);

                             if (jsonObject.getString("status").equals("1")) {

//                                 Intent intent=new Intent(SubscriptionActivity.this, PaymentActivity.class);
//                                 intent.putExtra("webredirect_url",jsonObject.getString("redirct_url"));
//                                 intent.putExtra("transactionid",jsonObject.getString("transactionId"));
//                                 startActivity(intent);



                                 Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                                 httpIntent.setData(Uri.parse(jsonObject.getString("redirct_url")));

                                 startActivity(httpIntent);

                                 finish();

                             }


                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 }, new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError volleyError) {

                         Log.d("dfdfsdf", volleyError.toString());
                         String message = null;
                         if (volleyError instanceof NetworkError) {
                             message = "Cannot connect to Internet...Please check your connection!";
                         } else if (volleyError instanceof ServerError) {
                             message = "The server could not be found. Please try again after some time!!";
                         } else if (volleyError instanceof AuthFailureError) {
                             message = "Cannot connect to Internet...Please check your connection!";
                         } else if (volleyError instanceof ParseError) {
                             message = "Parsing error! Please try again after some time!!";
                         } else if (volleyError instanceof NoConnectionError) {
                             message = "Cannot connect to Internet...Please check your connection!";
                         } else if (volleyError instanceof TimeoutError) {
                             message = "Connection TimeOut! Please check your internet connection.";
                         }
                         if (message != null) {

                             Toast.makeText(SubscriptionActivity.this, message, Toast.LENGTH_SHORT).show();

                         } else {
                             Toast.makeText(SubscriptionActivity.this, message, Toast.LENGTH_SHORT).show();

                         }
                     }
                 }) {

                     @Override
                     protected Map<String, String> getParams() throws AuthFailureError {

                         Map<String, String> param = new HashMap<>();

//                         if (selectedbutton.getId() == R.id.dinarradio) {
//                             param.put("debit_direct_type", "DINERS");
//
//                         } else
                             if (selectedbutton.getId() == R.id.maestroradio) {
                             param.put("debit_direct_type", "MAESTRO");
                         } else if (selectedbutton.getId() == R.id.visamastercardradio) {
                             param.put("debit_direct_type", "CB_VISA_MASTERCARD");
                         }

                         param.put("user_id", PrefManager.getUserId(SubscriptionActivity.this));
                         param.put("amount_pay", pricetopay.getText().toString());


                         return param;
                     }
                 };

                 requestQueue.add(stringRequest);


             }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

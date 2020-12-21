package com.app.fr.fruiteefy.user_client.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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
import com.app.fr.fruiteefy.Util.cardpogo;
import com.app.fr.fruiteefy.user_client.PaymentActivity;
import com.app.fr.fruiteefy.user_client.Paymentcardadapter;
import com.app.fr.fruiteefy.user_client.mywallet.AddCardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentCartActivity extends AppCompatActivity {

    RequestQueue requestQueue,requestQueue1;
    TextView totalpayment,walletprice,pay,addnewcard;
    ArrayList mArrayCardList;
    String pricereceivedfromwallet="";
    TextView one,two;
    String promocode,promoapplytype;
    String cardid="";
    String  pricetopay="";
    CheckBox walletcheckbox,alreadyaddedcardcheckbox,paybycard;
    RadioButton selectedbutton,maestroradio,visamastercardradio;
    RecyclerView addedcardrecvew;
    TextView cardno;
    LinearLayout layalreadyaddedcard;
    private RadioGroup radiopaymentmethodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_cart);

        totalpayment=findViewById(R.id.totalpayment);
        cardno=findViewById(R.id.cardno);
        layalreadyaddedcard=findViewById(R.id.layalreadyaddedcard);
        mArrayCardList=new ArrayList();
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        maestroradio=findViewById(R.id.maestroradio);
        visamastercardradio=findViewById(R.id.visamastercardradio);
        radiopaymentmethodGroup=findViewById(R.id.radiopaymentmethodGroup);
        walletprice=findViewById(R.id.walletprice);
        addedcardrecvew=findViewById(R.id.addedcardrecvew);
        addnewcard=findViewById(R.id.addnewcard);
        walletcheckbox=findViewById(R.id.walletcheckbox);
        paybycard=findViewById(R.id.paybycard);
        alreadyaddedcardcheckbox=findViewById(R.id.alreadyaddedcardcheckbox);
        pay=findViewById(R.id.pay);

        requestQueue= Volley.newRequestQueue(PaymentCartActivity.this);
        requestQueue1= Volley.newRequestQueue(PaymentCartActivity.this);

        RequestQueue requestQueue10 = Volley.newRequestQueue(PaymentCartActivity.this);
        StringRequest stringRequest10 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("card_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Log.d("sdsadd", response);
                try {
                    mArrayCardList.clear();
                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("sdsdasd",response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray mJsonArr = jsonObject.getJSONArray("mycartdata");
                        for (int i = 0; i < mJsonArr.length(); i++) {
                            JSONObject mJsonObj = mJsonArr.getJSONObject(i);
                            cardpogo cardpogo = new cardpogo();


                            if (mJsonObj.getString("status").equals("ACTIVE")) {


                                cardpogo.setId(mJsonObj.getString("Id"));
                                cardpogo.setCardtype(mJsonObj.getString("CardType"));
                                cardpogo.setCardno(mJsonObj.getString("Alias"));

                                mArrayCardList.add(cardpogo);
                          }


                            if(mArrayCardList.size()>0){
                                layalreadyaddedcard.setVisibility(View.VISIBLE);
                            }
                        }

                        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(PaymentCartActivity.this, LinearLayoutManager.VERTICAL, false);
                        addedcardrecvew.setLayoutManager(verticalLayoutManager);
                        Paymentcardadapter paymentcardadapter=new Paymentcardadapter(mArrayCardList,PaymentCartActivity.this);

                        addedcardrecvew.setAdapter(paymentcardadapter);
                        paymentcardadapter.setOnItemClickListener(position -> {

                            addedcardrecvew.setVisibility(View.GONE);

                            cardpogo modelResource = (cardpogo) mArrayCardList.get(position);
                            cardid=modelResource.getId();
                            cardno.setText(modelResource.getCardno());

                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(PaymentCartActivity.this));
                // Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
                return param;
            }
        };

        requestQueue10.add(stringRequest10);

        Intent intent=getIntent();

        walletcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Double pricefromwallet= Double.valueOf(pricereceivedfromwallet);
                Double finalprice= Double.valueOf(pricetopay);

                if(isChecked==true) {

                    if(finalprice>pricefromwallet) {

                        Double newpricefrombank=finalprice-pricefromwallet;

                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.VISIBLE);

                        one.setText(pricefromwallet+" "+getResources().getString(R.string.currency)+" "+getResources().getString(R.string.willbedeductedfromyourwalllet));
                        two.setText(String.format("%.2f", newpricefrombank)+" "+getResources().getString(R.string.currency)+" "+getResources().getString(R.string.willbedeductedfrombankaccout));


                    }

                    else if(finalprice<pricefromwallet) {
                        alreadyaddedcardcheckbox.setChecked(false);
                        paybycard.setChecked(false);
                        maestroradio.setEnabled(false);
                        visamastercardradio.setEnabled(false);


                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.VISIBLE);

                        one.setText(finalprice+" "+getResources().getString(R.string.currency)+" "+getResources().getString(R.string.willbedeductedfromyourwalllet));
                        two.setText("0"+" "+getResources().getString(R.string.currency)+" "+getResources().getString(R.string.willbedeductedfrombankaccout));

                    }
                    else {
                        alreadyaddedcardcheckbox.setChecked(false);
                        paybycard.setChecked(false);
                        maestroradio.setEnabled(false);
                        visamastercardradio.setEnabled(false);
                    }
                }
                if(isChecked==false){
                    if(finalprice>pricefromwallet) {
                        alreadyaddedcardcheckbox.setChecked(false);
                        paybycard.setChecked(false);
                        maestroradio.setEnabled(false);
                        visamastercardradio.setEnabled(false);

                    }
                    else{

                    }
                    one.setVisibility(View.GONE);
                    two.setVisibility(View.GONE);

                }


            }
        });
        alreadyaddedcardcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Double pricefromwallet= Double.valueOf(pricereceivedfromwallet);
                Double finalprice= Double.valueOf(pricetopay);

                  if(isChecked==true) {
                      addedcardrecvew.setVisibility(View.VISIBLE);

                      if(finalprice>pricefromwallet) {
                          paybycard.setChecked(false);
                          maestroradio.setEnabled(false);
                          visamastercardradio.setEnabled(false);

                      }
                      else {

                          walletcheckbox.setChecked(false);
                          paybycard.setChecked(false);
                          maestroradio.setEnabled(false);
                          visamastercardradio.setEnabled(false);
                      }


                  }
                  else if(isChecked==false){
                      addedcardrecvew.setVisibility(View.GONE);
                  }


            }
        });

        paybycard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Double pricefromwallet= Double.valueOf(pricereceivedfromwallet);
                Double finalprice= Double.valueOf(pricetopay);
                if(isChecked==true) {
                    maestroradio.setEnabled(true);
                    visamastercardradio.setEnabled(true);


                    if(finalprice>pricefromwallet) {
                        alreadyaddedcardcheckbox.setChecked(false);
                    }
                    else {

                        alreadyaddedcardcheckbox.setChecked(false);
                        walletcheckbox.setChecked(false);
                    }


                }

                if(isChecked==false){
                    maestroradio.setEnabled(false);
                    visamastercardradio.setEnabled(false);
                }


            }
        });


        totalpayment.setText("Total à payer "+getIntent().getStringExtra("finaltotal")+" "+getResources().getString(R.string.currency));
        pricetopay=getIntent().getStringExtra("finaltotal");
        promocode=getIntent().getStringExtra("promo_code");
        promoapplytype=getIntent().getStringExtra("promo_apply_type");
        requestQueue = Volley.newRequestQueue(PaymentCartActivity.this);
        requestQueue1 = Volley.newRequestQueue(PaymentCartActivity.this);

        CustomUtil.ShowDialog(PaymentCartActivity.this,false);
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("balance_summary"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CustomUtil.DismissDialog(PaymentCartActivity.this);
                //    mArrayCardList.clear();
                Log.d("sdsadd", response);
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject mJsonObj = jsonObject.getJSONObject("balance_summary");
                        pricereceivedfromwallet=mJsonObj.getString("Balance_available");

                        walletprice.setText(pricereceivedfromwallet+" "+getResources().getString(R.string.currency));
                        Log.d("sdssd",mJsonObj.getString("Balance_available"));

//                        if(mJsonObj.getString("Balance_available").equalsIgnoreCase("0 EUR")){
//                            walletcheckbox.setVisibility(View.GONE);
//                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());
                CustomUtil.DismissDialog(PaymentCartActivity.this);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(PaymentCartActivity.this));
                return param;
            }
        };

        requestQueue1.add(stringRequest1);

        addnewcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentCartActivity.this, AddCardActivity.class);
                startActivityForResult(intent,1);
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radiopaymentmethodGroup.getCheckedRadioButtonId();
                selectedbutton = (RadioButton) findViewById(selectedId);




                if(walletcheckbox.isChecked()) {
                    Double pricefromwallet= Double.valueOf(pricereceivedfromwallet);
                    Double finalprice= Double.valueOf(pricetopay);


                    Log.d("gffh",pricefromwallet.toString());
                    Log.d("gffh",finalprice.toString());


                    if(finalprice<pricefromwallet) {

                        CustomUtil.ShowDialog(PaymentCartActivity.this, false);


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("order_reserve"), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("fdfrgfgfs", response);
                                CustomUtil.DismissDialog(PaymentCartActivity.this);

                                try {

                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.has("redirct_url")){
                                        Intent intent1 = new Intent(PaymentCartActivity.this, PaymentActivity.class);
                                        intent1.putExtra("webredirect_url", jsonObject.getString("redirct_url"));
                                        startActivity(intent1);
                                        finish();

                                    }

                                 if(jsonObject.has("message")) {
                                     Toast.makeText(PaymentCartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                 }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                CustomUtil.DismissDialog(PaymentCartActivity.this);
                                Log.d("dfdfdfd", error.toString());

                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();


                                //  type 1

                                Log.d("sddasdsad",intent.getStringExtra("finaltotal"));
                                Log.d("sddasdsad",intent.getStringExtra("amounttopay"));


                                param.put("finaltotal", intent.getStringExtra("amounttopay"));
                                param.put("amount_select_from_wallet", intent.getStringExtra("finaltotal"));
                                param.put("select_payment_card", "");
                                param.put("first_name", intent.getStringExtra("first_name"));
                                param.put("last_name", intent.getStringExtra("last_name"));
                                param.put("email", intent.getStringExtra("email"));
                                param.put("address", intent.getStringExtra("address"));
                                param.put("country", intent.getStringExtra("country"));
                                param.put("state", intent.getStringExtra("state"));
                                param.put("city", intent.getStringExtra("city"));
                                param.put("zip", intent.getStringExtra("zip"));
                                param.put("promo_code", promocode);
                                param.put("promo_apply_type", promoapplytype);
                                param.put("debit_direct_type", "");
                                param.put("service_charge", intent.getStringExtra("service_charge"));
                                param.put("user_id", PrefManager.getUserId(PaymentCartActivity.this));

                                return param;
                            }


                        };

                        requestQueue.add(stringRequest);
                    }
                    else{

                        Double newpricefrombank=finalprice-pricefromwallet;

                        if(alreadyaddedcardcheckbox.isChecked()){



                            if(mArrayCardList.size()==1){

                                cardpogo cardpogo=(cardpogo)mArrayCardList.get(0);
                                cardid=cardpogo.getId();
                                Log.d("fdfdf",cardid);

                                CustomUtil.ShowDialog(PaymentCartActivity.this, false);

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("order_reserve"), new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("fdfrgfgfs", response);
                                        CustomUtil.DismissDialog(PaymentCartActivity.this);

                                        try {

                                            JSONObject jsonObject = new JSONObject(response);
                                            if(jsonObject.has("redirct_url")) {

                                                Intent intent1 = new Intent(PaymentCartActivity.this, PaymentActivity.class);
                                                intent1.putExtra("webredirect_url", jsonObject.getString("redirct_url"));
                                                startActivity(intent1);
                                                finish();
                                            }

                                            if(jsonObject.has("message")) {


                                                Toast.makeText(PaymentCartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        CustomUtil.DismissDialog(PaymentCartActivity.this);
                                        Log.d("dfdfdfd", error.toString());

                                    }
                                }) {

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> param = new HashMap<>();


                                        param.put("user_id", PrefManager.getUserId(PaymentCartActivity.this));
                                        param.put("finaltotal", intent.getStringExtra("amounttopay"));
                                        param.put("amount_select_from_wallet", String.valueOf(pricefromwallet));

                                        param.put("select_payment_card", cardid);
                                        param.put("first_name", intent.getStringExtra("first_name"));
                                        param.put("last_name", intent.getStringExtra("last_name"));
                                        param.put("email", intent.getStringExtra("email"));
                                        param.put("address", intent.getStringExtra("address"));
                                        param.put("country", intent.getStringExtra("country"));
                                        param.put("state", intent.getStringExtra("state"));
                                        param.put("city", intent.getStringExtra("city"));
                                        param.put("zip", intent.getStringExtra("zip"));
                                        param.put("promo_code", promocode);
                                        param.put("promo_apply_type", promoapplytype);
                                        param.put("debit_direct_type", "");
                                        param.put("service_charge", intent.getStringExtra("service_charge"));


                                        return param;
                                    }


                                };

                                requestQueue.add(stringRequest);

                            }
                            else {

                                if (cardid.equals("")) {
                                    Toast.makeText(PaymentCartActivity.this, getResources().getString(R.string.pleaseselectcardforpyment), Toast.LENGTH_SHORT).show();

                                } else {


                                    CustomUtil.ShowDialog(PaymentCartActivity.this, false);

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("order_reserve"), new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.d("fdfrgfgfs", response);
                                            CustomUtil.DismissDialog(PaymentCartActivity.this);

                                            try {

                                                JSONObject jsonObject = new JSONObject(response);
                                                if(jsonObject.has("redirct_url")) {

                                                    Intent intent1 = new Intent(PaymentCartActivity.this, PaymentActivity.class);
                                                    intent1.putExtra("webredirect_url", jsonObject.getString("redirct_url"));
                                                    startActivity(intent1);
                                                    finish();
                                                }
                                                if(jsonObject.has("message")){
                                                    Toast.makeText(PaymentCartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            CustomUtil.DismissDialog(PaymentCartActivity.this);
                                            Log.d("dfdfdfd", error.toString());

                                        }
                                    }) {

                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> param = new HashMap<>();


                                            param.put("user_id", PrefManager.getUserId(PaymentCartActivity.this));
                                            param.put("finaltotal",  intent.getStringExtra("amounttopay"));
                                            param.put("amount_select_from_wallet", String.valueOf(pricefromwallet));

                                            param.put("select_payment_card", cardid);
                                            param.put("first_name", intent.getStringExtra("first_name"));
                                            param.put("last_name", intent.getStringExtra("last_name"));
                                            param.put("email", intent.getStringExtra("email"));
                                            param.put("address", intent.getStringExtra("address"));
                                            param.put("country", intent.getStringExtra("country"));
                                            param.put("state", intent.getStringExtra("state"));
                                            param.put("city", intent.getStringExtra("city"));
                                            param.put("zip", intent.getStringExtra("zip"));
                                            param.put("promo_code", promocode);
                                            param.put("promo_apply_type", promoapplytype);
                                            param.put("debit_direct_type", "");
                                            param.put("service_charge", intent.getStringExtra("service_charge"));


                                            return param;
                                        }


                                    };

                                    requestQueue.add(stringRequest);

                                }
                            }

















                        }
                        else if(paybycard.isChecked()){




                            if(selectedbutton==null) {
                                Toast.makeText(PaymentCartActivity.this, "Please select card type", Toast.LENGTH_SHORT).show();
                            }

                            else {


                                CustomUtil.ShowDialog(PaymentCartActivity.this, false);


                                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("order_reserve"), new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("fdfrgfgfs", response);
                                        CustomUtil.DismissDialog(PaymentCartActivity.this);

                                        try {

                                            JSONObject jsonObject = new JSONObject(response);

//                                    Intent intent1 = new Intent(PaymentCartActivity.this, PaymentActivity.class);
//                                    intent1.putExtra("webredirect_url", jsonObject.getString("redirct_url"));
//                                    startActivity(intent1);

                                            if(jsonObject.has("redirct_url")) {
                                                finish();


                                                Intent intent = new Intent(PaymentCartActivity.this, UserCliantHomeActivity.class);
                                                intent.putExtra("homeact", "myorder");
                                                startActivity(intent);

                                                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(jsonObject.getString("redirct_url")));
                                                startActivity(intent2);
                                                finish();
                                            }
                                            if(jsonObject.has("message")) {

                                                Toast.makeText(PaymentCartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        CustomUtil.DismissDialog(PaymentCartActivity.this);
                                        Log.d("dfdfdfd", error.toString());

                                    }
                                }) {

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> param = new HashMap<>();


                                        //  type 2
                                        if (selectedbutton.getId() == R.id.maestroradio) {
                                            param.put("debit_direct_type", "MAESTRO");
                                        } else if (selectedbutton.getId() == R.id.visamastercardradio) {
                                            param.put("debit_direct_type", "CB_VISA_MASTERCARD");
                                        }
                                        Log.d("dfdfsd", String.valueOf(newpricefrombank));
                                        Log.d("dfdfsd",String.valueOf(pricefromwallet));

                                        param.put("user_id", PrefManager.getUserId(PaymentCartActivity.this));
                                        param.put("finaltotal", intent.getStringExtra("amounttopay"));
                                        param.put("amount_select_from_wallet", String.valueOf(pricefromwallet));
                                        param.put("select_payment_card", "direct_web");
                                        param.put("first_name", intent.getStringExtra("first_name"));
                                        param.put("last_name", intent.getStringExtra("last_name"));
                                        param.put("email", intent.getStringExtra("email"));
                                        param.put("address", intent.getStringExtra("address"));
                                        param.put("country", intent.getStringExtra("country"));
                                        param.put("state", intent.getStringExtra("state"));
                                        param.put("city", intent.getStringExtra("city"));
                                        param.put("zip", intent.getStringExtra("zip"));
                                        param.put("promo_code", promocode);
                                        param.put("promo_apply_type", promoapplytype);
                                        param.put("service_charge", intent.getStringExtra("service_charge"));


                                        return param;
                                    }


                                };

                                requestQueue.add(stringRequest);


                            }
                        }

                      else{
                            Toast.makeText(PaymentCartActivity.this, "Merci de sélectionner une carte de paiement", Toast.LENGTH_SHORT).show();
                        }



                    }

                }


                else if(alreadyaddedcardcheckbox.isChecked()) {

                    Log.d("ddsda", String.valueOf(mArrayCardList.size()));
                   // Log.d("ddsda", String.valueOf(mArrayCardList.get(0)));
                    //Log.d("ddsda", String.valueOf(mArrayCardList.get(1)));

                    if(mArrayCardList.size()==1){

                        cardpogo cardpogo=(cardpogo)mArrayCardList.get(0);
                        cardid=cardpogo.getId();
                        Log.d("fdfdf",cardid);

                        CustomUtil.ShowDialog(PaymentCartActivity.this, false);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("order_reserve"), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("fdfrgfgfs", response);
                                CustomUtil.DismissDialog(PaymentCartActivity.this);

                                try {

                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.has("redirct_url")) {

                                        Intent intent1 = new Intent(PaymentCartActivity.this, PaymentActivity.class);
                                        intent1.putExtra("webredirect_url", jsonObject.getString("redirct_url"));
                                        startActivity(intent1);
                                        finish();
                                    }

                                    if(jsonObject.has("message")) {

                                        Toast.makeText(PaymentCartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                CustomUtil.DismissDialog(PaymentCartActivity.this);
                                Log.d("dfdfdfd", error.toString());

                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();


                                param.put("user_id", PrefManager.getUserId(PaymentCartActivity.this));
                                param.put("finaltotal",  intent.getStringExtra("amounttopay"));
                                param.put("amount_select_from_wallet", "");
                                param.put("select_payment_card", cardid);
                                param.put("first_name", intent.getStringExtra("first_name"));
                                param.put("last_name", intent.getStringExtra("last_name"));
                                param.put("email", intent.getStringExtra("email"));
                                param.put("address", intent.getStringExtra("address"));
                                param.put("country", intent.getStringExtra("country"));
                                param.put("state", intent.getStringExtra("state"));
                                param.put("city", intent.getStringExtra("city"));
                                param.put("zip", intent.getStringExtra("zip"));
                                param.put("promo_code", promocode);
                                param.put("promo_apply_type", promoapplytype);
                                param.put("debit_direct_type", "");
                                param.put("service_charge", intent.getStringExtra("service_charge"));


                                return param;
                            }


                        };

                        requestQueue.add(stringRequest);

                    }
                    else {

                        if (cardid.equals("")) {
                            Toast.makeText(PaymentCartActivity.this, getResources().getString(R.string.pleaseselectcardforpyment), Toast.LENGTH_SHORT).show();

                        } else {


                            CustomUtil.ShowDialog(PaymentCartActivity.this, false);

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("order_reserve"), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("fdfrgfgfs", response);
                                    CustomUtil.DismissDialog(PaymentCartActivity.this);

                                    try {

                                        JSONObject jsonObject = new JSONObject(response);
                                        if(jsonObject.has("redirct_url")) {

                                            Intent intent1 = new Intent(PaymentCartActivity.this, PaymentActivity.class);
                                            intent1.putExtra("webredirect_url", jsonObject.getString("redirct_url"));
                                            startActivity(intent1);
                                            finish();
                                        }
                                        if(jsonObject.has("message")){
                                            Toast.makeText(PaymentCartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    CustomUtil.DismissDialog(PaymentCartActivity.this);
                                    Log.d("dfdfdfd", error.toString());

                                }
                            }) {

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> param = new HashMap<>();


                                    param.put("user_id", PrefManager.getUserId(PaymentCartActivity.this));
                                    param.put("finaltotal",  intent.getStringExtra("amounttopay"));
                                    param.put("amount_select_from_wallet", "");
                                    param.put("select_payment_card", cardid);
                                    param.put("first_name", intent.getStringExtra("first_name"));
                                    param.put("last_name", intent.getStringExtra("last_name"));
                                    param.put("email", intent.getStringExtra("email"));
                                    param.put("address", intent.getStringExtra("address"));
                                    param.put("country", intent.getStringExtra("country"));
                                    param.put("state", intent.getStringExtra("state"));
                                    param.put("city", intent.getStringExtra("city"));
                                    param.put("zip", intent.getStringExtra("zip"));
                                    param.put("promo_code", promocode);
                                    param.put("promo_apply_type", promoapplytype);
                                    param.put("debit_direct_type", "");
                                    param.put("service_charge", intent.getStringExtra("service_charge"));


                                    return param;
                                }


                            };

                            requestQueue.add(stringRequest);

                        }
                    }
                }
                else if(paybycard.isChecked()){

                    if(selectedbutton==null) {
                        Toast.makeText(PaymentCartActivity.this, "Please select card type", Toast.LENGTH_SHORT).show();
                    }

                 else {


                        CustomUtil.ShowDialog(PaymentCartActivity.this, false);


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("order_reserve"), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("fdfrgfgfs", response);
                                CustomUtil.DismissDialog(PaymentCartActivity.this);

                                try {

                                    JSONObject jsonObject = new JSONObject(response);

//                                    Intent intent1 = new Intent(PaymentCartActivity.this, PaymentActivity.class);
//                                    intent1.putExtra("webredirect_url", jsonObject.getString("redirct_url"));
//                                    startActivity(intent1);
                                    finish();


                                    if(jsonObject.has("redirct_url")) {

                                        Intent intent = new Intent(PaymentCartActivity.this, UserCliantHomeActivity.class);
                                        intent.putExtra("homeact", "myorder");
                                        startActivity(intent);

                                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(jsonObject.getString("redirct_url")));
                                        startActivity(intent2);
                                        finish();
                                    }

                                    if(jsonObject.has("message")){
                                        Toast.makeText(PaymentCartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                CustomUtil.DismissDialog(PaymentCartActivity.this);
                                Log.d("dfdfdfd", error.toString());
                                NetworkResponse networkResponse=error.networkResponse;
                                String eerror=new String(networkResponse.data);
                                Log.d("fsffsd",eerror);


                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();

                                Log.d("sddsada",intent.getStringExtra("total"));
                                Log.d("shdgdd",intent.getStringExtra("service_charge"));

                                //  type 2
                                if (selectedbutton.getId() == R.id.maestroradio) {
                                    param.put("debit_direct_type", "MAESTRO");
                                } else if (selectedbutton.getId() == R.id.visamastercardradio) {
                                    param.put("debit_direct_type", "CB_VISA_MASTERCARD");
                                }

                                param.put("user_id", PrefManager.getUserId(PaymentCartActivity.this));
                                param.put("finaltotal", intent.getStringExtra("amounttopay"));
                                param.put("amount_select_from_wallet", "");
                                param.put("select_payment_card", "direct_web");
                                param.put("first_name", intent.getStringExtra("first_name"));
                                param.put("last_name", intent.getStringExtra("last_name"));
                                param.put("email", intent.getStringExtra("email"));
                                param.put("address", intent.getStringExtra("address"));
                                param.put("country", intent.getStringExtra("country"));
                                param.put("state", intent.getStringExtra("state"));
                                param.put("city", intent.getStringExtra("city"));
                                param.put("zip", intent.getStringExtra("zip"));
                                param.put("promo_code", promocode);
                                param.put("promo_apply_type", promoapplytype);
                                param.put("service_charge", intent.getStringExtra("service_charge"));


                                return param;
                            }


                        };

                        requestQueue.add(stringRequest);


                    }
                }

                else{
                    Toast.makeText(PaymentCartActivity.this, getResources().getString(R.string.pleaseselectmodeofpayment), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode==RESULT_OK){


                RequestQueue requestQueue10 = Volley.newRequestQueue(PaymentCartActivity.this);
                StringRequest stringRequest10 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("card_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Log.d("sdsadd", response);
                        try {
                            mArrayCardList.clear();
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d("sdsdasd",response);
                            if (jsonObject.getString("status").equals("1")) {
                                JSONArray mJsonArr = jsonObject.getJSONArray("mycartdata");
                                for (int i = 0; i < mJsonArr.length(); i++) {
                                    JSONObject mJsonObj = mJsonArr.getJSONObject(i);
                                    cardpogo cardpogo = new cardpogo();


                                    if (mJsonObj.getString("status").equals("ACTIVE")) {


                                        cardpogo.setId(mJsonObj.getString("Id"));
                                        cardpogo.setCardtype(mJsonObj.getString("CardType"));
                                        cardpogo.setCardno(mJsonObj.getString("Alias"));

                                        mArrayCardList.add(cardpogo);
                                    }


                                    if(mArrayCardList.size()>0){
                                        layalreadyaddedcard.setVisibility(View.VISIBLE);
                                    }
                                }

                                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(PaymentCartActivity.this, LinearLayoutManager.VERTICAL, false);
                                addedcardrecvew.setLayoutManager(verticalLayoutManager);
                                Paymentcardadapter paymentcardadapter=new Paymentcardadapter(mArrayCardList,PaymentCartActivity.this);

                                addedcardrecvew.setAdapter(paymentcardadapter);
                                paymentcardadapter.setOnItemClickListener(position -> {

                                    addedcardrecvew.setVisibility(View.GONE);

                                    cardpogo modelResource = (cardpogo) mArrayCardList.get(position);
                                    cardid=modelResource.getId();
                                    cardno.setText(modelResource.getCardno());

                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sdsadd", error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(PaymentCartActivity.this));
                        // Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
                        return param;
                    }
                };

                requestQueue10.add(stringRequest10);



            }
        }
    }
}

package com.app.fr.fruiteefy.user_picorear;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.app.fr.fruiteefy.Chat.MessageActivity;
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.adapter.PagerListAdapter;
import com.app.fr.fruiteefy.user_client.home.LargeView;
import com.app.fr.fruiteefy.user_client.products.ProductDetailsActivity;
import com.app.fr.fruiteefy.user_picorear.Adapter.SlotsAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.Product;
import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonationaroundmedetailActivity extends AppCompatActivity {

    TextView title,addr,productdetail,comeandpickup,availablefor,desc,sendmsg,deposby,varietytxt,txtname,txtaccess,descadd,txttype,availabletxt,unavailabletxt;
    ImageView iv_product_image;
    Button opinioncommentry;
    ImageView img;
    FloatingActionButton fabedit,fabdlete;
    StringRequest stringRequest2,stringRequest1;
    RequestQueue requestQueue,requestQueue1;
    String str="";
    private ArrayList<String> arrayListimg=new ArrayList<>();;
    String userid="",firstname="",lastname="";
    Spinner spinneravailable;
    RecyclerView recavailableslots;
    ArrayList<String> availabledate;
    ArrayList<String> availableslot;
    Button bookdonationame;
    private RadioGroup radiopaymentmethodGroup;
    RadioButton selectedbutton;
    String offerid,Token="";
     Product product;
    private LoopingViewPager mPager;
    private PageIndicatorView indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donationaroundmedetail);

        setTitle(getResources().getString(R.string.donationaroundme));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        requestQueue= Volley.newRequestQueue(DonationaroundmedetailActivity.this);
        requestQueue1= Volley.newRequestQueue(DonationaroundmedetailActivity.this);
        availabledate=new ArrayList<>();
        availableslot=new ArrayList<>();
        title=findViewById(R.id.title);
        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);
        recavailableslots=findViewById(R.id.availableslots);
        sendmsg=findViewById(R.id.sendmsg);
        bookdonationame=findViewById(R.id.bookdonationame);
        availablefor=findViewById(R.id.availablefor);
        spinneravailable=findViewById(R.id.spinneravailable);
        desc=findViewById(R.id.desc);
        addr=findViewById(R.id.addr);
        radiopaymentmethodGroup=findViewById(R.id.radiopaymentmethodGroup);
        comeandpickup=findViewById(R.id.comeandpickup);
        opinioncommentry=findViewById(R.id.opinioncommentry);
        productdetail=findViewById(R.id.productdetail);
        iv_product_image=findViewById(R.id.iv_product_image);
        fabedit=findViewById(R.id.fabedit);
        fabdlete=findViewById(R.id.fabdlete);
        deposby = findViewById(R.id.deposby);
        txtname = findViewById(R.id.txtname);
        txtaccess = findViewById(R.id.txtaccess);
        varietytxt = findViewById(R.id.varietytxt);
        img = findViewById(R.id.img);
        descadd = findViewById(R.id.descadd);
        txttype= findViewById(R.id.txttype);
        availabletxt = findViewById(R.id.availabletxt);
        unavailabletxt = findViewById(R.id.unavailabletxt);
        spinneravailable.setVisibility(View.GONE);










        if(getIntent().hasExtra("offerdata")) {


            Intent intent = getIntent();
             product= (Product) intent.getSerializableExtra("offerdata");

            desc.setText(":"+" "+product.getDesc());


            RequestQueue requestQueue7=Volley.newRequestQueue(DonationaroundmedetailActivity.this);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("selection_box"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("dgdfgfgfg",response);


                    try {
                        JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArrayslots=jsonObject.getJSONArray("list");



                    for (int j = 0; j <= jsonArrayslots.length(); j++) {

                        availabledate.add(jsonArrayslots.getString(j));
                        spinneravailable.setAdapter(new ArrayAdapter<String>( DonationaroundmedetailActivity.this, android.R.layout.simple_spinner_dropdown_item, availabledate));

                        spinneravailable.setVisibility(View.VISIBLE);
                    }


                    Log.d("dsadsdssds", String.valueOf(availabledate.size()));

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
                    Map<String, String> param = new HashMap<>();
                    param.put("product_id",product.getProductid());
                    return param;
                }
            };

            requestQueue7.add(stringRequest);


                    stringRequest2=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("donations_around_me_details"),
                            new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("donations_around",response);


                            try {
                                JSONObject jsonObject1=new JSONObject(response);
                                JSONObject jsonObject=jsonObject1.getJSONObject("reservation_details");




                                JSONArray jsonArray=jsonObject.getJSONArray("offer_dates");



                                JSONArray jsonarrimg=jsonObject1.getJSONArray("offr_images");


                                for(int i=0;i<jsonarrimg.length();i++){
                                    JSONObject jsonObject2=jsonarrimg.getJSONObject(i);

                                    Log.d("fsfsfs",jsonObject2.getString("image_url"));
                                    arrayListimg.add(BaseUrl.ANTIGASPIURL.concat(jsonObject2.getString("image_url")));

                                }

                                if(arrayListimg.size()==0){
                                    arrayListimg.add(jsonObject.getString("offer_image"));
                                }

                                Log.d("dfsdfsdff", String.valueOf(arrayListimg));
                                mPager.setAdapter(new PagerListAdapter(DonationaroundmedetailActivity.this,arrayListimg,true));

                                indicator.setCount(mPager.getIndicatorCount());
                                mPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
                                    @Override
                                    public void onIndicatorProgress(int selectingPosition, float progress) {
                                        indicator.setProgress(selectingPosition, progress);
                                    }

                                    @Override
                                    public void onIndicatorPageChange(int newIndicatorPosition) {
//                indicatorView.setSelection(newIndicatorPosition);
                                    }
                                });

                                offerid=jsonObject.getString("offer_id");
                                userid=jsonObject.getString("user_id");
                                firstname=jsonObject.getString("firstname");
                                lastname=jsonObject.getString("lastname");
                                Token=jsonObject.getString("fcm_token");

                            //    Picasso.with(DonationaroundmedetailActivity.this).load(jsonObject.getString("offer_image")).into(iv_product_image);
                                Picasso.with(DonationaroundmedetailActivity.this).load(jsonObject.getString("image")).into(img);

                                if(jsonObject.getString("image") != null){
                                    img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            try {
                                                String imgurl = jsonObject.getString("image");
                                                Intent intent = new Intent(DonationaroundmedetailActivity.this, LargeView.class);
                                                intent.putExtra("imgurl", imgurl);
                                             startActivity(intent);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                                title.setText(jsonObject.getString("offer_title"));
                                deposby.setText(":"+" "+jsonObject.getString("firstname")+" "+jsonObject.getString("lastname"));
                                varietytxt.setText(":"+" "+jsonObject.getString("variety"));
                                descadd.setText(":"+" "+jsonObject.getString("description"));
                                txttype.setText(":"+" "+jsonObject.getString("type"));
                                txtname.setText(":"+" "+jsonObject.getString("nom"));
                                txtaccess.setText(":"+" "+jsonObject.getString("acces"));
                                if(jsonObject.getString("offer_status").equals("Available")){
                                    Log.d("offer_status",jsonObject.getString("offer_status"));
                                    availabletxt.setVisibility(View.VISIBLE);
                                    unavailabletxt.setVisibility(View.GONE);
                                }else{
                                    availabletxt.setVisibility(View.GONE);
                                    unavailabletxt.setVisibility(View.VISIBLE);
                                }
                                if(jsonObject.getString("offerStatus").equals("2")){
                                    Log.d("offerstatus",jsonObject.getString("offerStatus"));
                                    unavailabletxt.setClickable(false);
                                    unavailabletxt.setFocusable(false);
                                    unavailabletxt.setEnabled(false);
                                    unavailabletxt.setFocusableInTouchMode(false);

                                    availabletxt.setClickable(false);
                                    availabletxt.setFocusable(false);
                                    availabletxt.setEnabled(false);
                                    availabletxt.setFocusableInTouchMode(false);
                                }else{

                                    availabletxt.setClickable(true);
                                    availabletxt.setFocusable(true);
                                    availabletxt.setEnabled(true);
                                    availabletxt.setFocusableInTouchMode(true);

                                    unavailabletxt.setClickable(true);
                                    unavailabletxt.setFocusable(true);
                                    unavailabletxt.setEnabled(true);
                                    unavailabletxt.setFocusableInTouchMode(true);
                                }
//                                comeandpickup.setText(jsonObject.getString("offer_title").concat(" à venir récupérer selon les disponibilités ci-dessous:"));
//                                availablefor.setText(jsonObject.getString("offeravailabletitle"));

                                addr.setText(jsonObject.getString("address"));



                                for (int i = 0; i <= jsonArray.length(); i++) {

                                    availableslot.add(jsonArray.getString(i));

                                    str=str.concat(jsonArray.getString(i)).concat(",");


                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                            if(product.getAvailable().equals("1")){

                                fabedit.setVisibility(View.VISIBLE);
                                fabdlete.setVisibility(View.VISIBLE);
                            }

                           // productdetail.setText(product.getDesc());
                            if(product.getAvailable().equals("2")){
                                opinioncommentry.setVisibility(View.VISIBLE);
                            }



                            Log.d("gdfgdgfgdg", String.valueOf(availableslot.size()));
                            Log.d("gdfgdgfgdg", String.valueOf(availabledate.size()));


                            recavailableslots.setLayoutManager(new GridLayoutManager(DonationaroundmedetailActivity.this, 3));
                            recavailableslots.setAdapter(new SlotsAdapter(availableslot, DonationaroundmedetailActivity.this));






                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("ffgfdg",error.toString());

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param=new HashMap<>();
                            param.put("offer_id",product.getProductid());

                            Log.d("sdffsfd",product.getProductid());


                            return param;
                        }
                    };
                    requestQueue.add(stringRequest2);

                    sendmsg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(DonationaroundmedetailActivity.this, MessageActivity.class);
                            intent.putExtra("receiverid",userid);
                            intent.putExtra("token",Token);
                            intent.putExtra("name",firstname+" "+lastname);

                            startActivity(intent);
                        }
                    });



                }


       availabletxt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DonationaroundmedetailActivity.this);
               alertDialogBuilder.setMessage(R.string.alert);
               alertDialogBuilder.setPositiveButton(R.string.yes,
                       new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface arg0, int arg1)
                           {
                               callapi();

                           }
                       });

               alertDialogBuilder.setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
dialog.dismiss();
                   }
               });

               AlertDialog alertDialog = alertDialogBuilder.create();
               alertDialog.show();


           }
       });


                bookdonationame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int selectedId = radiopaymentmethodGroup.getCheckedRadioButtonId();
                        selectedbutton = (RadioButton) findViewById(selectedId);


                        if(selectedbutton==null) {
                            Toast.makeText(DonationaroundmedetailActivity.this, "Please select card type", Toast.LENGTH_SHORT).show();
                        }
                        else if(spinneravailable.getVisibility()==View.VISIBLE &&spinneravailable.getSelectedItem().equals("Please select")){
                            Toast.makeText(DonationaroundmedetailActivity.this, "Please select time slot", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            RequestQueue requestQueue = Volley.newRequestQueue(DonationaroundmedetailActivity.this);

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("reservation_offer"), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("dfdfdf",response);

                                    try {
                                        JSONObject jsonObject=new JSONObject(response);

                                        Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                                        httpIntent.setData(Uri.parse(jsonObject.getString("redirct_url")));

                                        startActivity(httpIntent);

                                        finish();

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

                                        Toast.makeText(DonationaroundmedetailActivity.this, message, Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(DonationaroundmedetailActivity.this, message, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }) {




                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> param = new HashMap<>();

//                                    if (selectedbutton.getId() == R.id.dinarradio) {
//                                        param.put("debit_direct_type", "DINERS");
//
//
//                                    } else
                                        if (selectedbutton.getId() == R.id.maestroradio) {
                                        param.put("debit_direct_type", "MAESTRO");

                                    } else if (selectedbutton.getId() == R.id.visamastercardradio) {
                                        param.put("debit_direct_type", "CB_VISA_MASTERCARD");

                                    }

                                    param.put("payer_email",PrefManager.getEmailId(DonationaroundmedetailActivity.this));



                                    param.put("user_id", PrefManager.getUserId(DonationaroundmedetailActivity.this));
                                    param.put("offer_title",title.getText().toString());
                                    if(spinneravailable.getVisibility()==View.VISIBLE) {
                                        param.put("collect_time_slot", spinneravailable.getSelectedItem().toString());
                                    }
                                    else{
                                        param.put("collect_time_slot", "00");
                                    }
                                    param.put("offer_id",offerid);

                                    if(str.equals(",")){
                                        param.put("offer_available_date", "0000-00-00");
                                    }
                                    else {
                                        param.put("offer_available_date", str);
                                    }
                                    Log.d("fsfdfdfsd",str);





                                    return param;
                                }

                            };

                            requestQueue.add(stringRequest);


                        }
                    }
                });




    }

    private void callapi() {
       CustomUtil.ShowDialog(DonationaroundmedetailActivity.this,true);
        stringRequest1=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("donations_around_me_details"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
           CustomUtil.DismissDialog(DonationaroundmedetailActivity.this);
                        Log.d("donations_around_api",response);


                        try {
                            JSONObject jsonObject1=new JSONObject(response);
                            JSONObject jsonObject=jsonObject1.getJSONObject("reservation_details");




                            JSONArray jsonArray=jsonObject.getJSONArray("offer_dates");


                            offerid=jsonObject.getString("offer_id");
                            userid=jsonObject.getString("user_id");
                            firstname=jsonObject.getString("firstname");
                            lastname=jsonObject.getString("lastname");
                            Token=jsonObject.getString("fcm_token");

                            Picasso.with(DonationaroundmedetailActivity.this).load(jsonObject.getString("offer_image")).into(iv_product_image);
                            Picasso.with(DonationaroundmedetailActivity.this).load(jsonObject.getString("image")).into(img);

                            title.setText(jsonObject.getString("offer_title"));
                            deposby.setText(":"+" "+jsonObject.getString("firstname")+" "+jsonObject.getString("lastname"));
                            varietytxt.setText(":"+" "+jsonObject.getString("variety"));
                            descadd.setText(":"+" "+jsonObject.getString("description"));
                            txttype.setText(":"+" "+jsonObject.getString("type"));
                            txtname.setText(":"+" "+jsonObject.getString("nom"));
                            txtaccess.setText(":"+" "+jsonObject.getString("acces"));
                            if(jsonObject.getString("offer_status").equals("Available")){
                                Log.d("offer_statusapi",jsonObject.getString("offer_status"));
                                availabletxt.setVisibility(View.VISIBLE);
                                unavailabletxt.setVisibility(View.GONE);
                            }else{
                                availabletxt.setVisibility(View.GONE);
                                unavailabletxt.setVisibility(View.VISIBLE);
                            }
                            if(jsonObject.getString("offerStatus").equals("2")){
                                Log.d("offerstatusapi",jsonObject.getString("offerStatus"));
                                unavailabletxt.setClickable(false);
                                unavailabletxt.setFocusable(false);
                                unavailabletxt.setEnabled(false);
                                unavailabletxt.setFocusableInTouchMode(false);

                                availabletxt.setClickable(false);
                                availabletxt.setFocusable(false);
                                availabletxt.setEnabled(false);
                                availabletxt.setFocusableInTouchMode(false);
                            }else{

                                availabletxt.setClickable(true);
                                availabletxt.setFocusable(true);
                                availabletxt.setEnabled(true);
                                availabletxt.setFocusableInTouchMode(true);

                                unavailabletxt.setClickable(true);
                                unavailabletxt.setFocusable(true);
                                unavailabletxt.setEnabled(true);
                                unavailabletxt.setFocusableInTouchMode(true);
                            }
                           // comeandpickup.setText(jsonObject.getString("offer_title").concat(" à venir récupérer selon les disponibilités ci-dessous:"));
                         //   availablefor.setText(jsonObject.getString("offeravailabletitle"));

                            addr.setText(jsonObject.getString("address"));



                            for (int i = 0; i <= jsonArray.length(); i++) {

                                availableslot.add(jsonArray.getString(i));

                                str=str.concat(jsonArray.getString(i)).concat(",");


                            }




                        } catch (JSONException e) {
                            CustomUtil.DismissDialog(DonationaroundmedetailActivity.this);
                            e.printStackTrace();
                        }



                        if(product.getAvailable().equals("1")){

                            fabedit.setVisibility(View.VISIBLE);
                            fabdlete.setVisibility(View.VISIBLE);
                        }

                        // productdetail.setText(product.getDesc());
                        if(product.getAvailable().equals("2")){
                            opinioncommentry.setVisibility(View.VISIBLE);
                        }



                        Log.d("gdfgdgfgdg", String.valueOf(availableslot.size()));
                        Log.d("gdfgdgfgdg", String.valueOf(availabledate.size()));


                        recavailableslots.setLayoutManager(new GridLayoutManager(DonationaroundmedetailActivity.this, 3));
                        recavailableslots.setAdapter(new SlotsAdapter(availableslot, DonationaroundmedetailActivity.this));






                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomUtil.DismissDialog(DonationaroundmedetailActivity.this);
                Log.d("ffgfdg",error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("offer_id",product.getProductid());
                Log.d("offer_id_api",product.getProductid());
                param.put("offerStatus","2");




                return param;
            }
        };
        requestQueue1.add(stringRequest1);
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

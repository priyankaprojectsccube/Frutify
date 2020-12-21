package com.app.fr.fruiteefy.user_antigaspi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_antigaspi.adapter.DonationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  DonationActivity extends AppCompatActivity {

    RecyclerView recvewdonation;
    RelativeLayout datelyt,datemonthly;
    TextView tv_apply;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ImageView filtericon,searchicon,backimg;
    LinearLayout filterlyt;
    DonationAdapter adapter;
    Spinner spinnertype;
    private ArrayList<String> sellertype;
    EditText textviewtitle;
    TextView mTxtProductionDatesSelected,mTxtMonthly;
    ArrayList<Product>  allProductPojos=new ArrayList<>();
    private String searchString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        setTitle(getResources().getString(R.string.my_donation));

        sellertype=new ArrayList<>();
        filtericon=findViewById(R.id.filtericon);
        filterlyt=findViewById(R.id.filterlyt);
        searchicon=findViewById(R.id.searchicon);
        textviewtitle=findViewById(R.id.textviewtitle);
        backimg=findViewById(R.id.backimg);
        datelyt=findViewById(R.id.datelyt);
        spinnertype=findViewById(R.id.spinnertype);
        mTxtMonthly=findViewById(R.id.mTxtMonthly);
        datemonthly=findViewById(R.id.datemonthly);
        tv_apply=findViewById(R.id.tv_apply);
        mTxtProductionDatesSelected=findViewById(R.id.mTxtProductionDatesSelected);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                filterlyt.setVisibility(View.GONE);
                allProductPojos.clear();


                stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("my_donations"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("dsfdfdfdf",response);


                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getString("status").equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("mydonations");

                                for (int i=0;i<jsonArray.length();i++){

                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    Product product=new Product();
                                    product.setProductimg(jsonObject1.getString("offer_image"));
                                    product.setOfferid("OFF-0000"+jsonObject1.getString("offer_id"));
                                    product.setOffertype(jsonObject1.getString("offer_type"));
                                    product.setBookby(jsonObject1.getString("booked_by"));
                                    product.setStatus(jsonObject1.getString("delivery_mode"));
                                    product.setActionclass(jsonObject1.getString("actionclass"));
                                    product.setProductname(jsonObject1.getString("offer_title"));
                                    product.setAction(jsonObject1.getString("action"));
                                    allProductPojos.add(product);
                                }

                                recvewdonation.setLayoutManager(new LinearLayoutManager(DonationActivity.this));
                                recvewdonation.setAdapter(new DonationAdapter(allProductPojos,DonationActivity.this));
                                recvewdonation.getAdapter().notifyDataSetChanged();

                                Log.d("dsdsadsad", String.valueOf(allProductPojos.size()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {


                        Log.d("dsdsdads",volleyError.toString());

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

                            Toast.makeText(DonationActivity.this, message, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(DonationActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

                        }
                    }
                }){


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> param=new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(DonationActivity.this));
                        param.put("searchbtn","filter");
                        param.put("fromdate",mTxtProductionDatesSelected.getText().toString());
                        param.put("todate",mTxtMonthly.getText().toString());



                        return param;
                    }
                };

                requestQueue.add(stringRequest);


            }
        });

        sellertype.add(0,getResources().getString(R.string.select));
        sellertype.add(1,getResources().getString(R.string.sellonly));
        sellertype.add(2,getResources().getString(R.string.mygift));
        spinnertype.setAdapter(new ArrayAdapter<String>(DonationActivity.this, android.R.layout.simple_spinner_dropdown_item, sellertype));


        datelyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mPreviousYear = getCalculatedDate("yyyy-MM-dd", -20);

                Calendar calendar = Calendar.getInstance();
                String[] mstrDate = mPreviousYear.split("-");
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(DonationActivity.this,android.R.style.Theme_Holo_Light_Dialog,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTxtProductionDatesSelected.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);

                    }
                }, Integer.parseInt(mstrDate[0]), Integer.parseInt(mstrDate[1]), Integer.parseInt(mstrDate[2]));
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.show();

            }
        });


        datemonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mPreviousYear = getCalculatedDate("yyyy-MM-dd", -20);

                Calendar calendar = Calendar.getInstance();
                String[] mstrDate = mPreviousYear.split("-");
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(DonationActivity.this,android.R.style.Theme_Holo_Light_Dialog,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTxtMonthly.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);

                    }
                }, Integer.parseInt(mstrDate[0]), Integer.parseInt(mstrDate[1]), Integer.parseInt(mstrDate[2]));
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.show();

            }
        });


        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<Product> filteredModelList = filter(allProductPojos, textviewtitle.getText().toString());
                Log.d("dfdfsdf", String.valueOf(filteredModelList.size()));
                if (filteredModelList.size() > 0) {
                    adapter.setFilter(filteredModelList);

                }
                else{

                }
            }
        });

        filtericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filterlyt.isShown()){
                    filterlyt.setVisibility(View.GONE);


                }
                else{
                    filterlyt.setVisibility(View.VISIBLE);
                }
            }
        });


        recvewdonation=findViewById(R.id.recvewdonation);
        requestQueue= Volley.newRequestQueue(DonationActivity.this);

        CustomUtil.ShowDialog(DonationActivity.this,false);
        stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("my_donations"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                CustomUtil.DismissDialog(DonationActivity.this);
                Log.d("dsfdfdfdf",response);


                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray=jsonObject.getJSONArray("mydonations");

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Product product=new Product();
                            product.setProductimg(jsonObject1.getString("offer_image"));
                            product.setOfferid("OFF-0000"+jsonObject1.getString("offer_id"));
                            product.setOffertype(jsonObject1.getString("offer_type"));
                            product.setBookby(jsonObject1.getString("booked_by"));
                            product.setStatus(jsonObject1.getString("delivery_mode"));
                            product.setActionclass(jsonObject1.getString("actionclass"));
                            product.setProductname(jsonObject1.getString("offer_title"));
                            product.setAction(jsonObject1.getString("action"));
                            allProductPojos.add(product);
                        }

                        recvewdonation.setLayoutManager(new LinearLayoutManager(DonationActivity.this));
                        recvewdonation.setAdapter(new DonationAdapter(allProductPojos,DonationActivity.this));
                        recvewdonation.getAdapter().notifyDataSetChanged();

                        Log.d("dsdsadsad", String.valueOf(allProductPojos.size()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


                CustomUtil.DismissDialog(DonationActivity.this);

                Log.d("dsdsdads",volleyError.toString());

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

                    Toast.makeText(DonationActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DonationActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

                }
            }
        }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("user_id", PrefManager.getUserId(DonationActivity.this));
                param.put("searchbtn","");
                return param;
            }
        };

        requestQueue.add(stringRequest);




    }


    public static String getCalculatedDate(String dateFormat, int YEARS) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.YEAR, YEARS);
        return s.format(new Date(cal.getTimeInMillis()));
    }


    private ArrayList<Product> filter(ArrayList<Product> models, String query) {


        Log.d("sdsda", query);
        this.searchString = query;

        final ArrayList<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {

            String text = model.getProductname().toLowerCase();
//
//
//            String text1=model.getLastname().toLowerCase();
//
//            String text2=model.getFirstname().toLowerCase()+" "+model.getLastname().toLowerCase();

            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }


        adapter = new DonationAdapter(filteredModelList, DonationActivity.this);

        recvewdonation.setLayoutManager(new LinearLayoutManager(DonationActivity.this));
        recvewdonation.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
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

package com.app.fr.fruiteefy.user_antigaspi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.AntiPicoriarHelperClass;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.ChooseHellperClass;
import com.app.fr.fruiteefy.Util.HelperClass;
import com.app.fr.fruiteefy.Util.PicoriarHelperClass;
import com.app.fr.fruiteefy.Util.PositionInterface;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.Util.Validation;
import com.app.fr.fruiteefy.Util.VolleyMultipartRequest;
import com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter.AdapterChoose;
import com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter.AdapterSpinnerCat;
import com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter.AdapterSpinnerOther;
import com.app.fr.fruiteefy.user_antigaspi.adapter.AddantiofferImageAdapter;
import com.app.fr.fruiteefy.user_client.adapter.PagerListAdapter;
import com.app.fr.fruiteefy.user_picorear.Adapter.AddPicoImageAdapter;
import com.app.fr.fruiteefy.user_picorear.Adapter.SlotsAdapter;
import com.app.fr.fruiteefy.user_picorear.AddOfferActivity;
import com.app.fr.fruiteefy.user_picorear.DonationaroundmedetailActivity;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.app.fr.fruiteefy.Util.BaseUrl.ANTIGASPIURL;

public class AntiAddofferActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener, OnDateSelectedListener, OnMonthChangedListener {

    private ArrayList<String> arrayListimg=new ArrayList<>();;
    StringRequest StringRequestDeleteImg;
    RequestQueue RequestQueueDeleteImg;
    private ArrayList<AntiPicoriarHelperClass> mPicorialArrListImg;
    Spinner spinnersubcat, spinnercat, collect_deliver_spinner, units_spinner, availibilities_spinner, time_spinner, time_spinner2, other_person_spinner,choice_spinner;
    Button validate_button;
    Boolean imageselecte =false;
    RadioGroup treated_product_radiogroup, available_radiogroup;
    RequestQueue requestQueue, requestQueue1, requestQueue3, requestQueue4,requestQueuechoose;
    StringRequest stringRequest, stringRequest1, stringRequest3,stringRequestchoose;
    String category_id = "", subcategory_id = "", collectid = "", unitid = "", availability = "",strvariety="",choose_id="";
    ArrayList<Product> unit = new ArrayList();
    ArrayList<Product> collect = new ArrayList();
    JSONArray result, result1, result3,resultchoose;
    EditText offer_title_editText, desc_editText, treated_product_editText, available_quantity_editText,Variety_editText;
    TextView desc_textView, treatedproducttitle_textView, available_quantity_textView, availablenow, available_textView;
    ArrayList<HelperClass> category = new ArrayList<>();
    ArrayList<HelperClass> subcategory = new ArrayList<>();
    ArrayList<HelperClass> otherpersonspinnerarr = new ArrayList<>();
   ArrayList<ChooseHellperClass> chooselist = new ArrayList<>();
    private RadioButton yes_radioButton, no_radioButton, dont_no_radioButton, yes_op_radioButton, No_op_radiobutton, mRadioEveryDay, mRadioEveryWeekend, mRadioAvailableLater;
    RelativeLayout mLytDate;
    private ImageView mImv_selectDates;
    private ArrayList<CalendarDay> mArrDateList;
    private TextView mTxtSelectedDate;
    private String currentPhotoPath;
    private ImageView selectedimg;
    private RadioButton donner,echanger;
    String url, updateUrl, passUrl;
    private int GALLERY = 1, CAMERAA = 2;
    ImageView backImageView, prof_image;
    private static final String IMAGE_DIRECTORY = "/shary.one";
    private Validation validation = new Validation();
    String lat="", lng="", addr, city, country, state, zip, type;
RecyclerView mRecylerImgListview;
    private int REQUEST_IMAGE_CAPTURE = 22;
    private int REQUEST_IMAGE_PICK = 11;
    private RadioButton mRadioYesTreated, mRadioNoTreated;
    private Button mBtnPickImg;
    long lengthbmp;
    private Bitmap bitmap;

    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_addoffer);


        setTitle(getResources().getString(R.string.addanoffer));


//        for(int i=0;i<getResources().getStringArray(R.array.collectnamearr).length;i++){
//            Product product=new Product();
//            product.setName(getResources().getStringArray(R.array.collectnamearr)[i]);
//            product.setValue(getResources().getStringArray(R.array.collectvaluearr)[i]);
//            collect.add(product);
//        }

        showPhotoDialogue();
        verifyStoragePermissions(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mPicorialArrListImg = new ArrayList<>();
        mRecylerImgListview = findViewById(R.id.imagelist);
        mRecylerImgListview.setLayoutManager(new LinearLayoutManager(AntiAddofferActivity.this, LinearLayoutManager.HORIZONTAL, false));
        Variety_editText = findViewById(R.id.Variety_editText);
        mTxtSelectedDate = findViewById(R.id.mTxtDatesSelected);
        mImv_selectDates = findViewById(R.id.imv_selectdate);
        echanger=findViewById(R.id.echanger);
        donner=findViewById(R.id.donner);
        selectedimg=findViewById(R.id.selectedimg);
        spinnersubcat = findViewById(R.id.sub_category_spinner);
        choice_spinner = findViewById(R.id.choice_spinner);
        time_spinner2 = findViewById(R.id.time_spinner2);
        mRadioYesTreated = findViewById(R.id.yes_radioButton);
        mRadioNoTreated = findViewById(R.id.no_radioButton);
        mLytDate = findViewById(R.id.datelyt);
        mBtnPickImg = findViewById(R.id.add_image_button);
        mArrDateList = new ArrayList<>();
        yes_op_radioButton = findViewById(R.id.yes_op_radioButton);
        No_op_radiobutton = findViewById(R.id.no_op_radioButton);
        other_person_spinner = findViewById(R.id.other_person_spinner);
        availablenow = findViewById(R.id.availablenow);
      //  location_editText = findViewById(R.id.location_editText);
        time_spinner = findViewById(R.id.time_spinner);
        available_textView = findViewById(R.id.available_textView);
        available_radiogroup = findViewById(R.id.available_radiogroup);
        units_spinner = findViewById(R.id.units_spinner);
        available_quantity_editText = findViewById(R.id.available_quantity_editText);
        availibilities_spinner = findViewById(R.id.availibilities_spinner);
        yes_radioButton = findViewById(R.id.yes_radioButton);

        mRadioEveryDay = findViewById(R.id.everyday_radioButton);
        mRadioEveryWeekend = findViewById(R.id.every_weekend_radioButton);
        mRadioAvailableLater = findViewById(R.id.availiable_later_radioButton);


        available_quantity_textView = findViewById(R.id.available_quantity_textView);
        collect_deliver_spinner = findViewById(R.id.collect_deliver_spinner);
        treated_product_editText = findViewById(R.id.treated_product_editText);
        no_radioButton = findViewById(R.id.no_radioButton);
        treatedproducttitle_textView = findViewById(R.id.treatedproducttitle_textView);
        desc_textView = findViewById(R.id.desc_textView);
        desc_editText = findViewById(R.id.desc_editText);
        treated_product_radiogroup = findViewById(R.id.treated_product_radiogroup);
        dont_no_radioButton = findViewById(R.id.dont_no_radioButton);
        spinnercat = findViewById(R.id.category_spinner);
        offer_title_editText = findViewById(R.id.offer_title_editText);
        validate_button = findViewById(R.id.validate_button);
        collect_deliver_spinner.setAdapter(new AdapterSpinnerOther(getResources().getStringArray(R.array.collectnamearr), AntiAddofferActivity.this));
        units_spinner.setAdapter(new AdapterSpinnerOther(getResources().getStringArray(R.array.unitofmeasure), AntiAddofferActivity.this));
        availibilities_spinner.setAdapter(new AdapterSpinnerOther(getResources().getStringArray(R.array.availability), AntiAddofferActivity.this));
        time_spinner2.setAdapter(new AdapterSpinnerOther(getResources().getStringArray(R.array.timespinner), AntiAddofferActivity.this));
        time_spinner.setAdapter(new AdapterSpinnerOther(getResources().getStringArray(R.array.timeSpinnerAvailable), AntiAddofferActivity.this));

        mBtnPickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });




//        location_editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!Places.isInitialized()) {
//                    Places.initialize(AntiAddofferActivity.this, getResources().getString(R.string.google_maps_key));
//                    PlacesClient placesClient = Places.createClient(AntiAddofferActivity.this);
//                }
//
//
//                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.PLUS_CODE);
//                Intent intent = new Autocomplete.IntentBuilder(
//                        AutocompleteActivityMode.OVERLAY, fields)
//                        .setTypeFilter(TypeFilter.REGIONS)
//                        .setCountry("FR")
//                        .build(AntiAddofferActivity.this);
//                startActivityForResult(intent, 4);
//            }
//        });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue1 = Volley.newRequestQueue(this);
        requestQueue3 = Volley.newRequestQueue(this);
        requestQueue4 = Volley.newRequestQueue(this);
        requestQueuechoose= Volley.newRequestQueue(this);
        mImv_selectDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalenderDialogue();
            }
        });

        validate_button.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {


                Log.d("dfdsfdsf",getIntent().getStringExtra("Flag"));

                if (ValidationCheck()) {


                    if (getIntent().getStringExtra("Flag").equalsIgnoreCase("yes")) {
                        Log.d("goinif","goinif");
                        CustomUtil.ShowDialog(AntiAddofferActivity.this,false);
                        Log.d("goinif2","goinif2");
                        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, BaseUrl.BASEURL.concat("edit_offer"),
                                new Response.Listener<NetworkResponse>() {
                                    @Override
                                    public void onResponse(NetworkResponse response) {
                                        Log.d("goinif2","goinif2");
                                        //progressDialog.dismiss();
                                        CustomUtil.DismissDialog(AntiAddofferActivity.this);

                                        Log.d("edit_offer", String.valueOf(response));
                                        if (response.statusCode == 200) {

                                            Toast.makeText(AntiAddofferActivity.this, getResources().getString(R.string.offerupdatedsucessfully), Toast.LENGTH_SHORT).show();

                                            Intent intent=new Intent(AntiAddofferActivity.this,UserAntigaspiHomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Toast.makeText(AntiAddofferActivity.this,"went wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        //   progressDialog.dismiss();
//                        pd.dismiss();
                                        CustomUtil.DismissDialog(AntiAddofferActivity.this);

                                        Log.v("ResError", "" + volleyError.toString());
                                    }
                                }
                        ) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();
                                //   param.put("user_id", SharedPrefManager.getUserId(context));
                                product = (Product) getIntent().getSerializableExtra("Data");

//                                if(donner.isChecked()){
//
//                                    param.put("offer_type", "1");
//                                }
//
//                                if(echanger.isChecked()){
//
//                                param.put("offer_type", "3");
//                                }

                                param.put("offer_id", product.getOfferid());

                                param.put("don_address",choose_id);
                                param.put("variety",Variety_editText.getText().toString());
                                param.put("cat_id", category.get(spinnercat.getSelectedItemPosition()).getmStrId());
                                param.put("subcat_id", subcategory.get(spinnersubcat.getSelectedItemPosition()).getmStrId());
                                param.put("offer_title", offer_title_editText.getText().toString());
                                param.put("is_treated", mRadioYesTreated.isChecked() ? "1" : "0");
                             //   param.put("is_treated_product_list", treated_product_editText.isShown() ? treated_product_editText.getText().toString() : " ");
                                param.put("is_treated_description", desc_editText.getText().toString());

                                if(collect_deliver_spinner.getSelectedItemPosition()==1){
                                    param.put("selpick", "pick");

                                }
                                else if(collect_deliver_spinner.getSelectedItemPosition()==2){
                                    param.put("selpick", "collect");

                                }
                                else if(collect_deliver_spinner.getSelectedItemPosition()==3){
                                    param.put("selpick", "deliver");

                                }
                                        String str=units_spinner.getSelectedItem().toString();
                                        String strnew=str.replace("(s)","");

                                param.put("stock_unit", strnew);

                                param.put("pincode", "" + PrefManager.getZip(AntiAddofferActivity.this));
                                param.put("stock", available_quantity_editText.getText().toString());
//                                param.put("lat",PrefManager.getLAT(AntiAddofferActivity.this));
//                                param.put("lng", PrefManager.getLNG(AntiAddofferActivity.this));
//                                param.put("offer_place",PrefManager.getAddress(AntiAddofferActivity.this));
                             //   param.put("offer_price", "-");
                                param.put("user_id", PrefManager.getUserId(AntiAddofferActivity.this));




//                                if (availibilities_spinner.getSelectedItemPosition() == 1) {
//                                    param.put("available_type", mRadioEveryDay.isChecked() ? "1" : mRadioEveryWeekend.isChecked() ? "2" : "3");
//                                } else {
//                                    param.put("available_type", "");
//
//                                }

//                                if (availibilities_spinner.getSelectedItemPosition() == 1) {
//                                    if (mRadioAvailableLater.isChecked()) {
//                                        param.put("offer_available_date", mTxtSelectedDate.getText().toString());
//                                    }
//                                    else{
//                                        param.put("offer_available_date", "");
//                                    }
//
//                                    Log.d("fdfdf",time_spinner.getSelectedItem().toString());
//                                    param.put("offer_available_time", "" + time_spinner.getSelectedItem().toString());
//
//
//                                    param.put("quick_availability", "");
//
//                                } else {
//                                    param.put("offer_available_time", "");
//                                    param.put("offer_available_date", "");
//                                    param.put("quick_availability", time_spinner2.getSelectedItem().toString());
//                                    //    param.put("offer_available_time", "" + availibilities_spinner.getSelectedItem().toString());
//                                }


//                                if (yes_op_radioButton.isChecked()) {
//                                    param.put("prefered_picoreur", "" + otherpersonspinnerarr.get(other_person_spinner.getSelectedItemPosition()).getmStrId());
//                                }
//
//                                else{
//                                    param.put("prefered_picoreur","");
//                                }
                                       return param;
                            }

                            @Override
                            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                                Map<String, DataPart> params = new HashMap<>();
                                for (int i = 0; i < mPicorialArrListImg.size(); i++) {
                                    if (!mPicorialArrListImg.get(i).getmImgFrom().equalsIgnoreCase("Api")) {
                                        Log.d("checkgoing","checkgoing");
                                        if (bitmap.getHeight() > 1200 || bitmap.getWidth() > 1920) {
                                            Log.d("checkgoing1",String.valueOf(mPicorialArrListImg.get(i).getmBitmap()));
                                            params.put("offer_image[" + i + "]", new VolleyMultipartRequest.DataPart("offimageedt"+ i+".png", getFileDataFromDrawable(scaledBitmap(mPicorialArrListImg.get(i).getmBitmap()))));

                                        } else {
                                            Log.d("checkgoing2", String.valueOf(mPicorialArrListImg.get(i).getmBitmap()));
                                            params.put("offer_image[" + i + "]", new VolleyMultipartRequest.DataPart("offimageedt"+ i +".png", getFileDataFromDrawable(mPicorialArrListImg.get(i).getmBitmap())));
                                        }
                                    }
                                }

                                return params;
                            }

                        };
                      //  volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(600000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                        requestQueue4.add(volleyMultipartRequest);
                    } else {
                        Log.d("goinelse","goinelse");
                        CustomUtil.ShowDialog(AntiAddofferActivity.this,false);

                        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, BaseUrl.BASEURL.concat("addoffer"),
                                new Response.Listener<NetworkResponse>() {
                                    @Override
                                    public void onResponse(NetworkResponse response) {
                                        //progressDialog.dismiss();

                                        CustomUtil.DismissDialog(AntiAddofferActivity.this);

                                        Log.d("ghfhfgf", String.valueOf(response.statusCode));
                                        String s=new String(response.data);

                                        Log.d("addoffer",s);
                                        if (response.statusCode == 200) {
                                            Intent intent=new Intent(AntiAddofferActivity.this,UserAntigaspiHomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        //   progressDialog.dismiss();
//                        pd.dismiss();
                                        CustomUtil.DismissDialog(AntiAddofferActivity.this);

                                        Log.v("ResError", "" + volleyError.toString());
                                    }
                                }
                        ) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();
                                //   param.put("user_id", SharedPrefManager.getUserId(context));
//                                if(donner.isChecked()){
//
//                                    param.put("offer_type", "1");
//                                }
//
//                                if(echanger.isChecked()){
//
//                                    param.put("offer_type", "3");
//                                }


                                 param.put("don_address",choose_id);
                                 param.put("variety",Variety_editText.getText().toString());
                                param.put("cat_id", category.get(spinnercat.getSelectedItemPosition()).getmStrId());
                                param.put("description",desc_editText.getText().toString());
                                param.put("subcat_id", subcategory.get(spinnersubcat.getSelectedItemPosition()).getmStrId());
                                param.put("offer_title", offer_title_editText.getText().toString());
                                param.put("is_treated", mRadioYesTreated.isChecked() ? "1" : "0");
                             //   param.put("is_treated_product_list", treated_product_editText.isShown() ? treated_product_editText.getText().toString() : " ");
                                param.put("is_treated_description", desc_editText.getText().toString());
                                String str=units_spinner.getSelectedItem().toString();
                                String strnew=str.replace("(s)","");

                                param.put("stock_unit", strnew);
                                Log.d("checkdta",strnew+","+choose_id+","+Variety_editText.getText().toString());

                                if(collect_deliver_spinner.getSelectedItemPosition()==1){
                                    param.put("selpick", "pick");

                                }
                                else if(collect_deliver_spinner.getSelectedItemPosition()==2){
                                    param.put("selpick", "collect");

                                }
                                else if(collect_deliver_spinner.getSelectedItemPosition()==3){
                                    param.put("selpick", "deliver");

                                }


                                param.put("pincode", "" + PrefManager.getZip(AntiAddofferActivity.this));
                                param.put("stock", available_quantity_editText.getText().toString());

//                                if (availibilities_spinner.getSelectedItemPosition() == 1) {
//                                    param.put("available_type", mRadioEveryDay.isChecked() ? "1" : mRadioEveryWeekend.isChecked() ? "2" : "3");
//                                } else {
//                                    param.put("available_type", "");
//
//                                }
//                                if (availibilities_spinner.getSelectedItemPosition() == 1) {
//                                    if (mRadioAvailableLater.isChecked()) {
//                                        param.put("offer_available_date", mTxtSelectedDate.getText().toString());
//                                    }
//                                    else{
//                                        param.put("offer_available_date", "");
//                                    }
//                                    Log.d("dfdfdfdf",time_spinner.getSelectedItem().toString());
//                                    param.put("offer_available_time", "" + time_spinner.getSelectedItem().toString());
//                                    param.put("quick_availability", "");
//
//                                } else {
//                                    param.put("offer_available_time", "");
//                                    param.put("offer_available_date", "");
//                                    param.put("quick_availability", time_spinner2.getSelectedItem().toString());
//                                    //    param.put("offer_available_time", "" + availibilities_spinner.getSelectedItem().toString());
//                                }
                                // param.put("offer_price", "-");
//                                if (yes_op_radioButton.isChecked()) {
//                                    param.put("prefered_picoreur", "" + otherpersonspinnerarr.get(other_person_spinner.getSelectedItemPosition()).getmStrId());
//                                }
//                                else{
//                                    param.put("prefered_picoreur","no");
//                                }
//                                param.put("lat",PrefManager.getLAT(AntiAddofferActivity.this));
//                                param.put("lng", PrefManager.getLNG(AntiAddofferActivity.this));
//                                param.put("offer_place", PrefManager.getAddress(AntiAddofferActivity.this));


                                param.put("user_id", PrefManager.getUserId(AntiAddofferActivity.this));
                                return param;
                            }

                            @Override
                            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                                Map<String, DataPart> params = new HashMap<>();
                                for (int i = 0; i < mPicorialArrListImg.size(); i++) {
                                    if(bitmap. getHeight()>1200 || bitmap.getWidth()>1920) {

                                        params.put("offer_image[" + i + "]", new VolleyMultipartRequest.DataPart("offimagemain"+i+".png", getFileDataFromDrawable(scaledBitmap(mPicorialArrListImg.get(i).getmBitmap()))));

                                    }
                                    else {
                                        params.put("offer_image[" + i + "]", new VolleyMultipartRequest.DataPart("offimagemain"+i+".png", getFileDataFromDrawable(mPicorialArrListImg.get(i).getmBitmap())));
                                    }
                                }

                                return params;
                            }




                        };


                        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(600000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                        requestQueue4.add(volleyMultipartRequest);
                    }


                }


            }
        });
        //choose_spinner
        stringRequestchoose = new StringRequest(Request.Method.GET, BaseUrl.BASEURL.concat("get_donner_address"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("getchooseResponse", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            resultchoose = jsonObject.getJSONArray("address_list");
                            getchhoselist(resultchoose);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                  Toast.makeText(AntiAddofferActivity.this,String.valueOf(error),Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        requestQueuechoose.add(stringRequestchoose);

        stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_category"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                 Log.d("getcategory", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    result = jsonObject.getJSONArray("catlist");
                    getCatrName(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.d("dsdsddd", error.toString());

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(AntiAddofferActivity.this));
                return param;
            }

        };


        requestQueue.add(stringRequest);


        stringRequest3 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("picoreur_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("dsdssdsdadsdsdsddd", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    result3 = jsonObject.getJSONArray("picoreur_list");
                    getPicoName(result3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dsdsddd", error.toString());

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(AntiAddofferActivity.this));
                return param;
            }

        };


        requestQueue3.add(stringRequest3);


        spinnercat.setOnItemSelectedListener(this);
        spinnersubcat.setOnItemSelectedListener(this);
        units_spinner.setOnItemSelectedListener(this);
        other_person_spinner.setOnItemSelectedListener(this);
        availibilities_spinner.setOnItemSelectedListener(this);
        collect_deliver_spinner.setOnItemSelectedListener(this);
        choice_spinner.setOnItemSelectedListener(this);


        //BaseUrl.BASEURL.concat("get_anti_offer") AntiOfferAdapter ManageofferActivity
        if (getIntent().getStringExtra("Flag").equalsIgnoreCase("yes")) {
            product = (Product) getIntent().getSerializableExtra("Data");

            Log.d("previousdata", String.valueOf(product));
            setDataFromPrevious(product);

            calldonationaround(product.getOfferid());
        }


    }

    private void calldonationaround(String offerid) {
        StringRequest stringRequest2;
        RequestQueue requestQueue2;
        requestQueue2= Volley.newRequestQueue(AntiAddofferActivity.this);
        stringRequest2=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("donations_around_me_details"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("donations_around_et",response);
                        try {
                            JSONObject jsonObject1=new JSONObject(response);
                            JSONObject jsonObject=jsonObject1.getJSONObject("reservation_details");

                            JSONArray jsonarrimg=jsonObject1.getJSONArray("offr_images");


                            for(int i=0;i<jsonarrimg.length();i++){
                                JSONObject jsonObject2=jsonarrimg.getJSONObject(i);

                                Log.d("fsfsfs",jsonObject2.getString("image_url"));
                                arrayListimg.add(BaseUrl.ANTIGASPIURL.concat(jsonObject2.getString("image_url")));


                                    Bitmap mBtp = null;
                                    AntiPicoriarHelperClass picoriarHelperClass = new AntiPicoriarHelperClass();
                                    picoriarHelperClass.setmImgId(jsonObject2.getString("image_id"));
                                    picoriarHelperClass.setmBitmap(mBtp);
                                    picoriarHelperClass.setmImvByte(BaseUrl.ANTIGASPIURL.concat(jsonObject2.getString("image_url")));
                                    picoriarHelperClass.setmImgFrom("Api");
                                    mPicorialArrListImg.add(picoriarHelperClass);



                            }

                            if(arrayListimg.size()==0){
                                arrayListimg.add(jsonObject.getString("offer_image"));
                            }
                            setImgAdapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        requestQueue2.add(stringRequest2);
    }


    private void setDataFromPrevious(Product product) {

        Log.d("fdfdfdf",product.getProductimg());


        if(product.getOffertype().equals("1")){
            donner.setChecked(true);
        }
        else if(product.getOffertype().equals("3")){
            echanger.setChecked(true);
        }

//        if (mArrProductInformation.get(0).getmArrProductStockImg().size() > 0) {
//            mPicorialArrListImg.clear();
//            for (int i = 0; i < mArrProductInformation.get(0).getmArrProductStockImg().size(); i++) {
//                Bitmap mBtp = null;
//                AntiPicoriarHelperClass picoriarHelperClass = new AntiPicoriarHelperClass();
//                picoriarHelperClass.setmImgId(mArrProductInformation.get(0).getmArrProductStockImg().get(i).getmStrImageID());
//                picoriarHelperClass.setmBitmap(mBtp);
//                picoriarHelperClass.setmImvByte(BaseUrl.PRODUCTURL.concat( mArrProductInformation.get(0).getmArrProductStockImg().get(i).getmStrReceipeImg()));
//                picoriarHelperClass.setmImgFrom("Api");
//                mPicorialArrListImg.add(picoriarHelperClass);
//
//            }
//            setImgAdapter();
//        }


        if(!product.getProductimg().equals("null")){
            imageselecte=true;
            selectedimg.setVisibility(View.GONE);
         //   Picasso.with(AntiAddofferActivity.this).load(ANTIGASPIURL.concat(product.getProductimg())).into(selectedimg);
        }

        offer_title_editText.setText(product.getProductname());
        Variety_editText.setText(product.getVariety());

        if (product.getmIsTreated().equalsIgnoreCase("1")) {
            mRadioYesTreated.setChecked(true);
            mRadioNoTreated.setChecked(false);
        } else {
            mRadioYesTreated.setChecked(false);
            mRadioNoTreated.setChecked(true);
        }
        if (mRadioYesTreated.isChecked()) {
            treated_product_editText.setVisibility(View.VISIBLE);
            treated_product_editText.setText(product.getIsTreaded_Desc());
        } else {
            treated_product_editText.setVisibility(View.GONE);
            treated_product_editText.setText("");
        }
        desc_editText.setText(product.getIsTreaded_Desc());
        treated_product_editText.setText(product.getIsTreadedProductList());

        Log.d("fdfdf",product.getmSalePick());

            if(product.getmSalePick().equalsIgnoreCase("pick") ){
                collect_deliver_spinner.setSelection(1);
            }
            else if(product.getmSalePick().equalsIgnoreCase("collect") ){
                collect_deliver_spinner.setSelection(2);
            }
            else if(product.getmSalePick().equalsIgnoreCase("deliver") ){
                collect_deliver_spinner.setSelection(3);
            }








        String[] mStrArr1 = getResources().getStringArray(R.array.unitofmeasure);
        for (int i = 0; i < mStrArr1.length; i++) {
            String strnew =mStrArr1[i].replace("(s)","");
            if (strnew.equalsIgnoreCase(product.getmStockUnit())) {
                units_spinner.setSelection(i);
            }
        }
      //  postal_code_editText.setText(product.getmZipCode());
        available_quantity_editText.setText(product.getmStock());
        lat = product.getLat();
        lng = product.getLng();
       // location_editText.setText(product.getOfferPlace());
        if (!product.getmAvailableType().equalsIgnoreCase("") && !product.getmAvailableType().equalsIgnoreCase("0")) {
            availibilities_spinner.setSelection(1);
            availability = "0";
            available_textView.setVisibility(View.GONE);
            available_radiogroup.setVisibility(View.GONE);
            time_spinner.setVisibility(View.GONE);
            mLytDate.setVisibility(View.GONE);

            String[] mStrArr3 = getResources().getStringArray(R.array.timeSpinnerAvailable);
            String[] mStrTime = product.getmOfferAvailableTime().split(",");
            for (int i = 0; i < mStrArr3.length; i++) {
                if (mStrTime[0].equalsIgnoreCase(mStrArr3[i])) {
                    time_spinner.setSelection(i);
                }
            }

            if (product.getmAvailableType().equalsIgnoreCase("3")) {
                mLytDate.setVisibility(View.VISIBLE);
                mRadioAvailableLater.setChecked(true);

                Log.d("fdfsf",product.getmOfferAvailableData());
                mTxtSelectedDate.setText(product.getmOfferAvailableData());
                mArrDateList = new ArrayList<>();
                String[] mStrDate = product.getmOfferAvailableData().split(",");
                for (int i = 0; i < mStrDate.length-1; i++) {
                    String[] mstrdate = mStrDate[i].split("-");
                    int year = Integer.parseInt(mstrdate[2]);
                    int month = Integer.parseInt(mstrdate[1]);
                    int day = Integer.parseInt(mstrdate[0]);
                    mArrDateList.add(CalendarDay.from(year, month, day));

                }
            } else if (product.getmAvailableType().equalsIgnoreCase("2")) {
                mRadioAvailableLater.setChecked(false);
                mRadioEveryWeekend.setChecked(true);
                mRadioEveryDay.setChecked(false);

            } else {
                mRadioAvailableLater.setChecked(false);
                mRadioEveryWeekend.setChecked(false);
                mRadioEveryDay.setChecked(true);
            }
            availablenow.setVisibility(View.GONE);
            time_spinner2.setVisibility(View.GONE);

        } else {
            availibilities_spinner.setSelection(2);
            availability = "0";

            available_textView.setVisibility(View.GONE);
            available_radiogroup.setVisibility(View.GONE);
            time_spinner.setVisibility(View.GONE);

            availablenow.setVisibility(View.GONE);
            time_spinner2.setVisibility(View.GONE);



            if(product.getmStrQuickAvailbality().equalsIgnoreCase("+30 Minute") || product.getmStrQuickAvailbality().equalsIgnoreCase("+30 Minutes")){
                time_spinner2.setSelection(1);
            }
            else if(product.getmStrQuickAvailbality().equalsIgnoreCase("+1 hour") ||  product.getmStrQuickAvailbality().equalsIgnoreCase("+1 Heure")){
                time_spinner2.setSelection(2);
            }
            else if(product.getmStrQuickAvailbality().equalsIgnoreCase("+2 hour") ||  product.getmStrQuickAvailbality().equalsIgnoreCase("+2 Heure")){
                time_spinner2.setSelection(3);
            }




        }

        Picasso.with(AntiAddofferActivity.this).load("https://www.fruiteefy.fr/dev/uploads/offer/" + product.getProductimg()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap mBitmap, Picasso.LoadedFrom from) {
                bitmap = mBitmap;
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


    }

    private boolean ValidationCheck() {
        Validation validation = new Validation();

        if (offer_title_editText.getText().toString().isEmpty()) {
            offer_title_editText.setError(getResources().getString(R.string.fill_field));
            offer_title_editText.requestFocus();
        } else if(Variety_editText.getText().toString().isEmpty()){
            Variety_editText.setError(getResources().getString(R.string.fill_field));
            Variety_editText.requestFocus();
        }

        else if (spinnercat.getSelectedItemPosition() == 0) {
            Toast.makeText(AntiAddofferActivity.this, getResources().getString(R.string.selectcat), Toast.LENGTH_SHORT).show();
            return false;
        } else if (spinnersubcat.getSelectedItemPosition() == 0) {
            Toast.makeText(AntiAddofferActivity.this, getResources().getString(R.string.selectsubcat), Toast.LENGTH_SHORT).show();
            return false;

        }
//        else if (treated_product_radiogroup.getCheckedRadioButtonId() == -1) {
//            Toast.makeText(AntiAddofferActivity.this, getResources().getString(R.string.pleaseinformifproductaretreatedornot), Toast.LENGTH_SHORT).show();
//            return false;
//
//        } else if (treated_product_editText.getVisibility() == View.VISIBLE && !validation.edttxtvalidation(treated_product_editText, AntiAddofferActivity.this)) {
//
//            return false;
//
//        }
        else if (!validation.edttxtvalidation(desc_editText, AntiAddofferActivity.this)) {

            return false;

        }
//        else if (collectid.equals("")) {
//
//            Toast.makeText(AntiAddofferActivity.this, getResources().getString(R.string.enterdeliverymethod), Toast.LENGTH_SHORT).show();
//            return false;
//
//        }
        else if (!validation.edttxtvalidation(available_quantity_editText, AntiAddofferActivity.this)) {

            return false;

        }
//        else if (!validation.edttxtvalidation(location_editText, AntiAddofferActivity.this)) {
//            return false;
//
//        } else if (!validation.edttxtvalidation(location_editText, AntiAddofferActivity.this)) {
//            return false;
//
//        } else if (!validation.edttxtvalidation(postal_code_editText, AntiAddofferActivity.this)) {
//            return false;
//
//        }
//        else if (availability.equals("")) {
//            Toast.makeText(AntiAddofferActivity.this, getResources().getString(R.string.enteryouravailability), Toast.LENGTH_SHORT).show();
//            return false;
//
//
//        }

//        else if (availibilities_spinner.getSelectedItemPosition()==1 && available_radiogroup.getCheckedRadioButtonId() == -1) {
//            Toast.makeText(AntiAddofferActivity.this, getResources().getString(R.string.selectyouravailability), Toast.LENGTH_SHORT).show();
//            return false;
//
//        }
//
//        else if(availibilities_spinner.getSelectedItemPosition()==1 && time_spinner.getSelectedItemPosition()==0){
//            Toast.makeText(this, getResources().getString(R.string.pleaseselectavailabletime), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        else if(availibilities_spinner.getSelectedItemPosition()==2 && time_spinner2.getSelectedItemPosition()==0){
//            Toast.makeText(this, getResources().getString(R.string.pleaseselectavailabletime), Toast.LENGTH_SHORT).show();
//             return false;
//        }
//
//        else if(imageselecte==false){
//            Toast.makeText(AntiAddofferActivity.this,getResources().getString(R.string.ef_title_select_image),Toast.LENGTH_LONG).show();
//            return false;
//
//        }

        return true;
    }
    private void getchhoselist(JSONArray resultchoose) {
        chooselist.clear();
        try {
            // category.add(getResources().getString(R.string.category));
            //  for (int i = 0; i <= j.length(); i++) {
            //  JSONObject json = j.getJSONObject(i);
            ChooseHellperClass chooseHellperClass = new ChooseHellperClass();
            chooseHellperClass.setType(getResources().getString(R.string.select));
            chooseHellperClass.setNom("");
            chooseHellperClass.setDonner_address_id("");
            chooselist.add(chooseHellperClass);

            // }


            for (int i = 0; i <= resultchoose.length(); i++) {
                JSONObject json = resultchoose.getJSONObject(i);
                ChooseHellperClass chooseHellperClass1 = new ChooseHellperClass();
                chooseHellperClass1.setType(json.getString("type"));
                chooseHellperClass1.setNom(json.getString("nom"));
                chooseHellperClass1.setDonner_address_id(json.getString("donner_address_id"));
                chooselist.add(chooseHellperClass1);

            }
        } catch (JSONException e) {

        }
        //spinnercat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, category));
        choice_spinner.setAdapter(new AdapterChoose(chooselist, AntiAddofferActivity.this,0));

        if (getIntent().getStringExtra("Flag").equalsIgnoreCase("yes")) {
            product = (Product) getIntent().getSerializableExtra("Data");
            for (int i = 0; i < chooselist.size(); i++) {
                if (chooselist.get(i).getDonner_address_id().equalsIgnoreCase(product.getDon_address_id_fk())) {
                    choice_spinner.setSelection(i);
                    choose_id = chooselist.get(i).getDonner_address_id();
                }

            }


        }
    }
    private void getCatrName(JSONArray j) {

        try {
            // category.add(getResources().getString(R.string.category));
            //  for (int i = 0; i <= j.length(); i++) {
            //  JSONObject json = j.getJSONObject(i);
            HelperClass helperClass = new HelperClass();
            helperClass.setmStrName(getResources().getString(R.string.select));
            helperClass.setmStrId("");
            category.add(helperClass);

            // }


            for (int i = 0; i <= j.length(); i++) {
                JSONObject json = j.getJSONObject(i);
                HelperClass helperClass1 = new HelperClass();
                helperClass1.setmStrName(json.getString("cat_name"));
                helperClass1.setmStrId(json.getString("cat_id"));
                category.add(helperClass1);

            }
        } catch (JSONException e) {

        }
        //spinnercat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, category));
        spinnercat.setAdapter(new AdapterSpinnerCat(category, AntiAddofferActivity.this));
        if (getIntent().getStringExtra("Flag").equalsIgnoreCase("yes")) {
            product = (Product) getIntent().getSerializableExtra("Data");
            for (int i = 0; i < category.size(); i++) {
                if (category.get(i).getmStrId().equalsIgnoreCase(product.getOffer_cat_id())) {
                    spinnercat.setSelection(i);
                    category_id = category.get(i).getmStrId();

                    requestQueue1 = Volley.newRequestQueue(this);
                    stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_subcategory"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //    Log.d("dsdsddd", response);


                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                result1 = jsonObject.getJSONArray("subcatlist");
                                getSubCatrName(result1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("dsdsddd", error.toString());

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> param = new HashMap<>();
                            param.put("user_id", PrefManager.getUserId(AntiAddofferActivity.this));
                            param.put("cat_id", category_id);
                            return param;
                        }

                    };


                    requestQueue1.add(stringRequest1);
                }

            }
        }


    }


    private void getPicoName(JSONArray j) {

        try {
            HelperClass helperClass = new HelperClass();
            helperClass.setmStrName(getResources().getString(R.string.select));
            helperClass.setmStrId("");
            otherpersonspinnerarr.add(helperClass);
            //     otherpersonspinnerarr.add("Please select");
            for (int i = 0; i <= j.length(); i++) {
                JSONObject json = j.getJSONObject(i);
                HelperClass helperClass1 = new HelperClass();
                helperClass1.setmStrName(json.getString("firstname") + " " + json.getString("lastname"));
                helperClass1.setmStrId(json.getString("userid"));
                otherpersonspinnerarr.add(helperClass1);
                // otherpersonspinnerarr.add(json.getString("firstname"));

            }
        } catch (JSONException e) {

        }
        // other_person_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, otherpersonspinnerarr));
        other_person_spinner.setAdapter(new AdapterSpinnerCat(otherpersonspinnerarr, AntiAddofferActivity.this));
        if (getIntent().getStringExtra("Flag").equalsIgnoreCase("yes")) {
            product = (Product) getIntent().getSerializableExtra("Data");

            if (product.getmPrefered_picoreur().equalsIgnoreCase("") | product.getmPrefered_picoreur().equalsIgnoreCase("0")) {
                No_op_radiobutton.setChecked(true);
                other_person_spinner.setVisibility(View.GONE);

            } else {
                yes_op_radioButton.setChecked(true);
                other_person_spinner.setVisibility(View.GONE);
                for (int i = 0; i < otherpersonspinnerarr.size(); i++)
                    if (product.getmPrefered_picoreur().equalsIgnoreCase(otherpersonspinnerarr.get(i).getmStrId().toString())) {
                        other_person_spinner.setSelection(i);
                    }

            }

        }


        //     other_person_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, otherpersonspinnerarr));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(AntiAddofferActivity.this,UserAntigaspiHomeActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //  Log.d("sdsfggfgfgfgfgddd", String.valueOf(adapterView.getId()));
        //   Log.d("sdsfggfgfgfgfgddd", String.valueOf(R.id.spiner_category));

        if (adapterView.getId() == R.id.category_spinner) {

            spinnersubcat.setSelection(0);
            String selectedItem = adapterView.getItemAtPosition(i).toString();

            //    Log.d("kjjjjjkkjkjjk", String.valueOf(i));
            if (selectedItem.equals(getResources().getString(R.string.select))) {
                category_id = "";
            } else {
                category_id = getCatId(i - 1);

                requestQueue1 = Volley.newRequestQueue(this);
                stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_subcategory"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //    Log.d("dsdsddd", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            result1 = jsonObject.getJSONArray("subcatlist");
                            getSubCatrName(result1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dsdsddd", error.toString());

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(AntiAddofferActivity.this));
                        param.put("cat_id", category_id);
                        return param;
                    }

                };


                requestQueue1.add(stringRequest1);
            }
        }
        else if (adapterView.getId() == R.id.sub_category_spinner) {


            String selectedItem = adapterView.getItemAtPosition(i).toString();
            if (selectedItem.equals(getResources().getString(R.string.select))) {
                subcategory_id = "";
            } else {
                subcategory_id = getsubCatId(i - 1);
            }
        }
        else if(adapterView.getId() == R.id.choice_spinner){
            String selectedItem = adapterView.getItemAtPosition(i).toString();

                Log.d("choosekjkjkjkj", String.valueOf(i));
            if (selectedItem.equals(getResources().getString(R.string.select))) {
                choose_id = "";
            } else {
                choose_id = getChoose_id(i - 1);
            }
        }


        else if (adapterView.getId() == R.id.collect_deliver_spinner) {
            String selectedItem = adapterView.getItemAtPosition(i).toString();
            if (selectedItem.equals(getResources().getStringArray(R.array.collectnamearr)[0])) {
                collectid = "";
            } else {
                collectid = "0";

            }
        } else if (adapterView.getId() == R.id.units_spinner) {

            String selectedItem = adapterView.getItemAtPosition(i).toString();
            if (selectedItem.equals(getResources().getStringArray(R.array.unitofmeasure)[0])) {
                unitid = "";
            } else {
                unitid = "0";

            }
        } else if (adapterView.getId() == R.id.availibilities_spinner) {

            String selectedItem = adapterView.getItemAtPosition(i).toString();
            if (selectedItem.equals(getResources().getStringArray(R.array.availability)[0])) {
                availability = "";

                available_textView.setVisibility(View.GONE);
                available_radiogroup.setVisibility(View.GONE);
                time_spinner.setVisibility(View.GONE);
                time_spinner.setSelection(0);

                availablenow.setVisibility(View.GONE);
                time_spinner2.setVisibility(View.GONE);
                time_spinner2.setSelection(0);
                mTxtSelectedDate.setText("");
                mLytDate.setVisibility(View.GONE);


            } else if (selectedItem.equals(getResources().getStringArray(R.array.availability)[1])) {
                availability = "0";



                mRadioAvailableLater.setSelected(true);


                available_textView.setVisibility(View.GONE);
                available_radiogroup.setVisibility(View.GONE);
                time_spinner.setVisibility(View.GONE);
                availablenow.setVisibility(View.GONE);
                time_spinner2.setVisibility(View.GONE);
                time_spinner2.setSelection(0);



            } else if (selectedItem.equals(getResources().getStringArray(R.array.availability)[2])) {
                availability = "0";

                available_textView.setVisibility(View.GONE);
                available_radiogroup.setVisibility(View.GONE);
                time_spinner.setVisibility(View.GONE);
                time_spinner.setSelection(0);
                availablenow.setVisibility(View.GONE);
                time_spinner2.setVisibility(View.GONE);
                mLytDate.setVisibility(View.GONE);
                mTxtSelectedDate.setText("");




            } else {
                availability = "0";

            }
        }


    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AntiAddofferActivity.this,UserAntigaspiHomeActivity.class);
        startActivity(intent);
        finish();
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.yes_radioButton:
                if (checked)
                    treatedproducttitle_textView.setVisibility(View.VISIBLE);
                treated_product_editText.setVisibility(View.VISIBLE);

                break;
            case R.id.no_radioButton:
                if (checked)

                    if (treatedproducttitle_textView.getVisibility() == View.VISIBLE) {
                        treatedproducttitle_textView.setVisibility(View.GONE);
                    }

                if (treated_product_editText.getVisibility() == View.VISIBLE) {
                    treated_product_editText.setVisibility(View.GONE);
                    treated_product_editText.setText("");
                }

                break;
            case R.id.dont_no_radioButton:
                if (checked)
                    if (treatedproducttitle_textView.getVisibility() == View.VISIBLE) {
                        treatedproducttitle_textView.setVisibility(View.GONE);
                    }


                if (treated_product_editText.getVisibility() == View.VISIBLE) {
                    treated_product_editText.setVisibility(View.GONE);
                    treated_product_editText.setText("");
                }

                break;

            case R.id.yes_op_radioButton:

                if (checked) {
                    other_person_spinner.setVisibility(View.GONE);
                } else {


                    other_person_spinner.setVisibility(View.GONE);
                }


                break;

            case R.id.no_op_radioButton:
                other_person_spinner.setVisibility(View.GONE);
                break;
            case R.id.everyday_radioButton:
                //  Toast.makeText(this, "sddsdsdfsffs", Toast.LENGTH_SHORT).show();
                if (checked) {
                    //  Toast.makeText(this, "dfsffs", Toast.LENGTH_SHORT).show();
                    time_spinner.setVisibility(View.VISIBLE);
                    mLytDate.setVisibility(View.GONE);
                    mTxtSelectedDate.setText("");
                } else {
                    // other_person_spinner.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.every_weekend_radioButton:
                //  Toast.makeText(this, "sddsdsdfsffs", Toast.LENGTH_SHORT).show();
                if (checked) {
                    //  Toast.makeText(this, "dfsffs", Toast.LENGTH_SHORT).show();
                    time_spinner.setVisibility(View.VISIBLE);
                    mLytDate.setVisibility(View.GONE);
                    mTxtSelectedDate.setText("");
                }


                break;
            case R.id.availiable_later_radioButton:
                //  Toast.makeText(this, "sddsdsdfsffs", Toast.LENGTH_SHORT).show();
                if (checked) {
                    //  Toast.makeText(this, "dfsffs", Toast.LENGTH_SHORT).show();
                    time_spinner.setVisibility(View.VISIBLE);
                    mLytDate.setVisibility(View.VISIBLE);
                } else {
                    // other_person_spinner.setVisibility(View.VISIBLE);
                }


                break;

        }
    }


    //Show Calender Dialogue To select multiple Date (Note : if you want to select single ,none or multiple then set selection mode as per your requirement) .
    //Comment by sandesh on 20 Feb
    Dialog dialog1;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    MaterialCalendarView widget;
    private TextView mTxtSubmit;

    private void showCalenderDialogue() {
        dialog1 = new Dialog(AntiAddofferActivity.this);
        Window window = dialog1.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog1.setTitle(null);
        dialog1.setContentView(R.layout.calenderdialogue);
        dialog1.setCancelable(true);
        widget = dialog1.findViewById(R.id.calendarView);
        mTxtSubmit = dialog1.findViewById(R.id.btnsubmit);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);


//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2017, 11, 14);
//
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.set(2017, 11, 16);
//        widget.setDateSelected(calendar, true);
//        widget.setDateSelected(calendar1, true);

        if (mArrDateList.size() > 0) {
            for (int i = 0; i < mArrDateList.size(); i++) {
                widget.setDateSelected(CalendarDay.from(mArrDateList.get(i).getDate()), true);
                // mStrArr.add(FORMATTER.format(mArrDateList.get(i).getDate()));
            }
        }

        mTxtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mStrArr = new ArrayList<>();
                for (int i = 0; i < mArrDateList.size(); i++) {
                    mStrArr.add(FORMATTER.format(mArrDateList.get(i).getDate()));
                }
                String mStrDates = android.text.TextUtils.join(",", mStrArr);
                mTxtSelectedDate.setText(mStrDates);
                dialog1.dismiss();
            }
        });
        //Setup initial text
        //  textView.setText("No Selection");
        dialog1.show();
    }


    private void getSubCatrName(JSONArray j) {
        try {

            HelperClass helperClass = new HelperClass();
            helperClass.setmStrName(getResources().getString(R.string.select));
            helperClass.setmStrId("");
            subcategory.add(helperClass);

            //  subcategory.add();
            for (int i = 0; i <= j.length(); i++) {
                JSONObject json = j.getJSONObject(i);
                HelperClass helperClass1 = new HelperClass();
                helperClass1.setmStrName(json.getString("subcat_name"));
                helperClass1.setmStrId(json.getString("subcat_id"));
                subcategory.add(helperClass1);
            }
        } catch (JSONException e) {

        }
        spinnersubcat.setAdapter(new AdapterSpinnerCat(subcategory, AntiAddofferActivity.this));


        if (getIntent().getStringExtra("Flag").equalsIgnoreCase("yes")) {
            product = (Product) getIntent().getSerializableExtra("Data");
            for (int i = 0; i < subcategory.size(); i++) {
                if (subcategory.get(i).getmStrId().equalsIgnoreCase(product.getOffer_subcat_id())) {
                    spinnersubcat.setSelection(i);
                    subcategory_id = subcategory.get(i).getmStrId();
                }

            }


        }

//

    }

    private String getCatId(int position) {
        String Catid = "";

        try {
            JSONObject json = result.getJSONObject(position);

            Catid = json.getString("cat_id");
            Log.d("sdsdsd", Catid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return Catid;
    }


    private String getsubCatId(int position) {
        String subCatid = "";
        try {
            JSONObject json = result1.getJSONObject(position);

            subCatid = json.getString("subcat_id");
            Log.d("sdsdsd", subCatid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return subCatid;
    }
    private String getChoose_id(int position){
        String chooseid = "";
        try{
            JSONObject jsonObject = resultchoose.getJSONObject(position);
            chooseid = jsonObject.getString("donner_address_id");
            Log.d("getdonneraddid",chooseid);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return  chooseid  ;
    }
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //  Log.d("DAtes", "" + widget.getSelectedDates());
        mArrDateList.clear();
        mArrDateList.addAll(widget.getSelectedDates());


    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //getSupportActionBar().setTitle(FORMATTER.format((TemporalAccessor) date.getDate()));
    }


    public void showImagePickerOptions() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        if (ActivityCompat.checkSelfPermission(AntiAddofferActivity.this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (validation.neverAskAgainSelected(AntiAddofferActivity.this, WRITE_EXTERNAL_STORAGE, "STORAGE")) {
                    validation.displayNeverAskAgainDialog("storage", AntiAddofferActivity.this);
                } else {
                    ActivityCompat.requestPermissions(AntiAddofferActivity.this,
                            new String[]{WRITE_EXTERNAL_STORAGE},
                            1);
                }
            }

        } else {


            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, REQUEST_IMAGE_PICK);
        }
    }

    private void takePhotoFromCamera() {
        if (ActivityCompat.checkSelfPermission(AntiAddofferActivity.this, CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (validation.neverAskAgainSelected(this, CAMERA, "CAMERA")) {
                    validation.displayNeverAskAgainDialog("camera", AntiAddofferActivity.this);
                } else {

                    ActivityCompat.requestPermissions(this,
                            new String[]{CAMERA},
                            2);


                }
            }

        } else {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                File photoFile = null;
                try {
                    photoFile = createImageFile();

                } catch (IOException ex) {
                }

                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }

        }

    }


    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        Log.v("lenthImage", "" + image.length());
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int RC, @NonNull String per[], @NonNull int[] PResult) {


        switch (RC) {

            case 1:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {


                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, REQUEST_IMAGE_PICK);


                } else {

                    validation.setShouldShowStatus(this, WRITE_EXTERNAL_STORAGE, "STORAGE");

                }
                break;


            case 2:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                        File photoFile = null;
                        try {
                            photoFile = createImageFile();

                        } catch (IOException ex) {
                        }

                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(this,
                                    "com.example.android.fileprovider", photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }

                } else {

                    validation.setShouldShowStatus(this, CAMERA, "CAMERA");

                }
                break;
        }
    }


    private void updateProfilepic(final Bitmap bitmap) {

        //     progressDialog.show();


    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        lengthbmp = imageInByte.length;
        return byteArrayOutputStream.toByteArray();
    }


    Uri selectedImage = null;
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        inputStreamImg = null;
//        if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {
//            try {
//                 imageselecte=true;
//                selectedImage = data.getData();
//
//
//                Uri imguri=data.getData();
//
//
//                Log.d("dsad", String.valueOf(imguri));
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bitmap = (Bitmap) data.getExtras().get("data");
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
//                selectedimg.setVisibility(View.GONE);
//
//                selectedimg.setImageBitmap(bitmap);
//
//                Log.d("jhhjjhj",bitmap.getHeight()+" "+bitmap.getWidth());
//
//                Log.e("Activity", "Pick from Camera::>>> ");
//                //   mProfileimg.setImageBitmap(bitmap);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK) {
//            imageselecte=true;
//            selectedImage = data.getData();
//            //TestingDemo();
//            try {
//
//                Uri imguri=data.getData();
//                selectedimg.setVisibility(View.GONE);
//                selectedimg.setImageURI(imguri);
//
//                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
//                Log.e("Activity", "Pick from Gallery::>>> ");
//
////                imgPath = getRealPathFromURI(selectedImage);
////                destination = new File(imgPath.toString());
//                //  mProfileimg.setImageBitmap(bitmap);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (requestCode == 4) {
//            if (resultCode == Activity.RESULT_OK) {
//
//                // piclatlng = Autocomplete.getPlaceFromIntent(data).getLatLng();
//
//               // location_editText.setText(Autocomplete.getPlaceFromIntent(data).getAddress());
//                Autocomplete.getPlaceFromIntent(data).getLatLng();
//
//
//                lat = String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude);
//                lng = String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude);
//
//                //   PrefManager.setLAT(String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude), AntiAddofferActivity.this);
//                //  PrefManager.setLAT(String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude), AntiAddofferActivity.this);
//                Geocoder geocoder = new Geocoder(this);
//                try {
//                    List<Address> addresses = geocoder.getFromLocation(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude, Autocomplete.getPlaceFromIntent(data).getLatLng().longitude, 1);
//                    if (addresses != null) {
////                        edt_state.setText(addresses.get(0).getAdminArea());
////                        edt_city.setText(addresses.get(0).getLocality());
////                        edt_country.setText(addresses.get(0).getCountryName());
//                      //  postal_code_editText.setText(addresses.get(0).getPostalCode());
//                    }
//
//                } catch (IOException e) {
//
//                    e.printStackTrace();
//                }
//
//
//            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//                // TODO: Handle the error.
//                Status status = Autocomplete.getStatusFromIntent(data);
//                Log.d("fdgfdgfd", String.valueOf(status));
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }
//    }

    /**
     * Pic Image From Gallary and Camera Dialogue
     */
    // Select image from camera and gallery
    private void selectImage() {
        try {

            final CharSequence[] options = {getResources().getString(R.string.lbl_take_camera_picture), getResources().getString(R.string.lbl_choose_from_gallery), getResources().getString(R.string.cancel)};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AntiAddofferActivity.this);
            builder.setTitle(getResources().getString(R.string.select));
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals(getResources().getString(R.string.lbl_take_camera_picture))) {
                        PackageManager pm = getPackageManager();
                        int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
                        int hasStoragePermission = pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName());
                        if (hasPerm == PackageManager.PERMISSION_GRANTED && hasStoragePermission == PackageManager.PERMISSION_GRANTED) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                            dialog.dismiss();
                        } else {
                            //  int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
                            //  int hasStoragePermission=pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,getPackageName());
                            if (hasPerm != PackageManager.PERMISSION_GRANTED) {
                                openCustomDialogue("Veuillez autoriser l'accès à la caméra");
                                dialog.dismiss();

                            } else if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
                                openCustomDialogue("Veuillez permettre d'accéder au gallary");
                                dialog.dismiss();

                            }

//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                                intent.setData(uri);
//                                startActivity(intent);
                        }

                    } else if (options[item].equals(getResources().getString(R.string.lbl_choose_from_gallery)) ){
                        PackageManager pm = getPackageManager();
                        int hasPerm = pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName());
                        if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else {
                            openCustomDialogue("Veuillez permettre d'accéder au gallary");
                            dialog.dismiss();


                        }

                    } else if (options[item].equals(getResources().getString(R.string.cancel))) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

        } catch (Exception e) {
//            Intent intent = new Intent();
//            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            Uri uri = Uri.fromParts("com.cube9.gokidok", getPackageName(), null);
//            intent.setData(uri);
//            startActivity(intent);
            Toast.makeText(this, getResources().getString(R.string.camera_permission), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void openCustomDialogue(String s) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AntiAddofferActivity.this);
        builder.setMessage(s)
                .setCancelable(false)
                .setPositiveButton("AUTORISER MANUELLEMENT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();

    }

    /**
     * Access permission to upload photo marshmellow
     */
    public void showPhotoDialogue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> listPermission = new ArrayList<>();
            if (this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                listPermission.add(Manifest.permission.CAMERA);
            }
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                listPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                listPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (listPermission.size() > 0) {
                String[] permArray = listPermission.toArray(new String[listPermission.size()]);
                requestPermissions(permArray, 2909);
            }
        }
    }

    /**
     * On Screen permission for android 6.0 and above
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    10
            );
        }
    }


    public Bitmap scaledBitmap(Bitmap bitmap){



        Bitmap scaled= Bitmap.createScaledBitmap(bitmap, 1200, 1000, true);


        return scaled;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {
            try {
                selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                byte[] mByteArr = getFileDataFromDrawable(bitmap);

                AntiPicoriarHelperClass picoriarHelperClass = new AntiPicoriarHelperClass();
                picoriarHelperClass.setmImgId("");
                picoriarHelperClass.setmImgFrom("storage");
                picoriarHelperClass.setmImvByte("");
                picoriarHelperClass.setmBitmap(bitmap);
                mPicorialArrListImg.add(picoriarHelperClass);

                setImgAdapter();

                Log.e("Activity", "Pick from Camera::>>> ");
                //   mProfileimg.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK) {

            selectedImage = data.getData();
            //TestingDemo();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");
                byte[] mByteArr = getFileDataFromDrawable(bitmap);

                AntiPicoriarHelperClass picoriarHelperClass = new AntiPicoriarHelperClass();
                picoriarHelperClass.setmImgId("");
                picoriarHelperClass.setmImgFrom("storage");
                picoriarHelperClass.setmImvByte("");
                picoriarHelperClass.setmBitmap(bitmap);
                mPicorialArrListImg.add(picoriarHelperClass);

                setImgAdapter();
//                imgPath = getRealPathFromURI(selectedImage);
//                destination = new File(imgPath.toString());
                //  mProfileimg.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void setImgAdapter() {
        AddantiofferImageAdapter addPicoImageAdapter = new AddantiofferImageAdapter(mPicorialArrListImg, AntiAddofferActivity.this, positionInterface);
        mRecylerImgListview.setAdapter(addPicoImageAdapter);
    }


    PositionInterface positionInterface = new PositionInterface() {
        @Override
        public void onClick(int pos) {
            if (mPicorialArrListImg.get(pos).getmImgFrom().equalsIgnoreCase("Api")) {
                wsDeleteImageProduct(mPicorialArrListImg.get(pos).getmImgId());
                mPicorialArrListImg.remove(pos);
                mRecylerImgListview.getAdapter().notifyDataSetChanged();
            } else {
                mPicorialArrListImg.remove(pos);
                mRecylerImgListview.getAdapter().notifyDataSetChanged();
            }

        }

        @Override
        public void onClickWithFlag(int pos, int flag) {

        }
    };



    private void wsDeleteImageProduct(String ID) {
        RequestQueueDeleteImg = Volley.newRequestQueue(this);

        StringRequestDeleteImg = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("delete_offr_image_picorear"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.d("dsdsddd", error.toString());

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("image_id", ID);
                return param;
            }

        };
        RequestQueueDeleteImg.add(StringRequestDeleteImg);

    }

}
//458,314
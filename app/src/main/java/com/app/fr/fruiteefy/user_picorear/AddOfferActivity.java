package com.app.fr.fruiteefy.user_picorear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.app.fr.fruiteefy.Util.DynamicClass;
import com.app.fr.fruiteefy.Util.PicoriarHelperClass;
import com.app.fr.fruiteefy.Util.PositionInterface;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.ProductHelperClass;
import com.app.fr.fruiteefy.Util.VolleyMultipartRequest;
import com.app.fr.fruiteefy.user_picorear.Adapter.AdapterSpinnerCatPicoriar;
import com.app.fr.fruiteefy.user_picorear.Adapter.AddPicoImageAdapter;
import com.app.fr.fruiteefy.user_picorear.Adapter.SpineerUnitAdapter;
import com.app.fr.fruiteefy.user_picorear.dataclasses.CategoryHelperClass;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddOfferActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mLinearMainLyt, mLinearIngredient;
    private ArrayList<DynamicClass> mArrayViewDataList, mArrayViewIngredientDataList;
    private Button mBtnAddLayout, mBtnAddingredient;
    private RecyclerView mRecylerImgListview, mRecyclerImgIngredientListview;
    private TextView mTxtSelectImg;
    private EditText mEdtProductName, mEdtDescription, mEdtDeliveryCost;
    private File destination = null;
    private InputStream inputStreamImg;
    private RadioButton everyday_radioButton,every_weekend_radioButton,availiable_later_radioButton;
    private String imgPath = null,selelctedoffertype,strvariety,availabletype,selelctedtime;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private final int PICK_IMAGE_CAMERAINGREDIENT = 11, PICK_IMAGE_GALLERYINGREDIENT = 22;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private long lengthbmp;
    private Bitmap bitmap;
    private ArrayList<PicoriarHelperClass> mPicorialArrListImg, mPicorialArrListImgIngredient;
    private RequestQueue RequestQueueCategory, RequestQueueSubCategory, RequestQueueAddPicoriar, RequestQueueGetPicoriar, RequestQueueDeleteImg;
    private StringRequest StringRequestCategory, StringRequestSubCategory, StringRequestGetOffer;
    private Spinner mSpnCat, mSpnSubCat,offertypespn,time_spinner;
    private ArrayList<CategoryHelperClass> mCategoryArrList, mSubCategoryList;
    ArrayList<String> offertypearrayList = new ArrayList<>();
    ArrayList<String> timelist = new ArrayList<>();
    private LinearLayout mLytDeliver, mLytCollect,mLytbelowcomelater;
    private RadioGroup mRadioGroupDeliver, mRadioGroupCollect;
    private RadioButton mRadioButtonYesDeliver, mRadioButtonNoDeliver, mRadioBtnYesCollect, mRadioBtnNoCollect;
    private TextView mTxtSelectImgIngredient;
    private TextView mTv_AvailablityDate, mTv_ProductionDate, mTv_BestBefore;
    private ImageView mImv_ProductionDate, imImv_BestBefore;
    private ArrayList<CalendarDay> mArrProductDate, mArrBeforeDate;
    private ArrayList<CalendarDay> mArrAvailablityDate;
    private LinearLayout mLinearIngredientSection;
    private TextView mTxtAvailablityDate;
    private ImageView mTxtSelectAvailabiltyDate;
    private TextView mBtnOfferValidate;
    private EditText mEdtReceipeDesc;
    private EditText mEdtAntigaspiTips;
    private  EditText EdtVariety;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pico_addoffer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUI();

    }

    private void setUI() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        EdtVariety= findViewById(R.id.EdtVariety);
        mEdtAntigaspiTips = findViewById(R.id.anitgaspitips);
        mEdtProductName = findViewById(R.id.productname);
        mEdtDescription = findViewById(R.id.EdtDesc);
        mEdtDeliveryCost = findViewById(R.id.EdtDeliver);
        mEdtReceipeDesc = findViewById(R.id.EdtReceipeDesc);
        showPhotoDialogue();
        verifyStoragePermissions(this);
        mPicorialArrListImgIngredient = new ArrayList<>();
        mLinearIngredient = findViewById(R.id.LytIngredients);
        mLinearIngredientSection = findViewById(R.id.ingredientLyt);
        mLinearIngredientSection.setVisibility(View.GONE);
        mArrProductDate = new ArrayList<>();
        mArrBeforeDate = new ArrayList<>();
        mArrAvailablityDate = new ArrayList<>();
        mRecyclerImgIngredientListview = findViewById(R.id.img_ingredient);
        mRecyclerImgIngredientListview.setLayoutManager(new LinearLayoutManager(AddOfferActivity.this, LinearLayoutManager.HORIZONTAL, false));
        mImv_ProductionDate = findViewById(R.id.imv_selectproductiondate);
        imImv_BestBefore = findViewById(R.id.imv_selectdatebefore);
        mTv_BestBefore = findViewById(R.id.mTxtDatesSelectedBefore);
        mTv_ProductionDate = findViewById(R.id.mTxtProductionDatesSelected);
        mTxtAvailablityDate = findViewById(R.id.mTxtAvailablity);
        mTxtSelectAvailabiltyDate = findViewById(R.id.imv_selectavailabledate);
        //layout for radiogroup
        mLytDeliver = findViewById(R.id.deliverLyt);
        mLytCollect = findViewById(R.id.collectLyt);
        mLytbelowcomelater = findViewById(R.id.belowcomelater);
        //radio groups
        mRadioGroupDeliver = findViewById(R.id.radiogrop_deliver);
        mRadioGroupCollect = findViewById(R.id.radiogroup_collect);
        //radiobutton for group
        mRadioButtonYesDeliver = findViewById(R.id.yes_deliver);
        mRadioButtonNoDeliver = findViewById(R.id.no_deliver);
        mRadioBtnYesCollect = findViewById(R.id.yes_collect);
        mRadioBtnNoCollect = findViewById(R.id.no_collect);

        everyday_radioButton = findViewById(R.id.everyday_radioButton);
        every_weekend_radioButton = findViewById(R.id.every_weekend_radioButton);
        availiable_later_radioButton = findViewById(R.id.availiable_later_radioButton);

        mTxtSelectImgIngredient = findViewById(R.id.selectimgingredient);
        mBtnAddingredient = findViewById(R.id.add_ingredent);
        mCategoryArrList = new ArrayList<>();
        mSubCategoryList = new ArrayList<>();
        mSpnCat = findViewById(R.id.spinner_cat);
        offertypespn = findViewById(R.id.offertypespn);
        time_spinner = findViewById(R.id.time_spinner);
        mSpnSubCat = findViewById(R.id.spinner_subcat);
        mPicorialArrListImg = new ArrayList<>();
        mArrayViewDataList = new ArrayList<>();
        mArrayViewIngredientDataList = new ArrayList();
        mLinearMainLyt = findViewById(R.id.Lyt_Main);
        mBtnAddLayout = findViewById(R.id.add_button);
        mTxtSelectImg = findViewById(R.id.selectImg);
        mRecylerImgListview = findViewById(R.id.imagelist);
        mRecylerImgListview.setLayoutManager(new LinearLayoutManager(AddOfferActivity.this, LinearLayoutManager.HORIZONTAL, false));
        RequestQueueCategory = Volley.newRequestQueue(this);
        RequestQueueSubCategory = Volley.newRequestQueue(this);
        RequestQueueAddPicoriar = Volley.newRequestQueue(this);
        RequestQueueGetPicoriar = Volley.newRequestQueue(this);

        //Categary Spinner
        mSpnCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    if (mCategoryArrList.get(position).getmStrReceipyId().equalsIgnoreCase("1")) {
                        mLinearIngredientSection.setVisibility(View.VISIBLE);
                    } else {
                        mLinearIngredientSection.setVisibility(View.GONE);
                    }
                    wsCallSubCategory(mCategoryArrList.get(position).getmStrCatId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //timespinner
        timelist.add("7h-9h");
        timelist.add("9h-12h");
        timelist.add("12h-14h");
        timelist.add("14h-17h");
        timelist.add("17h-19h");
        timelist.add("19h-21h");
        timelist.add("Toute la journée");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, timelist);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(arrayAdapter2);


        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selelctedtime = parent.getItemAtPosition(position).toString();
                if(selelctedtime.equals("7h-9h"))
                {
                    selelctedtime = "7h-9h";

                }else if(selelctedtime.equals("9h-12h"))
                {
                    selelctedtime = "9h-12h";

                }else if(selelctedtime.equals("12h-14h"))
                {
                    selelctedtime = "12h-14h";

                }else if(selelctedtime.equals("14h-17h"))
                {
                    selelctedtime = "14h-17h";

                }
                else if(selelctedtime.equals("17h-19h"))
                {
                    selelctedtime = "17h-19h";

                }
                else if(selelctedtime.equals("19h-21h"))
                {
                    selelctedtime = "19h-21h";

                }
                else if(selelctedtime.equals("Toute la journée"))
                {
                    selelctedtime = "Toute la journée";

                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
//offertype spinner

        offertypearrayList.add(getResources().getString(R.string.sell_ot));
        offertypearrayList.add(getResources().getString(R.string.sell_ep_ot));
        offertypearrayList.add(getResources().getString(R.string.give_ot));
        offertypearrayList.add(getResources().getString(R.string.toexchange_ot));
        offertypearrayList.add(getResources().getString(R.string.search_ot));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, offertypearrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        offertypespn.setAdapter(arrayAdapter);
        offertypespn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selelctedoffertype = parent.getItemAtPosition(position).toString();
                 if(selelctedoffertype.equals(getResources().getString(R.string.sell_ot)))
                 {
                     selelctedoffertype = "vendre";
                     createDefaultLyt(0);
                     createDefaultIngredientLyt(0);
                     setTitle(getResources().getString(R.string.addanoffer));
                 }else if(selelctedoffertype.equals(getResources().getString(R.string.sell_ep_ot)))
                 {
                     selelctedoffertype = "echange possible";
                     createDefaultLyt(0);
                     createDefaultIngredientLyt(0);
                     setTitle(getResources().getString(R.string.addanoffer));
                 }else if(selelctedoffertype.equals(getResources().getString(R.string.give_ot)))
                 {
                     selelctedoffertype = "donner";
                     mLinearMainLyt.removeAllViews();
                 }else if(selelctedoffertype.equals(getResources().getString(R.string.toexchange_ot)))
                 {
                     selelctedoffertype = "echanger";
                     mLinearMainLyt.removeAllViews();
                 }
                 else if(selelctedoffertype.equals(getResources().getString(R.string.search_ot)))
                 {
                     selelctedoffertype = "chercher";
                     mLinearMainLyt.removeAllViews();
                 }

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        wsCallCategoryList();  //get categary
        mBtnOfferValidate = findViewById(R.id.offer_validate_button);
        setOnClickListener();
        if (!getIntent().getStringExtra("From").equalsIgnoreCase("Update")) {
            createDefaultLyt(0);
            createDefaultIngredientLyt(0);
            setTitle(getResources().getString(R.string.addanoffer));
        } else {
            setTitle(getResources().getString(R.string.updateoffer));
        }


    }

    //add picoriar API
    private void wsCallAddPicoriar() {
        CustomUtil.ShowDialog(AddOfferActivity.this,false);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, BaseUrl.BASEURL.concat("add_product_picorear"),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        CustomUtil.DismissDialog(AddOfferActivity.this);
                        //progressDialog.dismiss();
                        if (response.statusCode == 200) {

                            String resultResponse = new String(response.data);
                            Log.d("add_product_picorear",resultResponse);


                            Intent intent=new Intent(AddOfferActivity.this,UserPicorearHomeActivity.class);
                            startActivity(intent);

                            try {
                                JSONObject jsonObject=new JSONObject(resultResponse);
                                if(jsonObject.getString("status").equals("1")){
                                    finish();
                                }

                                Toast.makeText(AddOfferActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //   progressDialog.dismiss();
//                        pd.dismiss();
                        CustomUtil.DismissDialog(AddOfferActivity.this);
                        Log.v("ResError", "" + volleyError.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();


                param.put("user_id", PrefManager.getUserId(AddOfferActivity.this));
                param.put("product_name", mEdtProductName.getText().toString());
                param.put("cat_id", mCategoryArrList.get(mSpnCat.getSelectedItemPosition()).getmStrCatId());
                param.put("subcat_id", mSubCategoryList.get(mSpnSubCat.getSelectedItemPosition()).getmStrCatId());
                param.put("description", mEdtDescription.getText().toString());
                param.put("offer_type",selelctedoffertype);
                param.put("variety",EdtVariety.getText().toString());
                param.put("available_type",availabletype);
                param.put("offer_available_time",selelctedtime);



                param.put("delivery_fees", mLytDeliver.isShown() ? mEdtDeliveryCost.getText().toString() : "0");
                param.put("offer_avail_date", mLytCollect.isShown() ? mTxtAvailablityDate.getText().toString() : "0");
                param.put("inv_stock", mStrInv_Stock);
                param.put("inv_price", mStrInv_Price);
                param.put("inv_unit", mStrInv_Unit);
                param.put("inv_weight", mStrInv_Weight);



                if (mCategoryArrList.get(mSpnCat.getSelectedItemPosition()).getmStrReceipyId().equalsIgnoreCase("1")) {
                    param.put("recipestatus", "1");
                    param.put("tips", mEdtAntigaspiTips.getText().toString());
                    param.put("recipy_desc", mEdtReceipeDesc.getText().toString());
                    param.put("man_date", mTv_ProductionDate.getText().toString());
                    param.put("best_before", mTv_BestBefore.getText().toString());
                    param.put("incrediant_name", mStrIngredientName);
                    param.put("unit", mSrtIngredientUnit);
                    param.put("weight", mStrIngredientAmont);
                } else {
                    param.put("recipestatus", "0");

                    // param.put("tips", "");
                    // param.put("recipy_desc", "");
                    // param.put("man_date", "");
                    // param.put("best_before", "");
                    //  param.put("incrediant_name", "");
                    // param.put("unit", "");
                    // param.put("weight", "");


                }

                param.put("deliverycollectname", mRadioButtonYesDeliver.isChecked() ? "delcollectyes" : "delcollectno");
                param.put("collectionname", mRadioBtnYesCollect.isChecked() ? "collectionyes" : "collectionno");

                Log.d("dfsfds",mStrInv_Unit);
               // Log.d("availabletype",availabletype);
                Log.d("sdsdssda",PrefManager.getUserId(AddOfferActivity.this));
                Log.d("sdsdssda",mEdtProductName.getText().toString());
                Log.d("sdsdssda",mCategoryArrList.get(mSpnCat.getSelectedItemPosition()).getmStrCatId());
                Log.d("sdsdssda",mSubCategoryList.get(mSpnSubCat.getSelectedItemPosition()).getmStrCatId());
                Log.d("sdsdssda",mEdtDescription.getText().toString());
                Log.d("sdsdssda",mLytDeliver.isShown() ? mEdtDeliveryCost.getText().toString() : "");
                Log.d("sdsdssda",mLytCollect.isShown() ? mTxtAvailablityDate.getText().toString() : "");
                Log.d("sdsdssda",mStrInv_Stock);
                Log.d("sdsdssda",mStrInv_Price);
                Log.d("sdsdssda",mStrInv_Unit);
                Log.d("sdsdssda",mStrInv_Weight);
                Log.d("sdsdssda", mRadioButtonYesDeliver.isChecked() ? "delcollectyes" : "delcollectno");
                Log.d("sdsdssda",mRadioBtnYesCollect.isChecked() ? "collectionyes" : "collectionno");

                return param;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                Log.d("sizeofproductimgs", String.valueOf(mPicorialArrListImg.size()));
                for (int i = 0; i < mPicorialArrListImg.size(); i++) {
                    Log.d("sizeofproductimgs"+i,mPicorialArrListImg.get(i).getmImgId());
                    if(bitmap. getHeight()>1200 || bitmap.getWidth()>1920) {
                        params.put("product_image[" + i + "]", new VolleyMultipartRequest.DataPart("proimagemain"+i+".png", getFileDataFromDrawable(scaledBitmap(mPicorialArrListImg.get(i).getmBitmap()))));
                        Log.d("imageif","proimagemain"+i+".png");
//                        params.put("product_image[" + i + "]", new VolleyMultipartRequest.DataPart("proimage"+i+".png", getFileDataFromDrawable(scaledBitmap(mPicorialArrListImg.get(i).getmBitmap()))));
//Log.d("product_imagei","imagein");
                    }
                    else {

                        params.put("product_image[" + i + "]", new VolleyMultipartRequest.DataPart("proimagemain"+i+".png", getFileDataFromDrawable(mPicorialArrListImg.get(i).getmBitmap())));
                        Log.d("imagee","proimagemain"+i+".png");
//                        params.put("product_image[" + i + "]", new VolleyMultipartRequest.DataPart("proimage"+i+".png", getFileDataFromDrawable(mPicorialArrListImg.get(i).getmBitmap())));
//                        Log.d("product_imagee","imagein");
                    }
                }
                if (mCategoryArrList.get(mSpnCat.getSelectedItemPosition()).getmStrReceipyId().equalsIgnoreCase("1")) {
                    for (int i = 0; i < mPicorialArrListImgIngredient.size(); i++) {
                        if (bitmap.getHeight() > 1200 || bitmap.getWidth() > 1920) {
                            params.put("recipy_image[" + i + "]", new VolleyMultipartRequest.DataPart("recimage"+i+".png", getFileDataFromDrawable(scaledBitmap(mPicorialArrListImgIngredient.get(i).getmBitmap()))));

                        } else {
                            params.put("recipy_image[" + i + "]", new VolleyMultipartRequest.DataPart("recimage"+i+".png", getFileDataFromDrawable(mPicorialArrListImgIngredient.get(i).getmBitmap())));
                        }
                    }
                }
                return params;
            }
        };
        RequestQueueAddPicoriar.add(volleyMultipartRequest);
    }


    //add picoriar API
    private void wsCallUpdatePicoriar(String mProductID) {
        CustomUtil.ShowDialog(AddOfferActivity.this,false);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, BaseUrl.BASEURL.concat("edit_product_picorear"),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        CustomUtil.DismissDialog(AddOfferActivity.this);
                        //progressDialog.dismiss();
                        if (response.statusCode == 200) {
                            String res=new String(response.data);
                            Log.d("edit_product_picorear",res);
                            finish();
                            Intent intent=new Intent(AddOfferActivity.this,UserPicorearHomeActivity.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        CustomUtil.DismissDialog(AddOfferActivity.this);
                        //   progressDialog.dismiss();
//                        pd.dismiss();
                        Log.v("ResError", "" + volleyError.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("product_id", mProductID);
                param.put("user_id", PrefManager.getUserId(AddOfferActivity.this));
                param.put("product_name", mEdtProductName.getText().toString());
                param.put("cat_id", mCategoryArrList.get(mSpnCat.getSelectedItemPosition()).getmStrCatId());
                param.put("subcat_id", mSubCategoryList.get(mSpnSubCat.getSelectedItemPosition()).getmStrCatId());
                param.put("description", mEdtDescription.getText().toString());
                param.put("offer_type",selelctedoffertype);
                param.put("variety",EdtVariety.getText().toString());
                param.put("delivery_fees", mLytDeliver.isShown() ? mEdtDeliveryCost.getText().toString() : "");
                param.put("offer_avail_date", mLytCollect.isShown() ? mTxtAvailablityDate.getText().toString() : "");
                param.put("inv_stock", mStrInv_Stock);
                param.put("inv_price", mStrInv_Price);
                param.put("inv_unit", mStrInv_Unit);
                param.put("inv_weight", mStrInv_Weight);

                if(availiable_later_radioButton.isChecked()){
                    param.put("available_type","3");
                }else if(everyday_radioButton.isChecked()){
                    param.put("available_type","1");
                }else if(every_weekend_radioButton.isChecked()){
                    param.put("available_type","2");
                }

                if( time_spinner.getSelectedItem().toString().equalsIgnoreCase("7h-9h")){
                    param.put("offer_available_time","7h-9h");
                }
                else if(time_spinner.getSelectedItem().toString().equalsIgnoreCase("9h-12h")){
                    param.put("offer_available_time","9h-12h");
                }
                else if(time_spinner.getSelectedItem().toString().equalsIgnoreCase("12h-14h")){
                    param.put("offer_available_time","12h-14h");
                }
                else if(time_spinner.getSelectedItem().toString().equalsIgnoreCase("14h-17h")){
                    param.put("offer_available_time","14h-17h");
                }
                else if(time_spinner.getSelectedItem().toString().equalsIgnoreCase("17h-19h")){
                    param.put("offer_available_time","17h-19h");
                }
                else if(time_spinner.getSelectedItem().toString().equalsIgnoreCase("19h-21h")){
                    param.put("offer_available_time","19h-21h");
                }
                else if(time_spinner.getSelectedItem().toString().equalsIgnoreCase("Toute la journée")){
                    param.put("offer_available_time","Toute la journée");
                }

                if (mCategoryArrList.get(mSpnCat.getSelectedItemPosition()).getmStrReceipyId().equalsIgnoreCase("1")) {
                    param.put("recipestatus", "1");
                    param.put("tips", mEdtAntigaspiTips.getText().toString());
                    param.put("recipy_desc", mEdtReceipeDesc.getText().toString());
                    param.put("man_date", mTv_ProductionDate.getText().toString());
                    param.put("best_before", mTv_BestBefore.getText().toString());
                    param.put("incrediant_name", mStrIngredientName);
                    param.put("unit", mSrtIngredientUnit);
                    param.put("weight", mStrIngredientAmont);
                } else {
                    param.put("recipestatus", "0");

                }
                param.put("deliverycollectname", mRadioButtonYesDeliver.isChecked() ? "delcollectyes" : "delcollectno");
                param.put("collectionname", mRadioBtnYesCollect.isChecked() ? "collectionyes" : "collectionno");

                return param;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                for (int i = 0; i < mPicorialArrListImg.size(); i++) {
                    if (!mPicorialArrListImg.get(i).getmImgFrom().equalsIgnoreCase("Api")) {
                        if (bitmap.getHeight() > 1200 || bitmap.getWidth() > 1920) {
                            params.put("product_image[" + i + "]", new VolleyMultipartRequest.DataPart("proimageedt"+i+".png", getFileDataFromDrawable(scaledBitmap(mPicorialArrListImg.get(i).getmBitmap()))));
 Log.d("edit_image","proimageedt"+i+".png");
                        } else {
                            params.put("product_image[" + i + "]", new VolleyMultipartRequest.DataPart("proimageedt"+i+".png", getFileDataFromDrawable(mPicorialArrListImg.get(i).getmBitmap())));
                            Log.d("edit_image","proimageedt2"+i+".png");
                        }
                    }

                }
                if (mCategoryArrList.get(mSpnCat.getSelectedItemPosition()).getmStrReceipyId().equalsIgnoreCase("1")) {
                    for (int i = 0; i < mPicorialArrListImgIngredient.size(); i++) {
                        if (!mPicorialArrListImgIngredient.get(i).getmImgFrom().equalsIgnoreCase("Api")) {
                            if (bitmap.getHeight() > 1200 || bitmap.getWidth() > 1920) {

                                params.put("recipy_image[" + i + "]", new VolleyMultipartRequest.DataPart("recimageedt"+i+".png", getFileDataFromDrawable(scaledBitmap(mPicorialArrListImgIngredient.get(i).getmBitmap()))));

                            }
                            else{
                                params.put("recipy_image[" + i + "]", new VolleyMultipartRequest.DataPart("recimageedt"+i+".png", getFileDataFromDrawable(mPicorialArrListImgIngredient.get(i).getmBitmap())));

                            }
                        }
                    }
                }
                return params;
            }
        };
        RequestQueueAddPicoriar.add(volleyMultipartRequest);
    }


    //add picoriar Validation Check
    boolean ValidationCheck() {
        EdtVariety.clearFocus();
        EdtVariety.setError(null);
        mEdtProductName.clearFocus();
        mEdtProductName.setError(null);
        mEdtDescription.clearFocus();
        mEdtDescription.setError(null);
        if (mEdtProductName.getText().toString().equalsIgnoreCase("")) {
            mEdtProductName.setError(getResources().getString(R.string.enterproductname));
            mEdtProductName.requestFocus();
            return false;
        } else if (mPicorialArrListImg.size() == 0) {
            Toast.makeText(this, getResources().getString(R.string.add_a_photo_image), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mCategoryArrList.get(mSpnCat.getSelectedItemPosition()).getmStrCatId().equalsIgnoreCase("")) {
            Toast.makeText(this, getResources().getString(R.string.selectcat), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mSubCategoryList.get(mSpnSubCat.getSelectedItemPosition()).getmStrCatId().equalsIgnoreCase("")) {
            Toast.makeText(this, getResources().getString(R.string.selectsubcat), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (mEdtDescription.getText().toString().equalsIgnoreCase("")) {
            mEdtDescription.requestFocus();
            mEdtDescription.setError(getResources().getString(R.string.enterproductdesc));
            return false;
        }
        else if (EdtVariety.getText().toString().equalsIgnoreCase("")) {
            EdtVariety.requestFocus();
            EdtVariety.setError(getResources().getString(R.string.entervariety));
            return false;
        }
        else if (selelctedoffertype.equalsIgnoreCase("")) {
            Toast.makeText(this, getResources().getString(R.string.selectofferetype), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (mArrayViewDataList.size() == 0) {
            Toast.makeText(this, getResources().getString(R.string.pleaseenterproductinfo), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mRadioGroupDeliver.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getResources().getString(R.string.pleaseselctdeliveryinfo), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mRadioGroupDeliver.getCheckedRadioButtonId() == R.id.yes_deliver && mEdtDeliveryCost.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, getResources().getString(R.string.pleaseentercostofdelivery), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mRadioGroupCollect.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getResources().getString(R.string.selectcollectinfo), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mLytCollect.isShown() && mTxtAvailablityDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, getResources().getString(R.string.enteravailabilitydate), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mLinearIngredientSection.isShown() && mEdtReceipeDesc.getText().toString().equalsIgnoreCase("")) {
            mEdtReceipeDesc.requestFocus();
            mEdtReceipeDesc.setError(getResources().getString(R.string.fillreceipysteps));
            return false;
        }

        else if (mLinearIngredientSection.isShown() && mTv_ProductionDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, getResources().getString(R.string.entermanufacringdte), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mLinearIngredientSection.isShown() && mTv_BestBefore.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, getResources().getString(R.string.addbestbeforedate), Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    private String mStrInv_Stock, mStrInv_Price, mStrInv_Unit, mStrInv_Weight;
    private String mStrIngredientName, mStrIngredientAmont, mSrtIngredientUnit;

    // Convert All Views JSon Data
    private void CreateInvetoryJson() throws JSONException {
        //for Inv_Stock
        JSONArray jsonArrayStock = new JSONArray();
        JSONObject mJSonStock = new JSONObject();
        for (int i = 0; i < mArrayViewDataList.size(); i++) {
            if (!mArrayViewDataList.get(i).getmTxtUpdate().isShown()) {
                jsonArrayStock.put(mArrayViewDataList.get(i).getmEdtNumberAvailable().getText().toString());
            }
        }
        mJSonStock.put("inv_stock", jsonArrayStock);
        mStrInv_Stock = mJSonStock.toString();
        Log.d("Final_StockJson", mJSonStock.toString());


        //for inv_price
        JSONArray jsonArrayPrice = new JSONArray();
        JSONObject mJSonPrice = new JSONObject();
        for (int i = 0; i < mArrayViewDataList.size(); i++) {
            if (!mArrayViewDataList.get(i).getmTxtUpdate().isShown()) {
                jsonArrayPrice.put(mArrayViewDataList.get(i).getmEdtPrice().getText().toString());
            }
        }
        mJSonPrice.put("inv_price", jsonArrayPrice);
        mStrInv_Price = mJSonPrice.toString();
        Log.d("inv_price", mJSonPrice.toString());


        //for Inv_Unit
        JSONArray jsonArrayUnit = new JSONArray();
        JSONObject mJSonUnit = new JSONObject();
        for (int i = 0; i < mArrayViewDataList.size(); i++) {
            if (!mArrayViewDataList.get(i).getmTxtUpdate().isShown()) {
                String str=mStrUnitArr[mArrayViewDataList.get(i).getmSpn_UnitMeasure().getSelectedItemPosition()].toString();
                String newstr=str.replace("(s)","");
                jsonArrayUnit.put(newstr);

            }
        }
        mJSonUnit.put("inv_unit", jsonArrayUnit);
        Log.d("inv_unit", mJSonUnit.toString());
        mStrInv_Unit = mJSonUnit.toString();

        Log.d("gfgfg",mStrInv_Unit);



        //for Inv_Weight
        JSONArray jsonArrayWeight = new JSONArray();
        JSONObject mJSonWeight = new JSONObject();
        for (int i = 0; i < mArrayViewDataList.size(); i++) {
            JSONObject mJsonObj = new JSONObject();
            if (!mArrayViewDataList.get(i).getmTxtUpdate().isShown()) {
                jsonArrayWeight.put(mArrayViewDataList.get(i).getmEdtWightValume().getText().toString());
            }
        }
        mJSonWeight.put("inv_weight", jsonArrayWeight);
        Log.d("inv_weight", mJSonWeight.toString());
        mStrInv_Weight = mJSonWeight.toString();


        if (mLinearIngredientSection.isShown()) {
            //for incrediant_name
            JSONArray jsonArrayIngredient = new JSONArray();
            JSONObject mJSonIngredient = new JSONObject();
            for (int i = 0; i < mArrayViewIngredientDataList.size(); i++) {
                if (!mArrayViewDataList.get(i).getmTxtUpdate().isShown()) {
                    jsonArrayIngredient.put(mArrayViewIngredientDataList.get(i).getmEdtIngredientName().getText().toString());
                }
            }
            mJSonIngredient.put("incrediant_name", jsonArrayIngredient);
            Log.d("incrediant_name", mJSonIngredient.toString());
            mStrIngredientName = mJSonIngredient.toString();



            //for Inv_Unit
            JSONArray jsonArrayUnitin = new JSONArray();
            JSONObject mJSonUnitn = new JSONObject();
            for (int i = 0; i < mArrayViewIngredientDataList.size(); i++) {
                if (!mArrayViewIngredientDataList.get(i).getmTxtUpdate().isShown()) {
                    String strn=mStrUnitArr[mArrayViewIngredientDataList.get(i).getmSp_UnitIngredient().getSelectedItemPosition()].toString();
                    String newstrn=strn.replace("(s)","");

                    jsonArrayUnitin.put(newstrn);
                }
            }
            mJSonUnitn.put("unit", jsonArrayUnitin);
            Log.d("unit", mJSonUnitn.toString());
            mSrtIngredientUnit = mJSonUnitn.toString();


            //for Inv_Weight
            JSONArray jsonArrayWeightIn = new JSONArray();
            JSONObject mJSonWeightIn = new JSONObject();
            for (int i = 0; i < mArrayViewIngredientDataList.size(); i++) {
                JSONObject mJsonObj = new JSONObject();
                if (!mArrayViewDataList.get(i).getmTxtUpdate().isShown()) {
                    jsonArrayWeightIn.put(mArrayViewIngredientDataList.get(i).getmEdtIngredientWeight().getText().toString());
                }
            }
            mJSonWeightIn.put("weight", jsonArrayWeightIn);
            Log.d("weight", mJSonWeightIn.toString());
            mStrIngredientAmont = mJSonWeightIn.toString();

        }
    }

    //get Sub Categary
    private void wsCallSubCategory(String getmStrCatId) {
        StringRequestSubCategory = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_picorear_subcategory"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mSubCategoryList.clear();
                    CategoryHelperClass categoryHelperClass = new CategoryHelperClass();
                    categoryHelperClass.setmStrCatId("");
                    categoryHelperClass.setmStrReceipyId("");
                    categoryHelperClass.setmStrCatName(getResources().getString(R.string.select));
                    mSubCategoryList.add(categoryHelperClass);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray mJsonCat = jsonObject.getJSONArray("subcatlist");
                    for (int i = 0; i < mJsonCat.length(); i++) {
                        JSONObject jsonObject1 = mJsonCat.getJSONObject(i);
                        CategoryHelperClass categoryHelperClass1 = new CategoryHelperClass();
                        categoryHelperClass1.setmStrCatId(jsonObject1.getString("subcat_id"));
                        categoryHelperClass1.setmStrCatName(jsonObject1.getString("subcat_name"));
                        //       categoryHelperClass1.setmStrReceipyId(jsonObject1.getString("recipy_status"));
                        mSubCategoryList.add(categoryHelperClass1);
                    }
                    setSubCatAdapter();

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
                param.put("user_id", PrefManager.getUserId(AddOfferActivity.this));
                param.put("cat_id", getmStrCatId);


                return param;
            }

        };


        RequestQueueSubCategory.add(StringRequestSubCategory);
    }

    //call categary list from webservices
    private void wsCallCategoryList() {
        StringRequestCategory = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_picorear_category"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.d("dasdad",response);
                    mCategoryArrList.clear();
                    CategoryHelperClass categoryHelperClass = new CategoryHelperClass();
                    categoryHelperClass.setmStrCatId("");
                    categoryHelperClass.setmStrReceipyId("");
                    categoryHelperClass.setmStrCatName(getResources().getString(R.string.select));
                    mCategoryArrList.add(categoryHelperClass);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray mJsonCat = jsonObject.getJSONArray("catlist");
                    for (int i = 0; i < mJsonCat.length(); i++) {
                        JSONObject jsonObject1 = mJsonCat.getJSONObject(i);
                        CategoryHelperClass categoryHelperClass1 = new CategoryHelperClass();
                        categoryHelperClass1.setmStrCatId(jsonObject1.getString("cat_id"));
                        categoryHelperClass1.setmStrCatName(jsonObject1.getString("cat_name"));
                        categoryHelperClass1.setmStrReceipyId(jsonObject1.getString("recipy_status"));
                        mCategoryArrList.add(categoryHelperClass1);
                    }

                    setCategoryAdapter();
                    if (getIntent().getStringExtra("From").equalsIgnoreCase("Update")) {
                        wsGetOfferData(getIntent().getStringExtra("Product_id"));
                    }

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
                param.put("user_id", PrefManager.getUserId(AddOfferActivity.this));
                return param;
            }

        };
        RequestQueueCategory.add(StringRequestCategory);

    }

    private ArrayList<ProductHelperClass> mArrProductInformation = new ArrayList<>();

    //Get Details OF Perticular Offer.
    private void wsGetOfferData(String product_id) {
        mArrProductInformation.clear();
        StringRequestGetOffer = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_details"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.d("get_product_details",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject mJsonObjectDetails = jsonObject.getJSONObject("product_details");
                    ProductHelperClass productHelperClass = new ProductHelperClass();
                    productHelperClass.setmStrProductId(mJsonObjectDetails.getString("product_id"));
                    productHelperClass.setmStrProductName(mJsonObjectDetails.getString("product_name"));
                    productHelperClass.setmStrProductImage(mJsonObjectDetails.getString("product_image"));
                    productHelperClass.setmStrCatId(mJsonObjectDetails.getString("cat_id"));
                    productHelperClass.setmStrSubCat(mJsonObjectDetails.getString("subcat_id"));
                    productHelperClass.setmStrProductDesc(mJsonObjectDetails.getString("description"));
                    productHelperClass.setmStrDeliveryFees(mJsonObjectDetails.getString("delivery_fees"));
                    productHelperClass.setmStrAvailOfferDate(mJsonObjectDetails.getString("offer_avail_date"));
                    productHelperClass.setmStrReceipyDesc(mJsonObjectDetails.getString("recipy_desc"));
                    productHelperClass.setmStrAntiGaspiTips(mJsonObjectDetails.getString("tips"));
                    productHelperClass.setmStrProductionDate(mJsonObjectDetails.getString("man_date"));
                    productHelperClass.setmStrBestBeforeDate(mJsonObjectDetails.getString("best_before"));
                    productHelperClass.setmStrReceipyStatus(mJsonObjectDetails.getString("recipe_status"));
                    productHelperClass.setmStrCollect(mJsonObjectDetails.getString("collect"));
                    productHelperClass.setmStrDeliver(mJsonObjectDetails.getString("delivery"));
                    productHelperClass.setStr_offer_type(mJsonObjectDetails.getString("offer_type"));
                    productHelperClass.setStr_variety(mJsonObjectDetails.getString("variety"));
                    productHelperClass.setMavailabletype(mJsonObjectDetails.getString("available_type"));
                    productHelperClass.setMavailabletime(mJsonObjectDetails.getString("offer_available_time"));
                    mArrProductInformation.add(productHelperClass);

                    //Product Stock Data
                    JSONArray mJsonArrProductionStock = jsonObject.getJSONArray("product_stock");
                    for (int i = 0; i < mJsonArrProductionStock.length(); i++) {
                        JSONObject mJsonObjectStock = mJsonArrProductionStock.getJSONObject(i);
                        ProductHelperClass productHelperClass1 = new ProductHelperClass();
                        productHelperClass1.setmStrStockID(mJsonObjectStock.getString("stock_id"));
                        productHelperClass1.setmStrStock(mJsonObjectStock.getString("stock"));
                        productHelperClass1.setmStrUnit(mJsonObjectStock.getString("unit"));
                        productHelperClass1.setmStrPrice(mJsonObjectStock.getString("price"));
                        productHelperClass1.setmStrWeight(mJsonObjectStock.getString("weight"));
                        mArrProductInformation.get(0).getmArrProductStockList().add(productHelperClass1);
                    }

                    //Product Ingredient Data
                    JSONArray mJsonArrProductionIngredient = jsonObject.getJSONArray("product_inc");
                    for (int i = 0; i < mJsonArrProductionIngredient.length(); i++) {
                        JSONObject mJsonObjectStock = mJsonArrProductionIngredient.getJSONObject(i);
                        ProductHelperClass productHelperClass2 = new ProductHelperClass();
                        productHelperClass2.setmStrIngredientID(mJsonObjectStock.getString("incrediant_id"));
                        productHelperClass2.setmStrIngredietName(mJsonObjectStock.getString("incrediant_name"));
                        productHelperClass2.setmStrIngredientWeight(mJsonObjectStock.getString("weight"));
                        productHelperClass2.setmStrIngredientUnit(mJsonObjectStock.getString("unit"));
                        mArrProductInformation.get(0).getmArrProductIngredient().add(productHelperClass2);
                    }

                    //Product Receipy  Images Data
                    JSONArray mJsonArrProductionIngredientImages = jsonObject.getJSONArray("product_recimages");
                    for (int i = 0; i < mJsonArrProductionIngredientImages.length(); i++) {
                        JSONObject mJsonObjectStock = mJsonArrProductionIngredientImages.getJSONObject(i);
                        ProductHelperClass productHelperClass2 = new ProductHelperClass();
                        productHelperClass2.setmStrImageID(mJsonObjectStock.getString("recipy_image_id"));
                        productHelperClass2.setmStrReceipeImg(mJsonObjectStock.getString("image_url"));
                        mArrProductInformation.get(0).getmArrProductIngredientImages().add(productHelperClass2);
                    }

                    //Product Product Images Data
                    JSONArray mJsonArrProductionImages = jsonObject.getJSONArray("product_images");
                    for (int i = 0; i < mJsonArrProductionImages.length(); i++) {
                        JSONObject mJsonObjectStock = mJsonArrProductionImages.getJSONObject(i);
                        ProductHelperClass productHelperClass2 = new ProductHelperClass();
                        productHelperClass2.setmStrImageID(mJsonObjectStock.getString("image_id"));
                        productHelperClass2.setmStrReceipeImg(mJsonObjectStock.getString("image_url"));
                        mArrProductInformation.get(0).getmArrProductStockImg().add(productHelperClass2);
                    }


                    setPreviouData();


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
                param.put("product_id", product_id);
                return param;
            }

        };
        RequestQueueGetPicoriar.add(StringRequestGetOffer);

    }

    //set Data From Previously Added Product  ...UPDATE SECTION
    private void setPreviouData() {

        mEdtProductName.setText(mArrProductInformation.get(0).getmStrProductName());
        mEdtProductName.setSelection(mEdtProductName.getText().length());
        EdtVariety.setText(mArrProductInformation.get(0).getStr_variety());
        if(mArrProductInformation.get(0).getMavailabletime().equals("7h-9h"))
        {
            time_spinner.setSelection(0);

        }else if(mArrProductInformation.get(0).getMavailabletime().equals("9h-12h"))
        {
            time_spinner.setSelection(1);

        }else if(mArrProductInformation.get(0).getMavailabletime().equals("12h-14h"))
        {
            time_spinner.setSelection(2);
        }else if(mArrProductInformation.get(0).getMavailabletime().equals("14h-17h"))
        {
            time_spinner.setSelection(3);

        }
        else if(mArrProductInformation.get(0).getMavailabletime().equals("17h-19h"))
        {
            time_spinner.setSelection(4);

        }
        else if(mArrProductInformation.get(0).getMavailabletime().equals("19h-21h"))
        {
            time_spinner.setSelection(5);

        }
        else if(mArrProductInformation.get(0).getMavailabletime().equals("Toute la journée"))
        {
            time_spinner.setSelection(6);

        }
//        if (mArrProductInformation.get(0).getMavailabletime()!= null) {
//            int p =Integer.parseInt( mArrProductInformation.get(0).getMavailabletime() )- 1;
//            time_spinner.setSelection(p);
//        }else{
//            time_spinner.setSelection(0);
//        }

            if (mArrProductInformation.get(0).getStr_offer_type() != null) {
 int p =Integer.parseInt( mArrProductInformation.get(0).getStr_offer_type() )- 1;
                offertypespn.setSelection(p);
         }else{
                offertypespn.setSelection(0);
            }

        if (mArrProductInformation.get(0).getmArrProductStockImg().size() > 0) {
            mPicorialArrListImg.clear();
            for (int i = 0; i < mArrProductInformation.get(0).getmArrProductStockImg().size(); i++) {
                Bitmap mBtp = null;
                PicoriarHelperClass picoriarHelperClass = new PicoriarHelperClass();
                picoriarHelperClass.setmImgId(mArrProductInformation.get(0).getmArrProductStockImg().get(i).getmStrImageID());
                picoriarHelperClass.setmBitmap(mBtp);
                picoriarHelperClass.setmImvByte(BaseUrl.PRODUCTURL.concat( mArrProductInformation.get(0).getmArrProductStockImg().get(i).getmStrReceipeImg()));
                picoriarHelperClass.setmImgFrom("Api");
                mPicorialArrListImg.add(picoriarHelperClass);

            }
            setImgAdapter();
        }


        //set Previous Cat and SubCategoary
        for (int i = 0; i < mCategoryArrList.size(); i++) {
            if (mCategoryArrList.get(i).getmStrCatId().equalsIgnoreCase(mArrProductInformation.get(0).getmStrCatId())) {
                mSpnCat.setSelection(i);
                if (mArrProductInformation.get(0).getmStrReceipyStatus().equalsIgnoreCase("1")) {
                    mLinearIngredientSection.setVisibility(View.VISIBLE);
                } else {
                    mLinearIngredientSection.setVisibility(View.GONE);
                }
                wsCallSubCategory(mCategoryArrList.get(i).getmStrCatId());
            }
        }

        mEdtDescription.setText(mArrProductInformation.get(0).getmStrProductDesc());

        if (mArrProductInformation.get(0).getmArrProductStockList().size() > 0) {

            CreateDynamicViewForUpdate(mArrProductInformation.get(0).getmArrProductStockList());
        //   createDefaultLyt(0);
           // createDefaultIngredientLyt(1);
        } else {
            createDefaultLyt(0);
           // createDefaultIngredientLyt(0);
        }

        if (mArrProductInformation.get(0).getmArrProductIngredient().size() > 0) {
            createDefaultIngredientLytForUpdate(mArrProductInformation.get(0).getmArrProductIngredient());
           // createDefaultLyt(1);
            createDefaultIngredientLyt(1);
        } else {
           //   createDefaultLyt(0);
            createDefaultIngredientLyt(0);
        }



        if (mArrProductInformation.get(0).getmStrDeliver().equalsIgnoreCase("1")) {
            mRadioButtonYesDeliver.setChecked(true);
            mRadioButtonNoDeliver.setChecked(false);
            mLytDeliver.setVisibility(View.VISIBLE);
            mEdtDeliveryCost.setText(mArrProductInformation.get(0).getmStrDeliveryFees());
        } else {
            mLytDeliver.setVisibility(View.GONE);
            mRadioButtonNoDeliver.setChecked(true);
            mRadioButtonYesDeliver.setChecked(false);
        }

        if (mArrProductInformation.get(0).getMavailabletype().equalsIgnoreCase("3")) {  //mArrProductInformation.get(0).getmStrCollect().equalsIgnoreCase("3")
            mRadioBtnYesCollect.setChecked(true);
            mRadioBtnNoCollect.setChecked(false);
            mLytCollect.setVisibility(View.VISIBLE);
            mTxtAvailablityDate.setText(mArrProductInformation.get(0).getmStrAvailOfferDate());
        }
        if(mArrProductInformation.get(0).getMavailabletype().equalsIgnoreCase("2")){
            mLytCollect.setVisibility(View.GONE);
            mRadioBtnNoCollect.setChecked(false);
            mRadioBtnYesCollect.setChecked(true);
            every_weekend_radioButton.setChecked(true);
            mLytbelowcomelater.setVisibility(View.VISIBLE);
        }
        if(mArrProductInformation.get(0).getMavailabletype().equalsIgnoreCase("1")){
            mLytCollect.setVisibility(View.GONE);
            mRadioBtnNoCollect.setChecked(false);
            mRadioBtnYesCollect.setChecked(true);
            mLytbelowcomelater.setVisibility(View.VISIBLE);
            everyday_radioButton.setChecked(true);
        }


        Log.d("checkvale",mArrProductInformation.get(0).getmStrCollect());
        if(mArrProductInformation.get(0).getmStrCollect().equalsIgnoreCase("0")){

            mLytCollect.setVisibility(View.GONE);
            mRadioBtnNoCollect.setChecked(true);
            mRadioBtnYesCollect.setChecked(false);
            mLytbelowcomelater.setVisibility(View.GONE);

        }

        if (mLinearIngredientSection.isShown()) {
            mEdtReceipeDesc.setText(mArrProductInformation.get(0).getmStrReceipyDesc());
            mEdtAntigaspiTips.setText(mArrProductInformation.get(0).getmStrAntiGaspiTips());
            mTv_ProductionDate.setText(mArrProductInformation.get(0).getmStrProductionDate());
            mTv_BestBefore.setText(mArrProductInformation.get(0).getmStrBestBeforeDate());
            if (mArrProductInformation.get(0).getmArrProductIngredientImages().size() > 0) {
                mPicorialArrListImgIngredient.clear();
                for (int i = 0; i < mArrProductInformation.get(0).getmArrProductIngredientImages().size(); i++) {
                    Bitmap mBtp = null;
                    PicoriarHelperClass picoriarHelperClass = new PicoriarHelperClass();
                    picoriarHelperClass.setmImgId(mArrProductInformation.get(0).getmArrProductIngredientImages().get(i).getmStrImageID());
                    picoriarHelperClass.setmBitmap(mBtp);
                    picoriarHelperClass.setmImvByte(BaseUrl.RECEPYPICURL.concat(mArrProductInformation.get(0).getmArrProductIngredientImages().get(i).getmStrReceipeImg()));
                    picoriarHelperClass.setmImgFrom("Api");
                    mPicorialArrListImgIngredient.add(picoriarHelperClass);

                }
                setImgIngredientAdapter();
            }


        }


    }

    private void setCategoryAdapter() {
        AdapterSpinnerCatPicoriar adapterSpinnerCat = new AdapterSpinnerCatPicoriar(mCategoryArrList, AddOfferActivity.this);
        mSpnCat.setAdapter(adapterSpinnerCat);
    }

    private void setSubCatAdapter() {
        AdapterSpinnerCatPicoriar adapterSpinnerCat = new AdapterSpinnerCatPicoriar(mSubCategoryList, AddOfferActivity.this);
        mSpnSubCat.setAdapter(adapterSpinnerCat);

        if (getIntent().getStringExtra("From").equalsIgnoreCase("Update")) {
            for (int i = 0; i < mSubCategoryList.size(); i++) {
                if (mSubCategoryList.get(i).getmStrCatId().equalsIgnoreCase(mArrProductInformation.get(0).getmStrSubCat())) {
                    mSpnSubCat.setSelection(i);
                }
            }
        }


    }

    //apply click listester on views
    private void setOnClickListener() {
        mBtnAddLayout.setOnClickListener(this);
        mTxtSelectImg.setOnClickListener(this);
        mBtnAddingredient.setOnClickListener(this);
        mTxtSelectImgIngredient.setOnClickListener(this);
        mImv_ProductionDate.setOnClickListener(this);
        imImv_BestBefore.setOnClickListener(this);
        mTxtSelectAvailabiltyDate.setOnClickListener(this);
        mBtnOfferValidate.setOnClickListener(this);
    }

    //dynamic lyt for Add Product
    private void CreateDynamicViewForUpdate(ArrayList<ProductHelperClass> mArrProductStockList) {
        Log.d("CreateDynamic","CreateDynamicViewForUpdate");
        mStrUnitArr = getResources().getStringArray(R.array.unitofmeasures);
        mLinearMainLyt.removeAllViews();
        mArrayViewDataList.clear();
        for (int i = 0; i < mArrProductStockList.size(); i++) {
            mDyanamicClass = new DynamicClass();
            View mViewSingleRowReturn = getLayoutInflater().inflate(R.layout.single_dynamic_lyt, null);
            mDyanamicClass.setmSpn_UnitMeasure((Spinner) mViewSingleRowReturn.findViewById(R.id.unit_spinner));
            mDyanamicClass.setmEdtWightValume((EditText) mViewSingleRowReturn.findViewById(R.id.edt_weightvalume));
            mDyanamicClass.setmEdtPrice((EditText) mViewSingleRowReturn.findViewById(R.id.edt_price));
            mDyanamicClass.setmEdtNumberAvailable((EditText) mViewSingleRowReturn.findViewById(R.id.edt_avaible));
            mDyanamicClass.setmImv_deleteView((ImageView) mViewSingleRowReturn.findViewById(R.id.imv_delete));
            mDyanamicClass.setmTxtUpdate((TextView) mViewSingleRowReturn.findViewById(R.id.tv_update));
            mDyanamicClass.getmImv_deleteView().setVisibility(View.GONE);
            mDyanamicClass.getmTxtUpdate().setVisibility(View.VISIBLE);

            //Unit  spinner Adapter
            mDyanamicClass.getmSpn_UnitMeasure().setAdapter(new SpineerUnitAdapter(AddOfferActivity.this, mStrUnitArr));

            for (int j = 0; j < mStrUnitArr.length; j++) {
                Log.d("getunit", mArrProductStockList.get(i).getmStrUnit());
                String newdata=mStrUnitArr[j].replace("(s)","");
                String relnewdata=newdata.replaceAll("Ã©","e");
                if (mArrProductStockList.get(i).getmStrUnit().equalsIgnoreCase(relnewdata)) {
                    mDyanamicClass.getmSpn_UnitMeasure().setSelection(j);
                }
            }

            mDyanamicClass.getmEdtWightValume().setText(mArrProductStockList.get(i).getmStrWeight());//
            mDyanamicClass.getmEdtPrice().setText(mArrProductStockList.get(i).getmStrPrice());
            mDyanamicClass.getmEdtNumberAvailable().setText(mArrProductStockList.get(i).getmStrStock());
            Log.d("getweight",mArrProductStockList.get(i).getmStrWeight());
            Log.d("getprice",mArrProductStockList.get(i).getmStrPrice());
            Log.d("getstock",mArrProductStockList.get(i).getmStrStock());
            mDyanamicClass.setmStrID(mArrProductStockList.get(i).getmStrStockID());
            mDyanamicClass.getmTxtUpdate().setTag(i);
            mDyanamicClass.getmTxtUpdate().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    wsUpdateStockAction(mArrayViewDataList.get(pos));
                }
            });

            mDyanamicClass.getmSpn_UnitMeasure().setTag(i);

            mDyanamicClass.getmImv_deleteView().setTag(i);
            mDyanamicClass.getmImv_deleteView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (pos != 0) {
                        mLinearMainLyt.removeViewAt(pos);
                        mArrayViewDataList.remove(pos);
                        reindexing();
                    }
                }
            });

            mDyanamicClass.getmSpn_UnitMeasure().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mArrayViewDataList.add(mDyanamicClass);
            mLinearMainLyt.addView(mViewSingleRowReturn);
        }


    }


    private DynamicClass mDyanamicClass;
    private String[] mStrUnitArr;

    //dynamic lyt for Add Product
    private void createDefaultLyt(int i) {

        mStrUnitArr = getResources().getStringArray(R.array.unitofmeasures);
        if (i == 0) {
            Log.d("createDefaultLytif","createDefaultLyt");
            mLinearMainLyt.removeAllViews();
            mArrayViewDataList.clear();
            mDyanamicClass = new DynamicClass();
            View mViewSingleRowReturn = getLayoutInflater().inflate(R.layout.single_dynamic_lyt, null);
            mDyanamicClass.setmSpn_UnitMeasure((Spinner) mViewSingleRowReturn.findViewById(R.id.unit_spinner));
            mDyanamicClass.setmEdtWightValume((EditText) mViewSingleRowReturn.findViewById(R.id.edt_weightvalume));
            mDyanamicClass.setmEdtPrice((EditText) mViewSingleRowReturn.findViewById(R.id.edt_price));
            mDyanamicClass.setmEdtNumberAvailable((EditText) mViewSingleRowReturn.findViewById(R.id.edt_avaible));
            mDyanamicClass.setmImv_deleteView((ImageView) mViewSingleRowReturn.findViewById(R.id.imv_delete));
            mDyanamicClass.setmTxtUpdate((TextView) mViewSingleRowReturn.findViewById(R.id.tv_update));
            mDyanamicClass.getmTxtUpdate().setVisibility(View.GONE);

            //Unit  spinner Adapter
            mDyanamicClass.getmSpn_UnitMeasure().setAdapter(new SpineerUnitAdapter(AddOfferActivity.this, mStrUnitArr));
            mDyanamicClass.getmSpn_UnitMeasure().setTag(mArrayViewDataList.size());

            mDyanamicClass.getmImv_deleteView().setTag(mArrayViewDataList.size());
            mDyanamicClass.getmImv_deleteView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (pos != 0) {
                        mLinearMainLyt.removeViewAt(pos);
                        mArrayViewDataList.remove(pos);
                        reindexing();
                    }
                }
            });

            mDyanamicClass.getmSpn_UnitMeasure().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mArrayViewDataList.add(mDyanamicClass);
            mLinearMainLyt.addView(mViewSingleRowReturn);
        }
        else {
            Log.d("createDefaultLytelse","createDefaultLyt");
            mDyanamicClass = new DynamicClass();
            View mViewSingleRowReturn = getLayoutInflater().inflate(R.layout.single_dynamic_lyt, null);
            mDyanamicClass.setmSpn_UnitMeasure((Spinner) mViewSingleRowReturn.findViewById(R.id.unit_spinner));
            mDyanamicClass.setmEdtWightValume((EditText) mViewSingleRowReturn.findViewById(R.id.edt_weightvalume));
            mDyanamicClass.setmEdtPrice((EditText) mViewSingleRowReturn.findViewById(R.id.edt_price));
            mDyanamicClass.setmEdtNumberAvailable((EditText) mViewSingleRowReturn.findViewById(R.id.edt_avaible));
            mDyanamicClass.setmImv_deleteView((ImageView) mViewSingleRowReturn.findViewById(R.id.imv_delete));
            mDyanamicClass.setmTxtUpdate((TextView) mViewSingleRowReturn.findViewById(R.id.tv_update));
            mDyanamicClass.getmTxtUpdate().setVisibility(View.GONE);
            mDyanamicClass.getmImv_deleteView().setTag(mArrayViewDataList.size());
            mDyanamicClass.getmImv_deleteView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (pos != 0) {
                        mLinearMainLyt.removeViewAt(pos);
                        mArrayViewDataList.remove(pos);
                        reindexing();
                    }
                }
            });
            //Unit  spinner Adapter
            mDyanamicClass.getmSpn_UnitMeasure().setAdapter(new SpineerUnitAdapter(AddOfferActivity.this, mStrUnitArr));
            mDyanamicClass.getmSpn_UnitMeasure().setTag(mArrayViewDataList.size());
            mDyanamicClass.getmSpn_UnitMeasure().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mArrayViewDataList.add(mDyanamicClass);
            mLinearMainLyt.addView(mViewSingleRowReturn);
        }


    }

    //reindexing array to reposition all indexes .
    void reindexingingredient() {
        for (int i = 0; i < mArrayViewIngredientDataList.size(); i++) {
            mArrayViewIngredientDataList.get(i).getmImv_deleteView().setTag(i);
            mArrayViewIngredientDataList.get(i).getmSp_UnitIngredient().setTag(i);
        }
    }


    private void createDefaultIngredientLytForUpdate(ArrayList<ProductHelperClass> mArrIngredientLst) {
        mStrUnitArrForIngredient = getResources().getStringArray(R.array.unitofmeasures);

        mLinearIngredient.removeAllViews();
        mArrayViewIngredientDataList.clear();

        for (int i = 0; i < mArrIngredientLst.size(); i++) {
            mDynamicIngredient = new DynamicClass();
            Log.d("single_dynamic_lyt","single_dynamic_lytingredient");
            View mViewSingleRowReturn = getLayoutInflater().inflate(R.layout.single_dynamic_lytingredient, null);
            mDynamicIngredient.setmSp_UnitIngredient((Spinner) mViewSingleRowReturn.findViewById(R.id.unit_spinner));
            mDynamicIngredient.setmEdtIngredientWeight((EditText) mViewSingleRowReturn.findViewById(R.id.edt_amount));
            mDynamicIngredient.setmEdtIngredientName((EditText) mViewSingleRowReturn.findViewById(R.id.edt_ingredientNae));
            mDynamicIngredient.setmImv_deleteView((ImageView) mViewSingleRowReturn.findViewById(R.id.imv_delete));
            mDynamicIngredient.setmTxtUpdate((TextView) mViewSingleRowReturn.findViewById(R.id.tv_update));
            mDynamicIngredient.getmTxtUpdate().setVisibility(View.VISIBLE);
            mDynamicIngredient.getmImv_deleteView().setVisibility(View.GONE);
            //Unit  spinner Adapter
            mDynamicIngredient.getmSp_UnitIngredient().setAdapter(new SpineerUnitAdapter(AddOfferActivity.this, mStrUnitArrForIngredient));
            mDynamicIngredient.getmSp_UnitIngredient().setTag(i);

            for (int j = 0; j < mStrUnitArrForIngredient.length; j++) {
                if (mArrIngredientLst.get(i).getmStrIngredientUnit().equalsIgnoreCase(mStrUnitArrForIngredient[j].toString())) {
                    mDynamicIngredient.getmSp_UnitIngredient().setSelection(j);
                }
            }

            mDynamicIngredient.getmEdtIngredientName().setText(mArrIngredientLst.get(i).getmStrIngredietName());
            mDynamicIngredient.getmEdtIngredientWeight().setText(mArrIngredientLst.get(i).getmStrIngredientWeight());
            mDynamicIngredient.setmStrID(mArrIngredientLst.get(i).getmStrIngredientID());
            mDynamicIngredient.getmTxtUpdate().setTag(i);
            mDynamicIngredient.getmTxtUpdate().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    wsUpdateIngredientAction(mArrayViewIngredientDataList.get(pos));
                }
            });


            mDynamicIngredient.getmImv_deleteView().setTag(i);
            mDynamicIngredient.getmImv_deleteView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (pos != 0) {
                        mLinearIngredient.removeViewAt(pos);
                        mArrayViewIngredientDataList.remove(pos);
                        reindexingingredient();
                    }
                }
            });

            mDynamicIngredient.getmSp_UnitIngredient().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mArrayViewIngredientDataList.add(mDynamicIngredient);
            mLinearIngredient.addView(mViewSingleRowReturn);
        }


    }


    /**
     * Ingredient Dynamic Lyt Code
     **/
    private DynamicClass mDynamicIngredient;
    private String[] mStrUnitArrForIngredient;

    private void createDefaultIngredientLyt(int i) {
        mStrUnitArrForIngredient = getResources().getStringArray(R.array.unitofmeasures);
        if (i == 0) {
            Log.d("createDefaultIngreif","createDefaultIngredientLytif");
            mLinearIngredient.removeAllViews();
            mArrayViewIngredientDataList.clear();
            mDynamicIngredient = new DynamicClass();
            View mViewSingleRowReturn = getLayoutInflater().inflate(R.layout.single_dynamic_lytingredient, null);
            mDynamicIngredient.setmSp_UnitIngredient((Spinner) mViewSingleRowReturn.findViewById(R.id.unit_spinner));
            mDynamicIngredient.setmEdtIngredientWeight((EditText) mViewSingleRowReturn.findViewById(R.id.edt_amount));
            mDynamicIngredient.setmEdtIngredientName((EditText) mViewSingleRowReturn.findViewById(R.id.edt_ingredientNae));
            mDynamicIngredient.setmImv_deleteView((ImageView) mViewSingleRowReturn.findViewById(R.id.imv_delete));
            mDynamicIngredient.setmTxtUpdate((TextView) mViewSingleRowReturn.findViewById(R.id.tv_update));
            mDynamicIngredient.getmTxtUpdate().setVisibility(View.GONE);
            //Unit  spinner Adapter
            mDynamicIngredient.getmSp_UnitIngredient().setAdapter(new SpineerUnitAdapter(AddOfferActivity.this, mStrUnitArrForIngredient));
            mDynamicIngredient.getmSp_UnitIngredient().setTag(mArrayViewIngredientDataList.size());

            mDynamicIngredient.getmImv_deleteView().setTag(mArrayViewIngredientDataList.size());
            mDynamicIngredient.getmImv_deleteView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (pos != 0) {
                        mLinearIngredient.removeViewAt(pos);
                        mArrayViewIngredientDataList.remove(pos);
                        reindexingingredient();
                    }
                }
            });

            mDynamicIngredient.getmSp_UnitIngredient().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mArrayViewIngredientDataList.add(mDynamicIngredient);
            mLinearIngredient.addView(mViewSingleRowReturn);
        } else {
            mDynamicIngredient = new DynamicClass();
            Log.d("createDefaultIngreelse","createDefaultIngredientLytelse");
            View mViewSingleRowReturn = getLayoutInflater().inflate(R.layout.single_dynamic_lytingredient, null);
            mDynamicIngredient.setmSp_UnitIngredient((Spinner) mViewSingleRowReturn.findViewById(R.id.unit_spinner));
            mDynamicIngredient.setmEdtIngredientWeight((EditText) mViewSingleRowReturn.findViewById(R.id.edt_amount));
            mDynamicIngredient.setmEdtIngredientName((EditText) mViewSingleRowReturn.findViewById(R.id.edt_ingredientNae));
            mDynamicIngredient.setmImv_deleteView((ImageView) mViewSingleRowReturn.findViewById(R.id.imv_delete));

            mDynamicIngredient.getmImv_deleteView().setTag(mArrayViewIngredientDataList.size());
            mDynamicIngredient.getmImv_deleteView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (pos != 0) {
                        mLinearIngredient.removeViewAt(pos);
                        mArrayViewIngredientDataList.remove(pos);
                        reindexingingredient();
                    }
                }
            });
            //Unit  spinner Adapter
            mDynamicIngredient.getmSp_UnitIngredient().setAdapter(new SpineerUnitAdapter(AddOfferActivity.this, mStrUnitArrForIngredient));
            mDynamicIngredient.getmSp_UnitIngredient().setTag(mArrayViewIngredientDataList.size());
            mDynamicIngredient.getmSp_UnitIngredient().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mArrayViewIngredientDataList.add(mDynamicIngredient);
            mLinearIngredient.addView(mViewSingleRowReturn);
        }


    }

    //reindexing array to reposition all indexes .
    void reindexing() {
        for (int i = 0; i < mArrayViewDataList.size(); i++) {
            mArrayViewDataList.get(i).getmImv_deleteView().setTag(i);
            mArrayViewDataList.get(i).getmSpn_UnitMeasure().setTag(i);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                createDefaultLyt(1);
                break;
            case R.id.selectImg:
                selectImage();
                break;

            case R.id.add_ingredent:
                createDefaultIngredientLyt(1);
                break;

            case R.id.selectimgingredient:
                selectImageIngredient();
                break;

            case R.id.imv_selectdatebefore:
                showCalenderDialogueForBeforeDate();

                break;
            case R.id.imv_selectproductiondate:
                showCalenderDialogueForProductionDate();

                break;
            case R.id.imv_selectavailabledate:
                showCalenderDialogueForAvailability();

                break;

            case R.id.offer_validate_button:
                //convertToJsonData();
                try {
                    CreateInvetoryJson();

                } catch (JSONException e) {

                }
                if (ValidationCheck()) {
                    if (getIntent().getStringExtra("From").equalsIgnoreCase("Update")) {
                        wsCallUpdatePicoriar(mArrProductInformation.get(0).getmStrProductId());
                    } else {
                        wsCallAddPicoriar();
                    }
                }

                break;
        }
    }

    private Dialog dialog;
    private static final DateTimeFormatter FORMATTER1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private MaterialCalendarView widget1;
    private TextView mTxtSubmit1;

    //Calender View For Best Before Section
    private void showCalenderDialogueForBeforeDate() {
        dialog = new Dialog(AddOfferActivity.this);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.calenderdialogue);
        dialog.setCancelable(true);
        widget1 = dialog.findViewById(R.id.calendarView);
        mTxtSubmit1 = dialog.findViewById(R.id.btnsubmit);
        widget1.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                mArrBeforeDate.clear();
                mArrBeforeDate.addAll(widget.getSelectedDates());
            }
        });
        widget1.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });


//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2017, 11, 14);
//
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.set(2017, 11, 16);
//        widget.setDateSelected(calendar, true);
//        widget.setDateSelected(calendar1, true);

//        if (mArrDateList.size() > 0) {
//            for (int i = 0; i < mArrDateList.size(); i++) {
//                widget.setDateSelected(CalendarDay.from(mArrDateList.get(i).getDate()), true);
//                // mStrArr.add(FORMATTER.format(mArrDateList.get(i).getDate()));
//            }
//        }

        mTxtSubmit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mStrArr = new ArrayList<>();
                for (int i = 0; i < mArrBeforeDate.size(); i++) {
                    mStrArr.add(FORMATTER1.format(mArrBeforeDate.get(i).getDate()));
                }
                String mStrDates = android.text.TextUtils.join(",", mStrArr);
                mTv_BestBefore.setText(mStrDates);
                dialog.dismiss();
            }
        });
        //Setup initial text
        //  textView.setText("No Selection");
        dialog.show();
    }


    //Show Calender Dialogue To select multiple Date (Note : if you want to select single ,none or multiple then set selection mode as per your requirement) .
    //Comment by sandesh
    private Dialog dialog2;
    private static final DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private MaterialCalendarView widget2;
    private TextView mTxtSubmit2;

    //Calender Date View For Availability
    private void showCalenderDialogueForAvailability() {
        dialog2 = new Dialog(AddOfferActivity.this);
        Window window = dialog2.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog2.setTitle(null);
        dialog2.setContentView(R.layout.calenderdialogue);
        dialog2.setCancelable(true);
        widget2 = dialog2.findViewById(R.id.calendarView);
        mTxtSubmit2 = dialog2.findViewById(R.id.btnsubmit);
        widget2.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                mArrAvailablityDate.clear();
                mArrAvailablityDate.addAll(widget.getSelectedDates());
            }
        });
        widget2.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
        mTxtSubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mStrArr = new ArrayList<>();
                for (int i = 0; i < mArrAvailablityDate.size(); i++) {
                    mStrArr.add(FORMATTER2.format(mArrAvailablityDate.get(i).getDate()));
                }
                String mStrDates = android.text.TextUtils.join(",", mStrArr);
                mTxtAvailablityDate.setText(mStrDates);
                dialog2.dismiss();
            }
        });
        //Setup initial text
        //  textView.setText("No Selection");
        dialog2.show();
    }


    //Show Calender Dialogue To select multiple Date (Note : if you want to select single ,none or multiple then set selection mode as per your requirement) .
    //Comment by sandesh on 20 Feb
    private Dialog dialog1;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private MaterialCalendarView widget;
    private TextView mTxtSubmit;

    //Calender View For Production Date
    private void showCalenderDialogueForProductionDate() {
        dialog1 = new Dialog(AddOfferActivity.this);
        Window window = dialog1.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog1.setTitle(null);
        dialog1.setContentView(R.layout.calenderdialogue);
        dialog1.setCancelable(true);
        widget = dialog1.findViewById(R.id.calendarView);
        mTxtSubmit = dialog1.findViewById(R.id.btnsubmit);
        widget.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                mArrProductDate.clear();
                mArrProductDate.addAll(widget.getSelectedDates());
            }
        });
        widget.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });


//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2017, 11, 14);
//
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.set(2017, 11, 16);
//        widget.setDateSelected(calendar, true);
//        widget.setDateSelected(calendar1, true);

//        if (mArrDateList.size() > 0) {
//            for (int i = 0; i < mArrDateList.size(); i++) {
//                widget.setDateSelected(CalendarDay.from(mArrDateList.get(i).getDate()), true);
//                // mStrArr.add(FORMATTER.format(mArrDateList.get(i).getDate()));
//            }
//        }

        mTxtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mStrArr = new ArrayList<>();
                for (int i = 0; i < mArrProductDate.size(); i++) {
                    mStrArr.add(FORMATTER.format(mArrProductDate.get(i).getDate()));
                }
                String mStrDates = android.text.TextUtils.join(",", mStrArr);
                mTv_ProductionDate.setText(mStrDates);
                dialog1.dismiss();
            }
        });
        //Setup initial text
        //  textView.setText("No Selection");
        dialog1.show();
    }

//    //cpnvert data into json string
//    private String convertToJsonData() {
//        try {
//            JSONArray jsonArray = new JSONArray();
//            for (int i = 0; i < mArrayViewDataList.size(); i++) {
//                JSONObject mJsonObj = new JSONObject();
//                mJsonObj.put("unit_value", mStrUnitArr[mArrayViewDataList.get(i).getmSpn_UnitMeasure().getSelectedItemPosition()].toString());
//                mJsonObj.put("weight", mArrayViewDataList.get(i).getmEdtWightValume().getText().toString());
//                mJsonObj.put("price", mArrayViewDataList.get(i).getmEdtPrice().getText().toString());
//                mJsonObj.put("Number Available", mArrayViewDataList.get(i).getmEdtNumberAvailable().getText().toString());
//                jsonArray.put(mJsonObj);
//
//            }
//            Log.d("Final Arr", jsonArray.toString());
//            return jsonArray.toString();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return "";
//
//    }


    //image section

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        lengthbmp = imageInByte.length;
        return byteArrayOutputStream.toByteArray();
    }


    Uri selectedImage = null;

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

                PicoriarHelperClass picoriarHelperClass = new PicoriarHelperClass();
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

                PicoriarHelperClass picoriarHelperClass = new PicoriarHelperClass();
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
        } else if (requestCode == PICK_IMAGE_CAMERAINGREDIENT && resultCode == RESULT_OK) {
            try {
                selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                byte[] mByteArr = getFileDataFromDrawable(bitmap);

                PicoriarHelperClass picoriarHelperClass = new PicoriarHelperClass();
                picoriarHelperClass.setmImgId("");
                picoriarHelperClass.setmImgFrom("storage");
                picoriarHelperClass.setmImvByte("");
                picoriarHelperClass.setmBitmap(bitmap);
                mPicorialArrListImgIngredient.add(picoriarHelperClass);
                setImgIngredientAdapter();
                setImgAdapter();

                Log.e("Activity", "Pick from Camera::>>> ");
                //   mProfileimg.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERYINGREDIENT && resultCode == RESULT_OK) {

            selectedImage = data.getData();
            //TestingDemo();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");
                byte[] mByteArr = getFileDataFromDrawable(bitmap);

                PicoriarHelperClass picoriarHelperClass = new PicoriarHelperClass();
                picoriarHelperClass.setmImgId("");
                picoriarHelperClass.setmImgFrom("storage");
                picoriarHelperClass.setmImvByte("");
                picoriarHelperClass.setmBitmap(bitmap);
                mPicorialArrListImgIngredient.add(picoriarHelperClass);

                setImgIngredientAdapter();
//                imgPath = getRealPathFromURI(selectedImage);
//                destination = new File(imgPath.toString());
                //  mProfileimg.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setImgAdapter() {
        AddPicoImageAdapter addPicoImageAdapter = new AddPicoImageAdapter(mPicorialArrListImg, AddOfferActivity.this, positionInterface);
        mRecylerImgListview.setAdapter(addPicoImageAdapter);
    }

    private void setImgIngredientAdapter() {
        AddPicoImageAdapter addPicoImageAdapter = new AddPicoImageAdapter(mPicorialArrListImgIngredient, AddOfferActivity.this, positionInterface1);
        mRecyclerImgIngredientListview.setAdapter(addPicoImageAdapter);
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
    StringRequest StringRequestDeleteImg;

    private void wsDeleteImageProduct(String ID) {
        RequestQueueDeleteImg = Volley.newRequestQueue(this);

        StringRequestDeleteImg = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("delete_product_image_picorear"), new Response.Listener<String>() {
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

    private void wsDeleteImageIngredient(String ID) {
        RequestQueueDeleteImg = Volley.newRequestQueue(this);

        StringRequestDeleteImg = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("delete_recipy_image_picorear"), new Response.Listener<String>() {
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


    private void wsUpdateStockAction(DynamicClass mArrFinalData) {
        RequestQueueDeleteImg = Volley.newRequestQueue(this);

        StringRequestDeleteImg = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("update_product_stock_data"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("update_product",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("getstust",jsonObject.getString("status"));
                    if(jsonObject.getString("status").equals("1")){
                        Toast.makeText(AddOfferActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddOfferActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    wsCallCategoryList();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

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
                param.put("stock_id", mArrFinalData.getmStrID());
                param.put("unit", mArrFinalData.getmSpn_UnitMeasure().getSelectedItem().toString());
                param.put("amount", mArrFinalData.getmEdtWightValume().getText().toString());
                param.put("price", mArrFinalData.getmEdtPrice().getText().toString());
                param.put("stock", mArrFinalData.getmEdtNumberAvailable().getText().toString());

                Log.d("params",mArrFinalData.getmStrID()+" "+ mArrFinalData.getmSpn_UnitMeasure().getSelectedItem().toString()+" "+
                        mArrFinalData.getmEdtWightValume().getText().toString()+" "+mArrFinalData.getmEdtPrice().getText().toString()+
                        " "+mArrFinalData.getmEdtNumberAvailable().getText().toString());
                return param;
            }

        };
        RequestQueueDeleteImg.add(StringRequestDeleteImg);

    }

    private void wsUpdateIngredientAction(DynamicClass mArrFinalData) {
        RequestQueueDeleteImg = Volley.newRequestQueue(this);

        StringRequestDeleteImg = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("update_recipy_stock_data"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    wsCallCategoryList();

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
                param.put("incrediant_id", mArrFinalData.getmStrID());
                param.put("incrediant_name", mArrFinalData.getmEdtIngredientName().getText().toString());
                param.put("incrediant_weight", mArrFinalData.getmEdtIngredientWeight().getText().toString());
                param.put("incredient_unit", mArrFinalData.getmSp_UnitIngredient().getSelectedItem().toString());


                return param;
            }

        };
        RequestQueueDeleteImg.add(StringRequestDeleteImg);

    }


    PositionInterface positionInterface1 = new PositionInterface() {
        @Override
        public void onClick(int pos) {

            if (mPicorialArrListImgIngredient.get(pos).getmImgFrom().equalsIgnoreCase("Api")) {
                wsDeleteImageIngredient(mPicorialArrListImgIngredient.get(pos).getmImgId());
                mPicorialArrListImgIngredient.remove(pos);
                mRecyclerImgIngredientListview.getAdapter().notifyDataSetChanged();
            } else {
                mPicorialArrListImgIngredient.remove(pos);
                mRecyclerImgIngredientListview.getAdapter().notifyDataSetChanged();
            }
//            mPicorialArrListImgIngredient.remove(pos);
//            mRecyclerImgIngredientListview.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onClickWithFlag(int pos, int flag) {

        }
    };


    /**
     * Pic Image From Gallary and Camera Dialogue
     */
    // Select image from camera and gallery
    private void selectImage() {
        try {

            final CharSequence[] options = {getResources().getString(R.string.lbl_take_camera_picture), getResources().getString(R.string.lbl_choose_from_gallery), getResources().getString(R.string.cancel)};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddOfferActivity.this);
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
                                openCustomDialogue("Veuillez autoriser l'accÃ¨s Ã  la camÃ©ra");
                                dialog.dismiss();

                            } else if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
                                openCustomDialogue("Veuillez permettre d'accÃ©der au gallary");
                                dialog.dismiss();

                            }

//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                                intent.setData(uri);
//                                startActivity(intent);
                        }

                    } else if (options[item].equals(getResources().getString(R.string.lbl_choose_from_gallery))) {
                        PackageManager pm = getPackageManager();
                        int hasPerm = pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName());
                        if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else {
                            openCustomDialogue("Veuillez permettre d'accÃ©der au gallary");
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
            Toast.makeText(this, "Veuillez autoriser l'accÃ¨s Ã  la camÃ©ra", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    /**
     * Pic Image From Gallary and Camera Dialogue
     */
    // Select image from camera and gallery
    private void selectImageIngredient() {
        try {

            final CharSequence[] options = {getResources().getString(R.string.lbl_take_camera_picture), getResources().getString(R.string.lbl_choose_from_gallery), getResources().getString(R.string.cancel)};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddOfferActivity.this);
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
                            startActivityForResult(intent, PICK_IMAGE_CAMERAINGREDIENT);
                            dialog.dismiss();
                        } else {
                            //  int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
                            //  int hasStoragePermission=pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,getPackageName());
                            if (hasPerm != PackageManager.PERMISSION_GRANTED) {
                                openCustomDialogue("Veuillez autoriser l'accÃ¨s Ã  la camÃ©ra");
                                dialog.dismiss();

                            } else if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
                                openCustomDialogue("Veuillez permettre d'accÃ©der au gallary");
                                dialog.dismiss();

                            }

//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                                intent.setData(uri);
//                                startActivity(intent);
                        }

                    } else if (options[item].equals(getResources().getString(R.string.lbl_choose_from_gallery))) {
                        PackageManager pm = getPackageManager();
                        int hasPerm = pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName());
                        if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERYINGREDIENT);
                        } else {
                            openCustomDialogue("Veuillez permettre d'accÃ©der au gallary");
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
            Toast.makeText(this, "Autoriser l'autorisation de la camÃ©ra", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void openCustomDialogue(String s) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddOfferActivity.this);
        builder.setMessage(s)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.permitmannually), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialog.dismiss();

                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
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


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.yes_deliver:
                if (checked) {
                    mLytDeliver.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.no_deliver:
                if (checked) {
                    mLytDeliver.setVisibility(View.GONE);
                }
                break;
            case R.id.yes_collect:
                if (checked) {
                    mLytbelowcomelater.setVisibility(View.VISIBLE);
                    time_spinner.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.no_collect:
                if (checked) {
                    mLytbelowcomelater.setVisibility(View.GONE);
                    mLytCollect.setVisibility(View.GONE);
                    time_spinner.setVisibility(View.GONE);
                }
                break;

            case  R.id.availiable_later_radioButton:
                if(checked){
                    availabletype="3";
                    mLytCollect.setVisibility(View.VISIBLE);
                }
break;
            case R.id.everyday_radioButton:
                if(checked){
                    availabletype="1";
                    mLytCollect.setVisibility(View.GONE);
                }
break;
            case R.id.every_weekend_radioButton:
                if (checked){
                    availabletype="2";
                    mLytCollect.setVisibility(View.GONE);
                }
                break;
        }
    }


    public Bitmap scaledBitmap(Bitmap bitmap){



        Bitmap scaled= Bitmap.createScaledBitmap(bitmap, 1200, 1000, true);


        return scaled;
    }

}

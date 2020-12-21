package com.app.fr.fruiteefy.user_antigaspi.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.NetworkResponse;
import com.app.fr.fruiteefy.Util.MyGardenImageHelperClass;
import com.app.fr.fruiteefy.Util.PositionInterface;
import com.app.fr.fruiteefy.Util.VolleyMultipartRequest;
import com.app.fr.fruiteefy.user_client.adapter.AdapterSpinnerGardenType;
import com.app.fr.fruiteefy.user_client.adapter.CustomAdapter;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;
import com.app.fr.fruiteefy.user_picorear.dataclasses.CategoryHelperClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.app.fr.fruiteefy.user_antigaspi.AddtogardenActivity;
import com.app.fr.fruiteefy.user_antigaspi.adapter.GardenAdapter;
import com.app.fr.fruiteefy.user_client.home.AllProductPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MyGardenActivity extends AppCompatActivity
        // implements SearchView.OnQueryTextListener
{

    RecyclerView recviewproduct;
    FloatingActionButton addtogarden;
    private ArrayList<AllProductPojo> antiprodArr = new ArrayList<>();
    //View view,view1;
    LinearLayout linlay;
    // SearchView searchview;
    GardenAdapter adapter;
    private String searchString = "";

    private Spinner mSpnGardenType;
    private ArrayList<CategoryHelperClass> mArrGardenType;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private final int PICK_IMAGE_CAMERAINGREDIENT = 11, PICK_IMAGE_GALLERYINGREDIENT = 22;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private long lengthbmp;
    private Bitmap bitmap;
    private ArrayList<MyGardenImageHelperClass> mPicorialArrListImg, mPicorialArrListImgIngredient;
    private RecyclerView mImgVideoList;
    private TextView mTxtSelectImg, mTxtSubmitImgVideo;
    private EditText mEdtAboutMe;
    private TextView mTxtSubmitGardentType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.fragment_my_garden);
        showPhotoDialogue();
        verifyStoragePermissions(this);
        setTitle(getResources().getString(R.string.mygarden));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inIt();
        onClick();


//
//        if( view instanceof LinearLayout) {
//            linlay = (LinearLayout) view;
//        }
//
//        if( view1 instanceof SearchView) {
//            searchview = (SearchView) view1;
//        }
//
//
//        searchview.setOnQueryTextListener(this);

//        linlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                linlay.setVisibility(View.GONE);
//                searchview.setVisibility(View.VISIBLE);
//                searchview.setIconifiedByDefault(true);
//                searchview.setFocusable(true);
//                searchview.setIconified(false);
//                searchview.requestFocusFromTouch();
//
//            }
//        });
//
//        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//
//                linlay.setVisibility(View.VISIBLE);
//                searchview.setVisibility(View.GONE);
//                return false;
//            }
//        });


    }


    private void inIt() {
        mEdtAboutMe = findViewById(R.id.EdtAbout);
        mTxtSubmitGardentType = findViewById(R.id.submitgardentype);
        mTxtSelectImg = findViewById(R.id.selectImg);
        mTxtSubmitImgVideo = findViewById(R.id.submitimg);
        mPicorialArrListImg = new ArrayList<>();
        mImgVideoList = findViewById(R.id.imagelist);
        mImgVideoList.setLayoutManager(new LinearLayoutManager(MyGardenActivity.this, LinearLayoutManager.HORIZONTAL, false));

        mSpnGardenType = findViewById(R.id.spgardentype);
        recviewproduct = findViewById(R.id.rv_antigardn);
        addtogarden = findViewById(R.id.addtogarden);
        mArrGardenType = new ArrayList<>();
        mTxtSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        mTxtSubmitImgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPicorialArrListImg.size() < 5) {
                    wsUpdategardenImg();
                } else {
                    Toast.makeText(MyGardenActivity.this, "You can only upload 5 Images.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mTxtSubmitGardentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wsAddAboutMe();
            }
        });
        wsGetGardenTypes();
        wsGetGardenImgData();
        wsGetGardenTypeAboutMe();
    }

    private void wsAddAboutMe() {
        RequestQueue requestQueue1 = Volley.newRequestQueue(MyGardenActivity.this);
        //  CustomUtil.ShowDialog(MyGardenActivity.this, true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_update"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(MyGardenActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("dfdsfsdf",response);
                wsGetGardenTypeAboutMe();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dfddf", volleyError.toString());
                CustomUtil.DismissDialog(MyGardenActivity.this);
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
                    Toast.makeText(MyGardenActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(MyGardenActivity.this));
                param.put("about_me", mEdtAboutMe.getText().toString());

                param.put("garden_type", mArrGardenType.get(mSpnGardenType.getSelectedItemPosition()).getmStrCatId());

                return param;
            }
        };

        requestQueue1.add(stringRequest1);
    }

    private void wsGetGardenTypeAboutMe() {
        RequestQueue requestQueue1 = Volley.newRequestQueue(MyGardenActivity.this);
        //  CustomUtil.ShowDialog(MyGardenActivity.this, true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_garden_data"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject mJsonGardenData = jsonObject.getJSONObject("garden_data");
                    mEdtAboutMe.setText(mJsonGardenData.getString("about_me"));
                    for (int i = 0; i < mArrGardenType.size(); i++) {
                        if (mJsonGardenData.getString("garden_type").equalsIgnoreCase("" + mArrGardenType.get(i).getmStrCatName())) {
                            mSpnGardenType.setSelection(i);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dfddf", volleyError.toString());
                CustomUtil.DismissDialog(MyGardenActivity.this);
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
                    Toast.makeText(MyGardenActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(MyGardenActivity.this));
                return param;
            }
        };

        requestQueue1.add(stringRequest1);
    }

    private void wsGetGardenImgData() {
        RequestQueue requestQueue1 = Volley.newRequestQueue(MyGardenActivity.this);
        //  CustomUtil.ShowDialog(MyGardenActivity.this, true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_garden_images"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mPicorialArrListImg.clear();
                    Log.d("dfddfrfgfdgfgdgfdg", response);

                //  CustomUtil.DismissDialog(MyGardenActivity.this);
                //antiprodArr.clear();

                //    Log.d("adasds", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("garden_images");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject mjsonObj = jsonArray.getJSONObject(i);
                        MyGardenImageHelperClass myGardenImageHelperClass = new MyGardenImageHelperClass();
                        myGardenImageHelperClass.setmImgId(mjsonObj.getString("id"));
                        myGardenImageHelperClass.setmImgFrom("APi");
                        myGardenImageHelperClass.setmImvByte(mjsonObj.getString("image"));
                        String extension = mjsonObj.getString("image").substring(mjsonObj.getString("image").lastIndexOf("."));
                        if (extension.equalsIgnoreCase(".png") | extension.equalsIgnoreCase(".jpeg") | extension.equalsIgnoreCase(".jpg")) {
                            myGardenImageHelperClass.setVideoOrImg(false);
                        } else {
                            myGardenImageHelperClass.setVideoOrImg(true);
                        }
                        mPicorialArrListImg.add(myGardenImageHelperClass);
                    }
                    setImgAdapter();

//                    JSONArray mJSonArr = jsonObject.getJSONArray("garden_types");
//                    for (int i = 0; i < mJSonArr.length(); i++) {
//                        JSONObject mJSon = mJSonArr.getJSONObject(i);
//                        CategoryHelperClass categoryHelperClass = new CategoryHelperClass();
//                        categoryHelperClass.setmStrCatId(mJSon.getString("id"));
//                        categoryHelperClass.setmStrCatName(mJSon.getString("dropdown"));
//                        mArrGardenType.add(categoryHelperClass);
//                    }
//                    setAdapter();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dfddf", volleyError.toString());
                CustomUtil.DismissDialog(MyGardenActivity.this);
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
                    Toast.makeText(MyGardenActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(MyGardenActivity.this));
                return param;
            }
        };

        requestQueue1.add(stringRequest1);
    }

    private void wsGetGardenTypes() {
        RequestQueue requestQueue1 = Volley.newRequestQueue(MyGardenActivity.this);
        //  CustomUtil.ShowDialog(MyGardenActivity.this, true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_garden_types"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //       Log.d("dfddfrfgfdgfgdgfdg", response);

                //  CustomUtil.DismissDialog(MyGardenActivity.this);
                //antiprodArr.clear();

                //    Log.d("adasds", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray mJSonArr = jsonObject.getJSONArray("garden_types");
                    for (int i = 0; i < mJSonArr.length(); i++) {
                        JSONObject mJSon = mJSonArr.getJSONObject(i);
                        CategoryHelperClass categoryHelperClass = new CategoryHelperClass();
                        categoryHelperClass.setmStrCatId(mJSon.getString("id"));
                        categoryHelperClass.setmStrCatName(mJSon.getString("dropdown"));
                        mArrGardenType.add(categoryHelperClass);
                    }
                    setAdapter();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dfddf", volleyError.toString());
                CustomUtil.DismissDialog(MyGardenActivity.this);
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
                    Toast.makeText(MyGardenActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {

                }
            }
        });

        requestQueue1.add(stringRequest1);
    }

    private void wsUpdategardenImg() {
        CustomUtil.ShowDialog(MyGardenActivity.this,true);
        RequestQueue requestQueue1 = Volley.newRequestQueue(MyGardenActivity.this);
        //  CustomUtil.ShowDialog(MyGardenActivity.this, true);


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, BaseUrl.BASEURL.concat("image_upload_in_garden"),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        CustomUtil.DismissDialog(MyGardenActivity.this);
                        //progressDialog.dismiss();
                        Log.d("sdsda", String.valueOf(response.statusCode));
                        if (response.statusCode == 200) {

                            String stry=new String(response.data);
                            Log.d("fdfdsfd",stry);

                            Intent intent=new Intent(MyGardenActivity.this,MyGardenActivity.class);
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
                        CustomUtil.DismissDialog(MyGardenActivity.this);
                        Log.v("ResError", "" + volleyError.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(MyGardenActivity.this));
                return param;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                for (int i = 0; i < mPicorialArrListImg.size(); i++) {
                    if (!mPicorialArrListImg.get(i).getmImgFrom().equalsIgnoreCase("Api")) {
                        if (mPicorialArrListImg.get(i).isVideoOrImg()) {
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            params.put("image[" + i + "]", new VolleyMultipartRequest.DataPart("" + timeStamp + ".mp4", mPicorialArrListImg.get(i).getmArrImgByte()));

                        } else {
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            params.put("image[" + i + "]", new VolleyMultipartRequest.DataPart(timeStamp+" "+".png", mPicorialArrListImg.get(i).getmArrImgByte()));

                        }
                    }

                }
                return params;
            }
        };

        requestQueue1.add(volleyMultipartRequest);
    }

    private void setAdapter() {
        AdapterSpinnerGardenType adapterSpinnerGardenType = new AdapterSpinnerGardenType(mArrGardenType, MyGardenActivity.this);
        mSpnGardenType.setAdapter(adapterSpinnerGardenType);

    }


//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        final List<AllProductPojo> filteredModelList = filter(antiprodArr, newText);
//        if (filteredModelList.size() > 0) {
//            adapter.setFilter(filteredModelList);
//            return true;
//        } else {
//            // If not matching search filter data
//
//            return false;
//        }
//    }


    private void onClick() {

        RequestQueue requestQueue1 = Volley.newRequestQueue(MyGardenActivity.this);
        // CustomUtil.ShowDialog(MyGardenActivity.this, true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_garden_data"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dfddfrfgfdgfgdgfdg", response);

                //    CustomUtil.DismissDialog(MyGardenActivity.this);
                antiprodArr.clear();

                Log.d("adasds", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("gardenlist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        AllProductPojo allProductPojo = new AllProductPojo();
                        allProductPojo.setGardenid(jsonObject1.getString("garden_id"));
                        allProductPojo.setGardenimg(jsonObject1.getString("subcat_image"));
                        allProductPojo.setCategory(jsonObject1.getString("catname"));
                        allProductPojo.setSubcategory(jsonObject1.getString("subcatname"));
//                        allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
//                        allProductPojo.setPrice(jsonObject1.getString("price"));
//                        allProductPojo.setProductStock(jsonObject1.getString("stock"));
//                        allProductPojo.setWeight(jsonObject1.getString("weight"));
//                        allProductPojo.setUnit(jsonObject1.getString("unit"));
//                        allProductPojo.setDistance("10");
                        antiprodArr.add(allProductPojo);


                    }
                    Log.d("dsdd", String.valueOf(antiprodArr.size()));

                    adapter = new GardenAdapter(antiprodArr, MyGardenActivity.this);
                    recviewproduct.setLayoutManager(new LinearLayoutManager(MyGardenActivity.this));
                    recviewproduct.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dfddf", volleyError.toString());
                CustomUtil.DismissDialog(MyGardenActivity.this);
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
                    Toast.makeText(MyGardenActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(MyGardenActivity.this));
                return param;
            }


        };

        requestQueue1.add(stringRequest1);


        addtogarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyGardenActivity.this, AddtogardenActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }


    private ArrayList<AllProductPojo> filter(ArrayList<AllProductPojo> models, String query) {


        Log.d("sdsda", query);
        this.searchString = query;

        final ArrayList<AllProductPojo> filteredModelList = new ArrayList<>();
        for (AllProductPojo model : models) {

            String text = model.getCategory().toLowerCase();
            String text1 = model.getSubcategory().toLowerCase();
//
//
//            String text1=model.getLastname().toLowerCase();
//
//            String text2=model.getFirstname().toLowerCase()+" "+model.getLastname().toLowerCase();

            if (text.contains(query) || text1.contains(query)) {
                filteredModelList.add(model);
            }
        }

        adapter = new GardenAdapter(filteredModelList, MyGardenActivity.this);

        recviewproduct.setLayoutManager(new LinearLayoutManager(MyGardenActivity.this));
        recviewproduct.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyGardenActivity.this, UserCliantHomeActivity.class);
        startActivity(intent);    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MyGardenActivity.this, UserCliantHomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Pic Image From Gallary and Camera Dialogue
     */
    // Select image from camera and gallery
    private void selectImage() {
        try {

            final CharSequence[] options = {getResources().getString(R.string.lbl_take_camera_picture), getResources().getString(R.string.lbl_choose_from_gallery), getResources().getString(R.string.choosevideo), getResources().getString(R.string.cancel)};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MyGardenActivity.this);
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

                    } else if (options[item].equals(getResources().getString(R.string.lbl_choose_from_gallery))) {
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

                    } else if (options[item].equals(getResources().getString(R.string.choosevideo))) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 20);
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
            Toast.makeText(this, "Veuillez autoriser l'accès à la caméra", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void openCustomDialogue(String s) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MyGardenActivity.this);
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

                MyGardenImageHelperClass picoriarHelperClass = new MyGardenImageHelperClass();
                picoriarHelperClass.setmImgId("");
                picoriarHelperClass.setmImgFrom("storage");
                picoriarHelperClass.setmImvByte("");
                picoriarHelperClass.setmArrImgByte(mByteArr);
                picoriarHelperClass.setmBitmap(bitmap);
                picoriarHelperClass.setVideoOrImg(false);

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

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());


                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");
                byte[] mByteArr = getFileDataFromDrawable(bitmap);

                MyGardenImageHelperClass picoriarHelperClass = new MyGardenImageHelperClass();
                picoriarHelperClass.setmImgId("");
                picoriarHelperClass.setmImgFrom("storage");
                picoriarHelperClass.setVideoOrImg(false);

                picoriarHelperClass.setmImvByte("");
                picoriarHelperClass.setmArrImgByte(mByteArr);
                picoriarHelperClass.setmBitmap(bitmap);
                mPicorialArrListImg.add(picoriarHelperClass);

                setImgAdapter();
//                imgPath = getRealPathFromURI(selectedImage);
//                destination = new File(imgPath.toString());
                //  mProfileimg.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 20 && resultCode == RESULT_OK) {
            try {
                selectedImage = data.getData();


//                bitmap = (Bitmap) data.getExtras().get("data");
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                // byte[] mByteArr = getFileDataFromDrawable(bitmap);
                File imageFile = new File(getRealPathFromURI(selectedImage));
                byte[] mByteArr = convertVideoToBytes(imageFile);

                MyGardenImageHelperClass picoriarHelperClass = new MyGardenImageHelperClass();
                picoriarHelperClass.setmImgId("");
                picoriarHelperClass.setmImgFrom("storage");
                picoriarHelperClass.setmArrImgByte(mByteArr);
                picoriarHelperClass.setVideoOrImg(true);
                picoriarHelperClass.setmImvByte("");
                picoriarHelperClass.setmBitmap(bitmap);
                mPicorialArrListImg.add(picoriarHelperClass);
                // setImgIngredientAdapter();
                //  setImgAdapter();
                setImgAdapter();
                Log.e("Activity", "Pick from Camera::>>> ");
                //   mProfileimg.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public byte[] convertVideoToBytes(File file) {
        byte[] videoBytes = null;
        try {//  w  w w  . j ava 2s . c  o m
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            FileInputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);

            videoBytes = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoBytes;
    }


    private void setImgAdapter() {
        CustomAdapter customAdapter = new CustomAdapter(mPicorialArrListImg, MyGardenActivity.this, positionInterface);
        mImgVideoList.setAdapter(customAdapter);
    }

    PositionInterface positionInterface = new PositionInterface() {
        @Override
        public void onClick(int pos) {
            if (mPicorialArrListImg.size() > 0) {
                for (int i = 0; i < mPicorialArrListImg.size(); i++) {
                    if (pos == i) {
                        if (mPicorialArrListImg.get(pos).getmImgFrom().equalsIgnoreCase("storage")) {
                            mPicorialArrListImg.remove(pos);
                            mImgVideoList.getAdapter().notifyDataSetChanged();
                        } else {
                            wsDeleteImg(mPicorialArrListImg.get(pos).getmImgId(), pos);

                        }


                    }

                }
            }
        }

        @Override
        public void onClickWithFlag(int pos, int flag) {

        }
    };

    private void wsDeleteImg(String getmImgId, int i) {
        RequestQueue requestQueue1 = Volley.newRequestQueue(MyGardenActivity.this);
        // CustomUtil.ShowDialog(MyGardenActivity.this, true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("delete_garden_image"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mPicorialArrListImg.remove(i);
                    mImgVideoList.getAdapter().notifyDataSetChanged();
                    wsGetGardenImgData();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dfddf", volleyError.toString());
                CustomUtil.DismissDialog(MyGardenActivity.this);
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
                    Toast.makeText(MyGardenActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("garden_image_id", getmImgId);
                return param;
            }


        };

        requestQueue1.add(stringRequest1);
    }

}
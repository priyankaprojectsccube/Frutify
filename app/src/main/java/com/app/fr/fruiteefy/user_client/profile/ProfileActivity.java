package com.app.fr.fruiteefy.user_client.profile;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Validation;
import com.app.fr.fruiteefy.Util.VolleyMultipartRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView  txt_dob,txt_fname, txt_lname, txt_emal, txt_mobno,txt_address,txt_pincode;
  //  private ImageView iv_back;
    private View parentLayout;
    private ImageView profpic;
    private LinearLayout layedit;
    private LinearLayout imgedt;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle(R.string.profile);

        init();
        onClick();
    }


    private void init() {

        //tv_title = findViewById(R.id.tv_title);
        // iv_back = findViewById(R.id.iv_back);
        txt_fname = findViewById(R.id.txt_fname);
        txt_lname = findViewById(R.id.txt_lname);
        txt_emal = findViewById(R.id.txt_emal);
        txt_dob=findViewById(R.id.txt_dob);
        layedit = findViewById(R.id.layedit);
        txt_mobno = findViewById(R.id.txt_mobno);
        parentLayout = findViewById(android.R.id.content);
        profpic = findViewById(R.id.profpic);
        imgedt = findViewById(R.id.imgedt);
        txt_address= findViewById(R.id.txt_address);
        txt_pincode= findViewById(R.id.txt_pincode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClick() {
       // tv_title.setText(getResources().getString(R.string.my_prof));
//        iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ProfileActivity.this, UserCliantHomeActivity.class));
//            }
//        });

        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        String img = PrefManager.getProfPic(ProfileActivity.this);
        Log.d("dffddsf", img);
        if (img != null && !img.equals("") && !img.equals("null")) {
            Picasso.with(this).load(BaseUrl.PROFPICURL.concat(img)).into(profpic);
        }
        else{
            Picasso.with(this).load(BaseUrl.LOGO).into(profpic);
        }

        txt_dob.setText(PrefManager.getdob(ProfileActivity.this));
        txt_fname.setText(PrefManager.getFirstName(ProfileActivity.this));
        txt_lname.setText(PrefManager.getLastName(ProfileActivity.this));
        txt_emal.setText(PrefManager.getEmailId(ProfileActivity.this));
        txt_mobno.setText(PrefManager.getMobile(ProfileActivity.this));
        txt_address.setText(PrefManager.getAddress(ProfileActivity.this));
        txt_pincode.setText(PrefManager.getZip(ProfileActivity.this));
    }

    public void changepassword(final View view) {


        final EditText dia_pass, dia_con_pass, dia_new_pass;
        Button btn_submit;

        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.setContentView(R.layout.dialog_change_password);
        dialog.show();



        dia_pass = dialog.findViewById(R.id.dia_pass);
        dia_con_pass = dialog.findViewById(R.id.dia_con_pass);
        dia_new_pass = dialog.findViewById(R.id.dia_new_pass);
        btn_submit = dialog.findViewById(R.id.btn_submit);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);

                Validation validation = new Validation();
                if (!validation.edttxtvalidation(dia_pass, ProfileActivity.this)) {

                } else if (!validation.passvalidation(dia_pass, ProfileActivity.this)) {

                } else if (!validation.edttxtvalidation(dia_new_pass, ProfileActivity.this)) {

                } else if (!validation.passvalidation(dia_new_pass, ProfileActivity.this)) {

                } else if (!validation.edttxtvalidation(dia_con_pass, ProfileActivity.this)) {

                } else if (!validation.conpassvalidation(dia_new_pass, dia_con_pass, ProfileActivity.this)) {

                } else {

                    CustomUtil.ShowDialog(ProfileActivity.this, false);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("change_password"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            CustomUtil.DismissDialog(ProfileActivity.this);
                            Log.d("dsdsad", response);


                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equals("0")) {
                                    Toast.makeText(ProfileActivity.this, jsonObject.getString("messge"), Toast.LENGTH_SHORT).show();
                                } else if (jsonObject.getString("status").equals("1")) {
                                    dialog.dismiss();
                                    Toast.makeText(ProfileActivity.this, jsonObject.getString("messge"), Toast.LENGTH_SHORT).show();
                                }

                                Log.d("dsdsad",jsonObject.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("sdsadd", volleyError.toString());
                            CustomUtil.DismissDialog(ProfileActivity.this);


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

                                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> param = new HashMap<>();
                            param.put("user_id", PrefManager.getUserId(ProfileActivity.this));
                            param.put("old_password", dia_pass.getText().toString());
                            param.put("new_password", dia_new_pass.getText().toString());
                            return param;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("Authorization", "Bearer ".concat(PrefManager.getApiToken(ProfileActivity.this)));

                            return param;
                        }
                    };

                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    public void editprofile(View view) {

        Intent intent= new Intent(ProfileActivity.this, EditprofileActivity.class);
        startActivity(intent);
        finish();
    }

    public void editprofpic(View view) {

        if (checkPermission() == false) {
            requestPermission();
        } else {

            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {


            try {


                final Uri mImageUri = data.getData();
                profpic.setImageURI(mImageUri);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());


                //calling the method uploadBitmap to upload image

                Log.d("iuyiuyiu", String.valueOf(mImageUri));


                CustomUtil.ShowDialog(ProfileActivity.this, false);
                RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
                    VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, BaseUrl.BASEURL.concat("profile_image_update"),
                        new Response.Listener<NetworkResponse>() {

                            @Override
                            public void onResponse(NetworkResponse response) {

                                CustomUtil.DismissDialog(ProfileActivity.this);
                                Log.d("dsadd", String.valueOf(response.statusCode));
                                if (response.statusCode == 200) {
                                    String result = new String(response.data);
                                    try {
                                        JSONObject response1 = new JSONObject(result);
                                        Log.d("dsadsa", String.valueOf(response1));
                                        if (response1.getString("status").equals("0")) {


                                            Toast.makeText(ProfileActivity.this, response1.getString("messge"), Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(ProfileActivity.this, ProfileActivity.class);

                                            startActivity(intent);
                                            finish();

                                        } else if (response1.getString("status").equals("1")) {
                                            PrefManager.setProfilePic(response1.getString("profile_pic"),ProfileActivity.this);
                                            Toast.makeText(ProfileActivity.this, response1.getString("messge"), Toast.LENGTH_SHORT).show();

                                            PrefManager.setProfilePic(response1.getString("profile_pic"), ProfileActivity.this);


                                            Intent intent=new Intent(ProfileActivity.this, ProfileActivity.class);

                                            startActivity(intent);
                                            finish();


                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                CustomUtil.DismissDialog(ProfileActivity.this);
                                //volleyError.printStackTrace();

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

                                    Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();

                                }
                                //else

//                                    Snackbar.make(parentLayout, getResources().getString(R.string.error_occur), Snackbar.LENGTH_LONG)
//                                            .setAction("Action", null).show();


                            }
                        }) {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();

                        param.put("user_id", PrefManager.getUserId(ProfileActivity.this));
                        param.put("old_profile_pic",PrefManager.getProfPic(ProfileActivity.this));

                        return param;
                    }

//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("Accept", "application/json");
//
//                        return params;
//                    }

                    /*
                     * Here we are passing image by renaming it with a unique name
                     * */
                    @Override
                    protected Map<String, VolleyMultipartRequest.DataPart> getByteData() throws AuthFailureError {
                        Map<String, DataPart> params = new HashMap<>();


                        try {

                                if (bitmap.getHeight() > 1200 || bitmap.getWidth() > 1920) {

                                    params.put("profile_pic", new VolleyMultipartRequest.DataPart( ".png",getFileDataFromDrawable(scaledBitmap(bitmap))));

                            } else {

                                params.put("profile_pic", new VolleyMultipartRequest.DataPart(".png", getFileDataFromDrawable(bitmap)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        return params;
                    }
                };

                requestQueue.add(volleyMultipartRequest);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void requestPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//        } else {
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
       // }
    }

    private boolean checkPermission() {

        int result3 = (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE));

        if (result3 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        return byteArrayOutputStream.toByteArray();
    }



    public byte[] readBytes(Uri uri) throws IOException {
        // this dynamically extends to take the bytes you read
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED  ) {


                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1);

                } else {

                    Toast.makeText(this, "Storage permission is required to access Photo", Toast.LENGTH_SHORT).show();
                }
                return;
            }


        }
    }

    public Bitmap scaledBitmap(Bitmap bitmap){
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1500, 1000, true);
        return scaled;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


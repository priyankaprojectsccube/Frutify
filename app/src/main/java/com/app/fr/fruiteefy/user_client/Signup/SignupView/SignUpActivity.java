package com.app.fr.fruiteefy.user_client.Signup.SignupView;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.app.fr.fruiteefy.user_client.Signup.SignupPressenter.SignupPresenter;
import com.google.android.gms.common.api.Status;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements ISignupView,Spinner.OnItemSelectedListener{
TextView tv_already_user;
EditText fname,lname,email,phoneno,pass,conpass,address;
SignupPresenter signupPresenter;
String type="";
    private Spinner spiner_category;
    private ArrayList<String> categoryArrList;

Button signup;
String addr,city,country,lat,lng,state,zip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(getResources().getString(R.string.sign_up));

        Init();
        signupPresenter=new SignupPresenter(this,SignUpActivity.this);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_already_user=findViewById(R.id.tv_already_user);
        spiner_category=findViewById(R.id.spiner_category);
        categoryArrList=new ArrayList<>();
        tv_already_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        categoryArrList.add(0,getResources().getString(R.string.select));
        categoryArrList.add(1,getResources().getString(R.string.particular));
        categoryArrList.add(2,getResources().getString(R.string.association));


        spiner_category.setOnItemSelectedListener(this);
        spiner_category.setAdapter(new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_spinner_dropdown_item, categoryArrList));


        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Places.isInitialized()) {
                    Places.initialize(SignUpActivity.this,  getResources().getString(R.string.google_maps_key));
                    PlacesClient placesClient = Places.createClient(SignUpActivity.this);
                }


                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS,Place.Field.PLUS_CODE);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)

                        .setCountry("FR")
                        .build(SignUpActivity.this);
                startActivityForResult(intent, 1);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupPresenter.validateData(fname.getText().toString(),lname.getText().toString(),email.getText().toString(),
                        address.getText().toString(),lat,lng,city,country,state,phoneno.getText().toString(),pass.getText().toString(),conpass.getText().toString(),type);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    if (item.getItemId()==android.R.id.home)
    {
        finish();
    }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getSignupResult(String status) {
        CustomUtil.DismissDialog(SignUpActivity.this);
        Log.d("sdsadd",status);
        try {
            JSONObject jsonObject=new JSONObject(status);
            if(jsonObject.getString("status").equals("1")){
                Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
               Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
               intent.putExtra("data","signup");
                startActivity(intent);
                finish();
            }
            if(jsonObject.getString("status").equals("0")){
                Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setProgressbar(int Visibility) {
        CustomUtil.DismissDialog(SignUpActivity.this);
    }

    @Override
    public void onValidatedata(int position) {
        Log.d("ghhghg", String.valueOf(position));

        switch (position){

        case 1:
        fname.setError(getResources().getString(R.string.fill_field));
        break;
        case 2:
        lname.setError(getResources().getString(R.string.fill_field));
        break;
        case 3:
        email.setError(getResources().getString(R.string.fill_field));
        break;
        case 4:
        email.setError(getResources().getString(R.string.email_error));
        break;
        case 5:
        phoneno.setError(getResources().getString(R.string.fill_field));
        break;
        case 6:
        address.setError(getResources().getString(R.string.fill_field));
        break;
        case 7:
        pass.setError(getResources().getString(R.string.fill_field));
        break;
        case 8:
        conpass.setError(getResources().getString(R.string.fill_field));
        break;
        case 9:
        conpass.setError(getResources().getString(R.string.passnotmatch));
        break;
        case 10:
            Toast.makeText(SignUpActivity.this, getResources().getString(R.string.select_iam), Toast.LENGTH_SHORT).show();
        break;

        case 11:

            signupPresenter.doSignUp(fname.getText().toString(),lname.getText().toString(),email.getText().toString(),address.getText().toString(),lat,lng,city,country,state,phoneno.getText().toString(),pass.getText().toString(),conpass.getText().toString(),zip,type);
        CustomUtil.ShowDialog(SignUpActivity.this,true);
        break;
    }
    }

    @Override
    public void onSignUpFailure(Throwable t) {
        Log.d("sdsadd",t.toString());
        CustomUtil.DismissDialog(SignUpActivity.this);
        VolleyError volleyError= (VolleyError) t;
        Log.d("dasd",t.toString());
        String message = null;
        if (volleyError instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (volleyError instanceof AuthFailureError) {

        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void Init(){
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        conpass=findViewById(R.id.conpass);
        address=findViewById(R.id.address);
        signup=findViewById(R.id.signup);
        phoneno=findViewById(R.id.phone);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                // piclatlng = Autocomplete.getPlaceFromIntent(data).getLatLng();


                address.setText(Autocomplete.getPlaceFromIntent(data).getAddress());
                Autocomplete.getPlaceFromIntent(data).getLatLng();

                lat= String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude);
                lng= String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude);
                Geocoder geocoder = new Geocoder(this);
                try
                {
                    List<Address> addresses = geocoder.getFromLocation(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude,Autocomplete.getPlaceFromIntent(data).getLatLng().longitude, 1);
                    if(addresses!=null) {
//                        String address = addresses.get(0).getAddressLine(0);
//                        String city = addresses.get(0).getAddressLine(1);
//                        String country = addresses.get(0).getAddressLine(2);


                         addr = addresses.get(0).getSubLocality();
                         city = addresses.get(0).getLocality();
                       country = addresses.get(0).getCountryName();
                       state=addresses.get(0).getAdminArea();
                       zip=addresses.get(0).getPostalCode();

                        Log.d("dsdsdd",address+" "+city+" "+country);

                    }




                } catch (IOException e)
                {

                    e.printStackTrace();
                }


            }

            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.d("fdgfdgfd", String.valueOf(status));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.d("sfsdfds", String.valueOf(position));


        if(position==1){
            type="Individual";

        }
        else if(position==2){
            type="Company";
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

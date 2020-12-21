package com.app.fr.fruiteefy.user_client.checkout;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.app.fr.fruiteefy.user_client.home.PaymentCartActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentCheckOut extends Fragment {
    private View mMainView;
    String amounttopay="",totaltosend="",finaltotaltosend="",servicechargetosend="";
    private String promocode="",promoapplytype="";
    private TextView mTxtShippingCost, mTxtSubtotal, mTxtPromotionalCodeReduction, mTxtServiceCharge, mTxtTotal;
    private LinearLayout mLytBtnReserve;
    private EditText mEdtFirstName, mEdtLastName, mEdtEmail, mEdtAddress, mEdtCountry, mEdtCity, mEdtRegion, mEdtPostalCode;
    private String mStrCartCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.layout_checkout, container, false);
        setUI();
        return mMainView;
    }

    private void setUI() {
        mLytBtnReserve = mMainView.findViewById(R.id.BtnReserve);
        mTxtShippingCost = mMainView.findViewById(R.id.txtshippingcost);
        mTxtPromotionalCodeReduction = mMainView.findViewById(R.id.promocodereduction);
        mTxtServiceCharge = mMainView.findViewById(R.id.servicecharge);
        mTxtSubtotal = mMainView.findViewById(R.id.subtotal);
        mTxtTotal = mMainView.findViewById(R.id.TxtTotal);

        mEdtFirstName = mMainView.findViewById(R.id.edtfirstname);
        mEdtLastName = mMainView.findViewById(R.id.edtlastname);
        mEdtEmail = mMainView.findViewById(R.id.edtEmail);
        mEdtAddress = mMainView.findViewById(R.id.edtAddress);
        mEdtCountry = mMainView.findViewById(R.id.edtcountry);
        mEdtCity = mMainView.findViewById(R.id.edtcity);
        mEdtRegion = mMainView.findViewById(R.id.edtregion);
        mEdtPostalCode = mMainView.findViewById(R.id.edtpostalcode);


        mLytBtnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                        Intent intent=new Intent(getActivity(), PaymentCartActivity.class);

                        intent.putExtra("finaltotal", finaltotaltosend);

                        intent.putExtra("first_name",mEdtFirstName.getText().toString());
                        intent.putExtra("last_name",mEdtLastName.getText().toString());
                        intent.putExtra("email",mEdtEmail.getText().toString());
                        intent.putExtra("address",mEdtAddress.getText().toString());
                        intent.putExtra("country",mEdtCountry.getText().toString());
                        intent.putExtra("state", mEdtRegion.getText().toString());
                        intent.putExtra("city",mEdtCity.getText().toString());
                        intent.putExtra("zip",mEdtPostalCode.getText().toString());
                        intent.putExtra("total",totaltosend);
                        intent.putExtra("service_charge",servicechargetosend);
                       intent.putExtra("vendor_id","");
                       intent.putExtra("amounttopay",amounttopay);
                        intent.putExtra("promo_code",promocode);
                      intent.putExtra("promo_apply_type",promoapplytype);
                        startActivity(intent);



            }
        });

        mEdtAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!Places.isInitialized()) {
                    Places.initialize(getActivity(), getResources().getString(R.string.google_maps_key));
                    PlacesClient placesClient = Places.createClient(getActivity());
                }


                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.PLUS_CODE);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .setTypeFilter(TypeFilter.REGIONS)
                        .setCountry("FR")
                        .build(getActivity());
                startActivityForResult(intent, 4);
            }
        });


        GetCartSummary();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {

                // piclatlng = Autocomplete.getPlaceFromIntent(data).getLatLng();

                mEdtAddress.setText(Autocomplete.getPlaceFromIntent(data).getAddress());
                Autocomplete.getPlaceFromIntent(data).getLatLng();


                //lat = String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude);
                //   lng = String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude);

                //   PrefManager.setLAT(String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude), AntiAddofferActivity.this);
                //  PrefManager.setLAT(String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude), AntiAddofferActivity.this);
                Geocoder geocoder = new Geocoder(getActivity());
                try {
                    List<Address> addresses = geocoder.getFromLocation(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude, Autocomplete.getPlaceFromIntent(data).getLatLng().longitude, 1);
                    if (addresses != null) {
//                        edt_state.setText(addresses.get(0).getAdminArea());
//                        edt_city.setText(addresses.get(0).getLocality());
//                        edt_country.setText(addresses.get(0).getCountryName());
                        mEdtCity.setText(addresses.get(0).getLocality());
                        mEdtRegion.setText(addresses.get(0).getAdminArea());
                        mEdtPostalCode.setText(addresses.get(0).getPostalCode());
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.d("fdgfdgfd", String.valueOf(status));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void GetCartSummary() {
        CustomUtil.ShowDialog(getActivity(),false);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("checkout"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CustomUtil.DismissDialog(getActivity());
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("ddasdas",response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("checkout");
                        promocode=""+jsonObject1.getString("promo_code");
                        promoapplytype=""+jsonObject1.getString("promo_apply_type");
                        Double one=0.0;
                        if(!jsonObject1.getString("deliverycharges").equals("null")) {
                             one = Double.valueOf(jsonObject1.getString("deliverycharges"));
                        }
                        else{
                             one = 0.0;
                        }
                            Double two = Double.valueOf(jsonObject1.getString("total"));
                            Double add = one + two;


                        amounttopay= String.valueOf(add);

                        if(jsonObject1.getString("deliverycharges").equalsIgnoreCase("null")){
                            mTxtShippingCost.setText("0"+" "+getResources().getString(R.string.currency));
                        }
                        else {
                            mTxtShippingCost.setText("" + jsonObject1.getString("deliverycharges") + " " + getResources().getString(R.string.currency));
                        }

                        mTxtServiceCharge.setText("" + jsonObject1.getDouble("service_charge")+" "+getResources().getString(R.string.currency));
                        servicechargetosend="" + jsonObject1.getDouble("service_charge");
                        mTxtSubtotal.setText("" + jsonObject1.getString("total")+" "+getResources().getString(R.string.currency));
                        totaltosend=jsonObject1.getString("total");
                        mTxtPromotionalCodeReduction.setText("" + jsonObject1.getString("discountamt")+" "+getResources().getString(R.string.currency));

                        if( String.valueOf(jsonObject1.getDouble("final_total")).equalsIgnoreCase("null")){
                            mTxtTotal.setText("0"+" "+getResources().getString(R.string.currency));
                            finaltotaltosend="0";
                        }
                        else {
                            mTxtTotal.setText("" + jsonObject1.getDouble("final_total")+" "+getResources().getString(R.string.currency));
                            finaltotaltosend=""+jsonObject1.getDouble("final_total");
                        }

                        mEdtFirstName.setText("" + jsonObject1.getString("firstname"));
                        mEdtLastName.setText("" + jsonObject1.getString("lastname"));
                        mEdtEmail.setText("" + jsonObject1.getString("email"));
                        mEdtAddress.setText("" + jsonObject1.getString("address"));
                        mEdtCountry.setText("" + jsonObject1.getString("country"));
                        mEdtCity.setText("" + jsonObject1.getString("city"));
                        mEdtRegion.setText("" + jsonObject1.getString("state"));
                        mEdtPostalCode.setText("" + jsonObject1.getString("zip"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomUtil.DismissDialog(getActivity());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

}

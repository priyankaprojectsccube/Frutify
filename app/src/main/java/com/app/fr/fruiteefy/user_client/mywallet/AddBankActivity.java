package com.app.fr.fruiteefy.user_client.mywallet;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.mywallet.adapter.CommonSpnAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddBankActivity extends AppCompatActivity {
    private Spinner mSpnBankType, mSpnCountry, mSpnOtherCountry, mSpntypeDeposite;
    String[] mStrBankType, mStrDeposite, mStrCountry;
    private EditText mEdtOwnerName, mEdtOwnerAddressLine1, mEdtOwnerAddressLine2, mEdtCity, mEdtRegion, mEdtPostalCode, mEdtLabel;
    private LinearLayout mLytIBAN, mLytGB, mLytUS, mLytIT, mLytOther;
    private EditText mEdtIBAN, mEdtBC, mEdtGBAccountNum, mEdtGBSettingCode, mEdtUSAccountNumber, mEdtUSaba, mEdtITaccountNumber, mEdtITBranchCode, mEdtITBankName, mEdtITInstitutionNu,
            mEdtOtherAccontNum, mEdtOtherBIC;
    private LinearLayout mLytBtnValidate;
    private ImageView mBackImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        setUI();
    }

    private void setUI() {
        mBackImg = findViewById(R.id.backimg);
        mEdtOwnerName = findViewById(R.id.edtownername);
        mEdtOwnerAddressLine1 = findViewById(R.id.edtowneraddress1);
        mEdtOwnerAddressLine2 = findViewById(R.id.edtowneraddress2);
        mEdtCity = findViewById(R.id.edtcity);
        mEdtRegion = findViewById(R.id.edtregion);
        mEdtPostalCode = findViewById(R.id.edtpostalcode);

        //lyt IBAN
        mLytIBAN = findViewById(R.id.lytIBAN);
        mEdtIBAN = findViewById(R.id.edtIBAN);
        mEdtBC = findViewById(R.id.edtbic);

        //lyt GB
        mLytGB = findViewById(R.id.lytGB);
        mEdtGBAccountNum = findViewById(R.id.edtGbaccount);
        mEdtGBSettingCode = findViewById(R.id.edtseetingcode);

        //lytUS
        mLytUS = findViewById(R.id.lytUs);
        mEdtUSAccountNumber = findViewById(R.id.edtusaccountno);
        mEdtUSaba = findViewById(R.id.edtAba);

        //lytIT
        mLytIT = findViewById(R.id.lytIt);
        mEdtITaccountNumber = findViewById(R.id.edtItaccountnumber);
        mEdtITBranchCode = findViewById(R.id.edtBranchCode);
        mEdtITBankName = findViewById(R.id.edtBankName);
        mEdtITInstitutionNu = findViewById(R.id.edtInstitutionno);

        //lytOther
        mLytOther = findViewById(R.id.lytOther);
        mEdtOtherAccontNum = findViewById(R.id.edtotheraccountno);
        mEdtOtherBIC = findViewById(R.id.edtOtherBIC);

        mLytBtnValidate = findViewById(R.id.btnvalidate);

        mSpnBankType = findViewById(R.id.spinnerbank);
        mSpntypeDeposite = findViewById(R.id.typedeposite);
        mSpnCountry = findViewById(R.id.spCountry);
        mSpnOtherCountry = findViewById(R.id.spOtherCountry);

        mStrBankType = getResources().getStringArray(R.array.bankType);
        mStrDeposite = getResources().getStringArray(R.array.deposite);
        mStrCountry = getResources().getStringArray(R.array.Country);

        setDepositeAdapter();
        setCountryAdapter();
        setAdapterBankType();

        mLytBtnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation()) {
                    wsSaveBankDetails();
                    //Toast.makeText(AddBankActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void setCountryAdapter() {
        CommonSpnAdapter commonSpnAdapter = new CommonSpnAdapter(mStrCountry, AddBankActivity.this);
        mSpnCountry.setAdapter(commonSpnAdapter);

        CommonSpnAdapter commonSpnAdapter1 = new CommonSpnAdapter(mStrCountry, AddBankActivity.this);
        mSpnOtherCountry.setAdapter(commonSpnAdapter1);
    }


    private void setDepositeAdapter() {
        CommonSpnAdapter commonSpnAdapter = new CommonSpnAdapter(mStrDeposite, AddBankActivity.this);
        mSpntypeDeposite.setAdapter(commonSpnAdapter);
    }


    private void setAdapterBankType() {
        CommonSpnAdapter commonSpnAdapter = new CommonSpnAdapter(mStrBankType, AddBankActivity.this);
        mSpnBankType.setAdapter(commonSpnAdapter);

        mSpnBankType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    lytVisibility();
                    mLytIBAN.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    lytVisibility();
                    mLytGB.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    lytVisibility();
                    mLytUS.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    lytVisibility();
                    mLytIT.setVisibility(View.VISIBLE);
                } else if (position == 4) {
                    lytVisibility();
                    mLytOther.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //set visibility gone intiality
    private void lytVisibility() {
        mLytIBAN.setVisibility(View.GONE);
        mLytGB.setVisibility(View.GONE);
        mLytUS.setVisibility(View.GONE);
        mLytOther.setVisibility(View.GONE);
        mLytIT.setVisibility(View.GONE);
    }

    //set Validation
    boolean Validation() {

        mEdtOwnerName.setError(null);
        mEdtOwnerName.clearFocus();

        mEdtOwnerAddressLine1.setError(null);
        mEdtOwnerAddressLine1.clearFocus();
        mEdtOwnerAddressLine2.setError(null);
        mEdtOwnerAddressLine2.clearFocus();
        mEdtCity.setError(null);
        mEdtCity.clearFocus();

        mEdtRegion.setError(null);
        mEdtRegion.clearFocus();
        mEdtPostalCode.setError(null);
        mEdtPostalCode.clearFocus();
        mEdtIBAN.setError(null);
        mEdtIBAN.clearFocus();

        mEdtBC.setError(null);
        mEdtBC.clearFocus();

        mEdtGBSettingCode.setError(null);
        mEdtGBSettingCode.clearFocus();

        mEdtGBAccountNum.setError(null);
        mEdtGBAccountNum.clearFocus();


        mEdtUSAccountNumber.setError(null);
        mEdtUSAccountNumber.clearFocus();

        mEdtUSaba.setError(null);
        mEdtUSaba.clearFocus();
        mEdtITaccountNumber.setError(null);
        mEdtITaccountNumber.clearFocus();

        mEdtITBranchCode.setError(null);
        mEdtITBranchCode.clearFocus();

        mEdtITBankName.setError(null);
        mEdtITBankName.clearFocus();

        mEdtITInstitutionNu.setError(null);
        mEdtITInstitutionNu.clearFocus();


        mEdtOtherAccontNum.setError(null);
        mEdtOtherAccontNum.clearFocus();

        mEdtOtherBIC.setError(null);
        mEdtOtherBIC.clearFocus();

        if (mEdtOwnerName.getText().toString().equalsIgnoreCase("")) {
            mEdtOwnerName.requestFocus();
            mEdtOwnerName.setError("Enter Owner's Name");
            return false;
        } else if (mEdtOwnerAddressLine1.getText().toString().equalsIgnoreCase("")) {
            mEdtOwnerAddressLine1.requestFocus();
            mEdtOwnerAddressLine1.setError("Enter Owner's first Address ");
            return false;
        } else if (mEdtOwnerAddressLine2.getText().toString().equalsIgnoreCase("")) {
            mEdtOwnerAddressLine2.requestFocus();
            mEdtOwnerAddressLine2.setError("Enter Owner's second address");
            return false;
        } else if (mEdtCity.getText().toString().equalsIgnoreCase("")) {
            mEdtCity.requestFocus();
            mEdtCity.setError("Enter City");
            return false;
        } else if (mEdtRegion.getText().toString().equalsIgnoreCase("")) {
            mEdtRegion.requestFocus();
            mEdtRegion.setError("Enter Region");
            return false;
        } else if (mEdtPostalCode.getText().toString().equalsIgnoreCase("")) {
            mEdtPostalCode.requestFocus();
            mEdtPostalCode.setError("Enter Postal Code");
            return false;
        } else if (mLytIBAN.isShown() && mEdtIBAN.getText().toString().equalsIgnoreCase("")) {
            mEdtIBAN.requestFocus();
            mEdtIBAN.setError("Enter IBAN");
            return false;
        } else if (mLytIBAN.isShown() && mEdtBC.getText().toString().equalsIgnoreCase("")) {
            mEdtBC.requestFocus();
            mEdtBC.setError("Enter BIC");
            return false;
        } else if (mLytGB.isShown() && mEdtGBAccountNum.getText().toString().equalsIgnoreCase("")) {
            mEdtGBAccountNum.requestFocus();
            mEdtGBAccountNum.setError("Enter Account Number");
            return false;
        } else if (mLytGB.isShown() && mEdtGBSettingCode.getText().toString().equalsIgnoreCase("")) {
            mEdtGBSettingCode.requestFocus();
            mEdtGBSettingCode.setError("Enter Setting Code");
            return false;
        } else if (mLytUS.isShown() && mEdtUSAccountNumber.getText().toString().equalsIgnoreCase("")) {
            mEdtUSAccountNumber.requestFocus();
            mEdtUSAccountNumber.setError("Enter Account Number");
            return false;
        } else if (mLytUS.isShown() && mEdtUSaba.getText().toString().equalsIgnoreCase("")) {
            mEdtUSaba.requestFocus();
            mEdtUSaba.setError("Enter ABA");
            return false;
        } else if (mLytIT.isShown() && mEdtITaccountNumber.getText().toString().equalsIgnoreCase("")) {
            mEdtITaccountNumber.requestFocus();
            mEdtITaccountNumber.setError("Enter Account Number");
            return false;
        } else if (mLytIT.isShown() && mEdtITBranchCode.getText().toString().equalsIgnoreCase("")) {
            mEdtITBranchCode.requestFocus();
            mEdtITBranchCode.setError("Enter Branch Code");
            return false;
        } else if (mLytIT.isShown() && mEdtITBankName.getText().toString().equalsIgnoreCase("")) {
            mEdtITBankName.requestFocus();
            mEdtITBankName.setError("Enter Bank Number");
            return false;
        } else if (mLytIT.isShown() && mEdtITInstitutionNu.getText().toString().equalsIgnoreCase("")) {
            mEdtITInstitutionNu.requestFocus();
            mEdtITInstitutionNu.setError("Enter Institution Number");
            return false;
        } else if (mLytOther.isShown() && mEdtOtherAccontNum.getText().toString().equalsIgnoreCase("")) {
            mEdtOtherAccontNum.requestFocus();
            mEdtOtherAccontNum.setError("Enter Account Number");
            return false;
        } else if (mLytOther.isShown() && mEdtOtherBIC.getText().toString().equalsIgnoreCase("")) {
            mEdtOtherBIC.requestFocus();
            mEdtOtherBIC.setError("Enter BIC");
            return false;
        }
        return true;
    }


    private void wsSaveBankDetails() {
        RequestQueue requestQueue = Volley.newRequestQueue(AddBankActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("add_bank_account"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // mArrBankList.clear();
                // Log.d("sdsadd", response);
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                        Toast.makeText(AddBankActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(AddBankActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

//                    if (jsonObject.getString("status").equals("1")) {
//                        JSONArray mJsonArr = jsonObject.getJSONArray("mycartdata");
//                        for (int i = 0; i < mJsonArr.length(); i++) {
//                            JSONObject mJsonObj = mJsonArr.getJSONObject(i);
//                            HashMap<String, String> mhash = new HashMap<>();
//                            mhash.put("Id", mJsonObj.getString("Id"));
//                            mhash.put("OwnerName", mJsonObj.getString("OwnerName"));
//                            mhash.put("account_no", mJsonObj.getString("account_no"));
//                            mhash.put("status", mJsonObj.getString("status"));
//                            mhash.put("Type", mJsonObj.getString("Type"));
//                            mArrBankList.add(mhash);
//                        }
//                        setAdapterSpinner();
                    //  }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /// Log.d("sdsadd", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(AddBankActivity.this));
                param.put("select_bank_type", "" + mSpnBankType.getSelectedItem().toString());
                if (mSpnBankType.getSelectedItem().toString().equalsIgnoreCase("IBAN")) {
                    param.put("text_owner_ibannum", mEdtIBAN.getText().toString());
                    param.put("text_owner_bic_num", mEdtBC.getText().toString());

                } else if (mSpnBankType.getSelectedItem().toString().equalsIgnoreCase("GB")) {
                    param.put("text_owner_account_number_gb", mEdtGBAccountNum.getText().toString());
                    param.put("text_owner_sort_code_gb", mEdtGBSettingCode.getText().toString());

                } else if (mSpnBankType.getSelectedItem().toString().equalsIgnoreCase("US")) {
                    param.put("text_owner_account_number_us", mEdtUSAccountNumber.getText().toString());
                    param.put("text_owner_aba_us", mEdtUSaba.getText().toString());
                    param.put("text_owner_deposit_acc_typ_us", mSpntypeDeposite.getSelectedItem().toString());


                } else if (mSpnBankType.getSelectedItem().toString().equalsIgnoreCase("CA") | mSpnBankType.getSelectedItem().toString().equalsIgnoreCase("IT")) {
                    param.put("text_owner_account_number_ca", mEdtITaccountNumber.getText().toString());
                    param.put("text_owner_brnch_code_ca", mEdtITBranchCode.getText().toString());
                    param.put("text_owner_bank_name_ca", mEdtITBankName.getText().toString());
                    param.put("text_owner_institution_num_ca", mEdtITaccountNumber.getText().toString());

                }
                if (mSpnBankType.getSelectedItem().toString().equalsIgnoreCase("OTHER")) {
                    param.put("text_owner_account_number_other", mEdtIBAN.getText().toString());
                    param.put("text_owner_bic_num_other", mEdtOtherBIC.getText().toString());
                    param.put("select_country_other", mSpnOtherCountry.getSelectedItem().toString());


                }

                param.put("text_owner_name", mEdtOwnerName.getText().toString());
                param.put("text_addressline1", mEdtOwnerAddressLine1.getText().toString());
                param.put("text_addressline2", mEdtOwnerAddressLine2.getText().toString());
                param.put("text_ownercity", mEdtCity.getText().toString());
                param.put("text_ownerregion", mEdtRegion.getText().toString());
                param.put("text_ownerpostalcode", mEdtPostalCode.getText().toString());
                param.put("select_country", mSpnCountry.getSelectedItem().toString());

                //// Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }


}

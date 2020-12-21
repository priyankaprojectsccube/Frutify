package com.app.fr.fruiteefy.user_client.mywallet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_client.mywallet.adapter.MyBalancesheetAdapter;

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

public class FragmentBalanceSheet extends Fragment implements SearchView.OnQueryTextListener,Spinner.OnItemSelectedListener{
    private View mMainView;
    private RecyclerView mRecyclerbalancesheet;
    private ImageView mImv_Filter;
    private LinearLayout mLytFilterLyt;
    private Spinner spinnertype;
    private RelativeLayout datelyt,datemonthly;
    private TextView tv_apply,mTxtProductionDatesSelected,mTxtMonthly;

    private String searchString = "";
    RecyclerView lst_viewbalancesheet;
    private ArrayList<Product> allreceipy = new ArrayList<Product>();
    private RequestQueue requestQueue;
    private ArrayList<String> sellertype;
    MyBalancesheetAdapter adapter;
    String transactiontype;
    ImageView backimg;
    private Activity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.layout_balancesheet, container, false);
        setUI();

        sellertype=new ArrayList<>();




        sellertype.add(0,getResources().getString(R.string.select));
        sellertype.add(1,getResources().getString(R.string.purchase));
        sellertype.add(2,getResources().getString(R.string.subscription));
        sellertype.add(3,getResources().getString(R.string.sale));
        sellertype.add(4,getResources().getString(R.string.reservation));

        spinnertype.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sellertype));

        spinnertype.setOnItemSelectedListener(this);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, new MyWalletInforFragment()).commit();
            }
        });
        return mMainView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Product> filteredModelList = filter(allreceipy, newText);
        if (filteredModelList.size() > 0) {
            adapter.setFilter(filteredModelList);
            return true;
        } else {
            // If not matching search filter data

            return false;
        }
    }



    private ArrayList<Product> filter(ArrayList<Product> models, String query) {


        Log.d("sdsda", query);
        this.searchString = query;

        final ArrayList<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {

            String text = model.getProductid().toLowerCase();
//
//
          String text1=model.getProductname().toLowerCase();
//
            String text2=model.getRating().toLowerCase();

            if (text.contains(query)) {
                filteredModelList.add(model);
            }
            else if(text1.contains(query)){
                filteredModelList.add(model);
            }
            else if(text2.contains(query)){
                filteredModelList.add(model);
            }
        }


        adapter = new MyBalancesheetAdapter(filteredModelList, getActivity());
        lst_viewbalancesheet.setLayoutManager(new LinearLayoutManager(getActivity()));
        lst_viewbalancesheet.setAdapter(adapter);


        adapter.notifyDataSetChanged();

        return filteredModelList;
    }


    //inflate all view from layout_balancesheet.xml file
    private void setUI() {
        activity=(WalletActivity)getActivity();
        backimg=activity.findViewById(R.id.backimg);
        requestQueue= Volley.newRequestQueue(getActivity());
        tv_apply= mMainView.findViewById(R.id.tv_apply);
        spinnertype= mMainView.findViewById(R.id.spinnertype);
        datemonthly= mMainView.findViewById(R.id.datemonthly);
        datelyt= mMainView.findViewById(R.id.datelyt);
        mTxtMonthly=mMainView.findViewById(R.id.mTxtMonthly);
        mTxtProductionDatesSelected=mMainView.findViewById(R.id.mTxtProductionDatesSelected);
        mLytFilterLyt = mMainView.findViewById(R.id.filterlyt);

        lst_viewbalancesheet = mMainView.findViewById(R.id.lst_viewbalancesheet);
        mLytFilterLyt.setVisibility(View.GONE);
        mImv_Filter=getActivity().findViewById(R.id.filtericon);
        //mImv_Filter = mMainView.findViewById(R.id.imv_filter);

        mImv_Filter.setVisibility(View.VISIBLE);

        datelyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mPreviousYear = getCalculatedDate("yyyy-MM-dd", -20);

                Calendar calendar = Calendar.getInstance();
                String[] mstrDate = mPreviousYear.split("-");
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTxtProductionDatesSelected.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);

                    }
                }, Integer.parseInt(mstrDate[0]), Integer.parseInt(mstrDate[1]), Integer.parseInt(mstrDate[2]));
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.getDatePicker().setMaxDate(System.currentTimeMillis());
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

                DatePickerDialog dp = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTxtMonthly.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);

                    }
                }, Integer.parseInt(mstrDate[0]), Integer.parseInt(mstrDate[1]), Integer.parseInt(mstrDate[2]));
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.show();

            }
        });

        mRecyclerbalancesheet = mMainView.findViewById(R.id.lst_viewbalancesheet);
        mRecyclerbalancesheet.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mImv_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLytFilterLyt.isShown()) {
                    mLytFilterLyt.setVisibility(View.GONE);
                } else {
                    mLytFilterLyt.setVisibility(View.VISIBLE);
                }


            }
        });
        wsCallGetBalancesheetHistory();

        tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("balance_sheet_filter"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mLytFilterLyt.setVisibility(View.GONE);

                        allreceipy.clear();

                        Log.d("adasds", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Product product = new Product();
                                product.setProductid(jsonObject1.getString("transaction_id"));
                                product.setProductname(jsonObject1.getString("type"));
                                product.setOfferimg(jsonObject1.getString("amount"));
                                product.setRating(jsonObject1.getString("status"));
                                product.setDate(jsonObject1.getString("date"));


                                allreceipy.add(product);


                            }
                            Log.d("dsdd", String.valueOf(allreceipy.size()));

                            adapter = new MyBalancesheetAdapter(allreceipy, getActivity());
                            lst_viewbalancesheet.setLayoutManager(new LinearLayoutManager(getActivity()));
                            lst_viewbalancesheet.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {


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
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        } else {

                        }

                    }
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(getActivity()));
                        param.put("type", transactiontype);

                        param.put("from_date", mTxtProductionDatesSelected.getText().toString());
                        param.put("to_date", mTxtMonthly.getText().toString());

                        return param;
                    }


                };

                requestQueue.add(stringRequest);


            }
        });
    }


    public static String getCalculatedDate(String dateFormat, int YEARS) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.YEAR, YEARS);
        return s.format(new Date(cal.getTimeInMillis()));
    }


    /**
     * Api call
     */
    private void wsCallGetBalancesheetHistory() {


        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("balance_sheet"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                allreceipy.clear();

                Log.d("adasds", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Product product = new Product();
                        product.setProductid(jsonObject1.getString("transaction_id"));
                        product.setProductname(jsonObject1.getString("type"));
                        product.setOfferimg(jsonObject1.getString("amount"));
                        product.setRating(jsonObject1.getString("status"));
                        product.setDate(jsonObject1.getString("date"));


                        allreceipy.add(product);


                    }
                    Log.d("dsdd", String.valueOf(allreceipy.size()));

                    adapter = new MyBalancesheetAdapter(allreceipy, getActivity());
                    lst_viewbalancesheet.setLayoutManager(new LinearLayoutManager(getActivity()));
                    lst_viewbalancesheet.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.d("dfdsfdfdf",volleyError.toString());


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
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                } else {

                }

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));

                return param;
            }


        };

        requestQueue.add(stringRequest);



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.d("fdfdgdfgf", String.valueOf(position));





        if(position==1){
            transactiontype="Purchases";

        }
        else if(position==2){
            transactiontype="Sales";
        }
        else if(position==3){
            transactiontype="Subscription";

        }
        else if(position==4){
            transactiontype="Donation Reservation";

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

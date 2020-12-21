package com.app.fr.fruiteefy.user_picorear;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
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
import com.app.fr.fruiteefy.Util.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddtogardenPicoActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{


    Spinner spinnersubcat,spinnercat;
    Button addtogarden;
    RatingBar one,two,three;
    RequestQueue requestQueue,requestQueue1;
    StringRequest stringRequest,stringRequest1;
    String category_id="",subcategory_id="";
    JSONArray result,result1;
    ArrayList<String> category=new ArrayList<>();
    ArrayList<String> subcategory=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtogarden);
        setTitle("Add to garden");


        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        spinnersubcat=findViewById(R.id.spinnersubcat);
        spinnercat=findViewById(R.id.spinnercat);
        addtogarden=findViewById(R.id.addtogarden);





        requestQueue= Volley.newRequestQueue(this);
        requestQueue1= Volley.newRequestQueue(this);

        stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_category"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("dsdsddd",response);

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    result = jsonObject.getJSONArray("catlist");
                    getCatrName(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dsdsddd",error.toString());

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(AddtogardenPicoActivity.this));
                return param;
            }

        };


        requestQueue.add(stringRequest);


        spinnercat.setOnItemSelectedListener(this);
        spinnersubcat.setOnItemSelectedListener(this);


        addtogarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(category_id.equals("")){
                    Toast.makeText(AddtogardenPicoActivity.this, getResources().getString(R.string.selectcat), Toast.LENGTH_SHORT).show();
                }
                else if(subcategory_id.equals("")){
                    Toast.makeText(AddtogardenPicoActivity.this, getResources().getString(R.string.selectsubcat), Toast.LENGTH_SHORT).show();
                }
                else{

                    RequestQueue  requestQueue3=Volley.newRequestQueue(AddtogardenPicoActivity.this);
                    StringRequest stringRequest3=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("addgarden"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("dsdfdfdfdfdd",response);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if(jsonObject.getString("status").equals("1")) {

                                    Intent intent = new Intent(AddtogardenPicoActivity.this, UserPicorearHomeActivity.class);
                                    intent.putExtra("type","gardenadd");
                                    startActivity(intent);
                                }
                                Toast.makeText(AddtogardenPicoActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("dsdfdfdfdfdd",error.toString());
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {


                            Map<String, String> param = new HashMap<>();
                            param.put("user_id", PrefManager.getUserId(AddtogardenPicoActivity.this));
                            param.put("cat_id",category_id);
                            param.put("subcat_id",subcategory_id );
                            return param;
                        }

                    };
                    requestQueue3.add(stringRequest3);

                }
            }
        });

    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("sdsfggfgfgfgfgddd", String.valueOf(adapterView.getId()));
        Log.d("sdsfggfgfgfgfgddd", String.valueOf(   R.id.spiner_category));

        if(adapterView.getId() == R.id.spinnercat)
        {

            spinnersubcat.setSelection(0);
            String selectedItem = adapterView.getItemAtPosition(i).toString();

            Log.d("kjjjjjkkjkjjk", String.valueOf(i));
            if (selectedItem.equals(getResources().getString(R.string.category))) {
                category_id = "";
            } else {
                category_id = getCatId(i - 1);

                requestQueue1 = Volley.newRequestQueue(this);
                stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_subcategory"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("dsdsddd", response);

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
                        param.put("user_id", PrefManager.getUserId(AddtogardenPicoActivity.this));
                        param.put("cat_id", category_id);
                        return param;
                    }

                };


                requestQueue1.add(stringRequest1);
            }
            }
        else if(adapterView.getId() == R.id.spinnersubcat)
        {


            String selectedItem = adapterView.getItemAtPosition(i).toString();
            if (selectedItem.equals(getResources().getString(R.string.subcategory))) {
                subcategory_id = "";
            } else {
                subcategory_id = getsubCatId(i - 1);
            }
        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private  void getCatrName(JSONArray j)  {
        try{
            category.add(getResources().getString(R.string.category));
            for (int i = 0; i <= j.length(); i++) {
                JSONObject json = j.getJSONObject(i);
                category.add(json.getString("cat_name"));

            }
        }catch(JSONException e){

        }
        spinnercat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, category));

    }


    private  void getSubCatrName(JSONArray j)  {
        try{
            subcategory.add(getResources().getString(R.string.subcategory));
            for (int i = 0; i <= j.length(); i++) {
                JSONObject json = j.getJSONObject(i);
                subcategory.add(json.getString("subcat_name"));
                Log.d("sdsdsddsd", String.valueOf(subcategory));

            }
        }catch(JSONException e){

        }
        spinnersubcat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, subcategory));

    }



    private String getCatId(int position){
        String Catid="";
        try {
            JSONObject json = result.getJSONObject(position);

            Catid = json.getString("cat_id");
            Log.d("sdsdsd",Catid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return Catid;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private String getsubCatId(int position){
        String subCatid="";
        try {
            JSONObject json = result1.getJSONObject(position);

             subCatid = json.getString("subcat_id");
            Log.d("sdsdsd",subCatid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return subCatid;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

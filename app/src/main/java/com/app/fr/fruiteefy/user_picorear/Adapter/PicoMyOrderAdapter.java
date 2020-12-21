package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.app.fr.fruiteefy.Chat.MessageActivity;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_picorear.OrderpicoDetailActivity;
import com.app.fr.fruiteefy.user_picorear.PicoOrderActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PicoMyOrderAdapter extends RecyclerView.Adapter<PicoMyOrderAdapter.MyViewHolder> {

    private ArrayList<Product> arrayList=new ArrayList<>();
    private Context context;


    public PicoMyOrderAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public PicoMyOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_picomyorder, viewGroup, false);

        return new PicoMyOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PicoMyOrderAdapter.MyViewHolder myViewHolder, final int i) {
        final Product product=arrayList.get(i);
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(product.getDate());
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (product.getValidationstatus().equalsIgnoreCase("0") && product.getDeliverystatus().equalsIgnoreCase("0")){
            myViewHolder.validate.setEnabled(true);
            myViewHolder.validate.setFocusable(true);
            myViewHolder.validate.setCursorVisible(true);
            myViewHolder.validate.setAlpha(1f);

            myViewHolder.validate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue requestQueue= Volley.newRequestQueue(context);

                    StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("status_validate"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                context.startActivity(new Intent(context, PicoOrderActivity.class));

                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, "An error occured", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> param=new HashMap<>();
                            param.put("order_id",product.getProductid());
                            param.put("user_id", PrefManager.getUserId(context));
                            return param;
                        }
                    };

                    requestQueue.add(stringRequest);

                }
            });
        }
        else {
            myViewHolder.validate.setEnabled(false);
            myViewHolder.validate.setFocusable(false);
            myViewHolder.validate.setCursorVisible(false);
            myViewHolder.validate.setKeyListener(null);
            myViewHolder.validate.setAlpha(0.5f);
        }


        if(product.getStatus().equalsIgnoreCase("New")){

            myViewHolder.cancel.setEnabled(true);
            myViewHolder.cancel.setFocusable(true);
            myViewHolder.cancel.setCursorVisible(true);
            myViewHolder.cancel.setAlpha(1f);

            myViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue requestQueue= Volley.newRequestQueue(context);

                    StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("cancel_order"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                context.startActivity(new Intent(context, PicoOrderActivity.class));

                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, "An error occured", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> param=new HashMap<>();
                            param.put("order_id",product.getProductid());
                            param.put("user_id", PrefManager.getUserId(context));
                            param.put("cancelby","seller");
                            return param;
                        }
                    };

                    requestQueue.add(stringRequest);




                }
            });
        }
        else{

            myViewHolder.cancel.setEnabled(false);
            myViewHolder.cancel.setFocusable(false);
            myViewHolder.cancel.setCursorVisible(false);
            myViewHolder.cancel.setKeyListener(null);
            myViewHolder.cancel.setAlpha(0.5f);

        }


     myViewHolder.ordernumber.setText(context.getResources().getString(R.string.command)+"-"+product.getProductid());
     myViewHolder.dateoforder.setText(product.getDate());
     myViewHolder.orderby.setText(product.getFirstname()+" "+product.getLastname());


        if(product.getValidationstatus().equals("1") && product.getDeliverystatus().equals("0")){
            myViewHolder.status.setText("Validée");
        }else {

            if (product.getStatus().equalsIgnoreCase("New")) {
                myViewHolder.status.setText(context.getResources().getString(R.string.waitingforvalidation));
            } else if (product.getStatus().equalsIgnoreCase("Canceled")) {
                myViewHolder.status.setText(context.getResources().getString(R.string.canceled));
            } else if (product.getStatus().equalsIgnoreCase("Delivered")) {
                myViewHolder.status.setText(context.getResources().getString(R.string.delivered));
            }
            else if (product.getStatus().equalsIgnoreCase("Collected")) {
                myViewHolder.status.setText("Livré/Récupéré");
            }
            else {
                myViewHolder.status.setText(product.getStatus());
            }
        }

     myViewHolder.totalorder.setText(product.getValue()+" "+context.getResources().getString(R.string.currency));

     myViewHolder.sendmsg.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(context, MessageActivity.class);

             intent.putExtra("token",product.getToken());
             intent.putExtra("receiverid",product.getUserid());
             intent.putExtra("name",product.getFirstname()+" "+product.getLastname());
             context.startActivity(intent);
         }
     });


     myViewHolder.stock.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent=new Intent(context, OrderpicoDetailActivity.class);
             intent.putExtra("order_id",product.getProductid());
             context.startActivity(intent);
         }
     });





    }

    @Override
    public int getItemCount() {
        Log.d("dfdfds", String.valueOf(arrayList.size()));

        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView ordernumber,dateoforder,totalorder,stock,orderby,status,sendmsg,cancel,validate;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ordernumber=itemView.findViewById(R.id.ordernumber);
            sendmsg=itemView.findViewById(R.id.sendmsg);
            cancel=itemView.findViewById(R.id.cancel);
            validate=itemView.findViewById(R.id.validate);
            dateoforder=itemView.findViewById(R.id.dateoforder);
            totalorder=itemView.findViewById(R.id.totalorder);
            stock=itemView.findViewById(R.id.stock);
            orderby=itemView.findViewById(R.id.orderby);
            status=itemView.findViewById(R.id.status);



        }
    }


    public void setFilter(List<Product> countryModels) {
        arrayList = new ArrayList<>();
        arrayList.addAll(countryModels);
        notifyDataSetChanged();
    }


}

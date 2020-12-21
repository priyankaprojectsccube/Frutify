package com.app.fr.fruiteefy.user_antigaspi.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationAntiAdapter extends RecyclerView.Adapter<NotificationAntiAdapter.MyViewHolder> {

    private ArrayList<Product> arrayList=new ArrayList<>();
    private Context context;


    public NotificationAntiAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public NotificationAntiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_notilayout, viewGroup, false);

        return new NotificationAntiAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAntiAdapter.MyViewHolder myViewHolder, final int i) {
        final Product items=arrayList.get(i);


        if(items.getReadstatus().equals("0")){
            myViewHolder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.notificationbackground));

        }
        if(items.getReadstatus().equals("1")){
            myViewHolder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));

        }

        myViewHolder.txtnoti.setText(items.getProductname());
        myViewHolder.date.setText(items.getDate());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, UserAntigaspiHomeActivity.class));
               if(items.getReadstatus().equals("0")) {
                   RequestQueue requestQueue = Volley.newRequestQueue(context);
                   StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("change_notifi_status"), new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {

                           Log.d("dfdf",response);
                           Log.d("dfdf",response);
                           try {
                               JSONObject jsonObject=new JSONObject(response);

                               if(jsonObject.getString("status").equals("1")){
                                   myViewHolder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {

                       }
                   }) {

                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {

                           Map<String, String> param = new HashMap<>();
                           param.put("noti_id", items.getOfferid());
                           param.put("read_status", "1");
                           return param;
                       }
                   };
                   requestQueue.add(stringRequest);

               }
            }
        });



    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txtnoti,date;
        CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtnoti=itemView.findViewById(R.id.txtnoti);
            date=itemView.findViewById(R.id.date);
            cardView=itemView.findViewById(R.id.cardview);

        }
    }




}


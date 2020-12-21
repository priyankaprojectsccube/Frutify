package com.app.fr.fruiteefy.user_antigaspi.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.PositionInterface;
import com.app.fr.fruiteefy.Util.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ManagemyofferAdapter extends RecyclerView.Adapter<ManagemyofferAdapter.MyViewHolder> {
    ArrayList<Product> allProductPojos;
    Context mctx;
    RequestQueue requestQueue;
    private PositionInterface positionInterface;

    public ManagemyofferAdapter(ArrayList<Product> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    public ManagemyofferAdapter(ArrayList<Product> allProductPojos, Context mctx, PositionInterface positionInterface) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
        this.positionInterface = positionInterface;
    }

    @NonNull
    @Override
    public ManagemyofferAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_managemyoffer, parent, false);

        return new ManagemyofferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagemyofferAdapter.MyViewHolder holder, int position) {
        final Product items = allProductPojos.get(position);

        Picasso.with(mctx).load("https://www.fruiteefy.fr/dev/uploads/offer/" + items.getProductimg()).into(holder.img);

        holder.offerno.setText(" " + items.getOfferid());
        holder.offertype.setText(" " + items.getProductname());

        if(items.getOffertype().equals("1")) {
            holder.status.setText(" "+mctx.getResources().getString(R.string.available));
        }

        else{
            holder.status.setText(" "+mctx.getResources().getString(R.string.reserve));
        }

        holder.location.setText(" " + items.getOfferPlace());
        holder.price.setText(" " + items.getPrice());
        holder.quantity.setText(" " + items.getStock());
        holder.mTxtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionInterface.onClickWithFlag(position, 1);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionInterface.onClickWithFlag(position, 2);

            }
        });
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                requestQueue = Volley.newRequestQueue(mctx);
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("offerdelete"), new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        //   Log.d("ffgfdg",response);
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        //   Log.d("ffgfdg",error.toString());
//
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> param = new HashMap<>();
//                        param.put("offer_id", items.getOfferid());
//                        param.put("user_id", PrefManager.getUserId(mctx));
//
//                        return param;
//                    }
//                };
//                requestQueue.add(stringRequest);
//            }
//        });

        if (items.getOffertype().equals("1")) {
            holder.btn.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        LinearLayout btn;
        TextView texttitle, offerno, offertype, status, location, quantity, price, delete, mTxtEdit;
        Button action;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            texttitle = (TextView) view.findViewById(R.id.texttitle);
            offerno = (TextView) view.findViewById(R.id.offerno);
            offertype = (TextView) view.findViewById(R.id.offertype);
            status = (TextView) view.findViewById(R.id.status);
            delete = (TextView) view.findViewById(R.id.delete);

            location = (TextView) view.findViewById(R.id.location);
            quantity = (TextView) view.findViewById(R.id.quantity);
            price = (TextView) view.findViewById(R.id.price);
            btn = (LinearLayout) view.findViewById(R.id.btn);
            mTxtEdit = view.findViewById(R.id.edit);

        }
    }

    public void setFilter(List<Product> countryModels) {
        allProductPojos = new ArrayList<>();
        allProductPojos.addAll(countryModels);
        notifyDataSetChanged();
    }

}


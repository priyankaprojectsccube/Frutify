package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.app.fr.fruiteefy.Util.Product;

import com.app.fr.fruiteefy.user_client.products.ProductDetailsActivity;
import com.app.fr.fruiteefy.user_picorear.AddOfferActivity;
import com.app.fr.fruiteefy.user_picorear.PicoOfferDetailActivity;
import com.app.fr.fruiteefy.user_picorear.UserPicorearHomeActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PicoOfferAdapter extends RecyclerView.Adapter<PicoOfferAdapter.MyViewHolder> {

    private ArrayList<Product> arrayList=new ArrayList<>();
    private Context context;


    public PicoOfferAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public PicoOfferAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_pico_offer, viewGroup, false);

        return new PicoOfferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PicoOfferAdapter.MyViewHolder myViewHolder, final int i) {
        final Product product=arrayList.get(i);

        if(!product.getProductimg().equals("null")){
            Picasso.with(context).load(product.getProductimg()).into(myViewHolder.imgveg);
        }
        //myViewHolder.tv_distance.setText("");

//        myViewHolder.addr.setText(product.getProductname().concat(" "+context.getResources().getString(R.string.from).concat(" "+ PrefManager
//                .getFirstName(context).concat(" "+PrefManager.getLastName(context)).concat(" "+context.getResources().getString(R.string.locatedat)+" "+product.getOfferPlace()))));
      //  myViewHolder.unit.setText(product.getWeight()+" "+product.getUnit());
//        if(product.getOffertype().equals("1")){
//            myViewHolder.status.setText(context.getResources().getString(R.string.don));
//        }
//        else {
//            myViewHolder.status.setText(product.getPrice().concat(" €"));
//        }
        if(product.getStatusai().equalsIgnoreCase("1"))
        {
            myViewHolder.status.setText("Activé/En Ligne");
            myViewHolder.status.setTextColor(Color.parseColor("#73c586"));
        }
        else if(product.getStatusai().equalsIgnoreCase("0"))
        {
            myViewHolder.status.setText("Désactivé/ En cours de modération");
            myViewHolder.status.setTextColor(Color.parseColor("#DE4B39"));
        }

        myViewHolder.title.setText(product.getProductname());
Log.d("getoffer",product.getOffertype());
        if(product.getOffertype().equals("Vente") || product.getOffertype().equals("Vente(Echange possible)")){
            myViewHolder.price.setVisibility(View.VISIBLE);
            myViewHolder.available.setVisibility(View.VISIBLE);
            myViewHolder.offertype.setVisibility(View.GONE);
            myViewHolder.price.setText(product.getPrice().concat(" €")+" ("+product.getWeight()+" "+(product.getUnit())+")");



            myViewHolder.available.setText(product.getStock()+" Article(s) disponible(s)");
        }else{
            myViewHolder.price.setVisibility(View.GONE);
            myViewHolder.available.setVisibility(View.GONE);
             myViewHolder.offertype.setVisibility(View.VISIBLE);
             myViewHolder.offertype.setText(product.getOffertype());
        }

//        if(product.getAvailable().equals("1")){
//            myViewHolder.green.setVisibility(View.VISIBLE);
//            myViewHolder.red.setVisibility(View.GONE);
//        }
//        else if(product.getAvailable().equals("2")){
//            myViewHolder.red.setVisibility(View.VISIBLE);
//            myViewHolder.green.setVisibility(View.GONE);
//        }

        myViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, AddOfferActivity.class);
                mIntent.putExtra("Product_id", arrayList.get(i).getProductid());
                mIntent.putExtra("From", "Update");
                context.startActivity(mIntent);


            }
        });

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.layout_deletconfirmationorder);
                dialog.setCancelable(false);
                dialog.show();

                TextView yes,no;
                yes=dialog.findViewById(R.id.yes);
                no=dialog.findViewById(R.id.no);

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        CustomUtil.ShowDialog(context,false);

                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("delete_product"), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                CustomUtil.DismissDialog(context);
                                Log.d("dsfsdfd",response);
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    if(jsonObject.getString("status").equals("1")){
                                      context.startActivity(new Intent(context, UserPicorearHomeActivity.class));
                                    }
                                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                //   Log.d("ffgfdg",response);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                CustomUtil.DismissDialog(context);
                                //   Log.d("ffgfdg",error.toString());

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();
                                param.put("product_id",  arrayList.get(i).getProductid());
                                param.put("user_id", PrefManager.getUserId(context));

                                Log.d("ffgf",arrayList.get(i).getProductid());



                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);

                    }
                });





            }
        });

        myViewHolder.cv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productid",product.getProductid());
                context.startActivity(intent);

//                Intent intent=new Intent(context, PicoOfferDetailActivity.class);
//                intent.putExtra("offerdata",product.getProductid());
//                context.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        Log.d("dfdfds", String.valueOf(arrayList.size()));

        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgveg,green,red;
        TextView tv_distance,edit,delete,

        //unit,
        price,title,addr,status,available,offertype;
        RatingBar ratingBar;
        CardView cv_main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            delete=itemView.findViewById(R.id.delete);
            edit=itemView.findViewById(R.id.edit);
            imgveg=itemView.findViewById(R.id.imgveg);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            tv_distance=itemView.findViewById(R.id.distance);
            green=itemView.findViewById(R.id.green);
            red=itemView.findViewById(R.id.red);
            offertype = itemView.findViewById(R.id.offertype);
            //   unit=itemView.findViewById(R.id.unit);
            price=itemView.findViewById(R.id.price);
            available = itemView.findViewById(R.id.available);

            cv_main=itemView.findViewById(R.id.cv_main);
            status=itemView.findViewById(R.id.status);

        }
    }



    public void setFilter(List<Product> countryModels) {
        arrayList = new ArrayList<>();
        arrayList.addAll(countryModels);
        notifyDataSetChanged();
    }

}

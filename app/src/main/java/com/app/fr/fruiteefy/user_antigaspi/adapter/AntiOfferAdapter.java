package com.app.fr.fruiteefy.user_antigaspi.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.app.fr.fruiteefy.user_antigaspi.AntiAddofferActivity;
import com.app.fr.fruiteefy.user_antigaspi.AntiOfferDetailActivity;
import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;
import com.app.fr.fruiteefy.user_picorear.DonationaroundmedetailActivity;
import com.app.fr.fruiteefy.user_picorear.UserPicorearHomeActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.app.fr.fruiteefy.Util.BaseUrl.ANTIGASPIURL;

public class AntiOfferAdapter extends RecyclerView.Adapter<AntiOfferAdapter.MyViewHolder> {

    private ArrayList<Product> arrayList=new ArrayList<>();
    private Context context;



    public AntiOfferAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public AntiOfferAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_anti_offer, viewGroup, false);

        return new AntiOfferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AntiOfferAdapter.MyViewHolder myViewHolder, final int i) {
        final Product product=arrayList.get(i);

        if(!product.getProductimg().equals("null")){
            Picasso.with(context).load(ANTIGASPIURL.concat(product.getProductimg())).into(myViewHolder.imgveg);
        }

        Log.d("sdadad",product.getOffertype());
        myViewHolder.tv_distance.setText("");
       myViewHolder.addr.setText(product.getAddress());
        myViewHolder.type_dstxt.setText(product.getType()+" "+product.getNom());
//        Log.d("itemaddress",product.getAddress());
  //      myViewHolder.addr.setText(product.getProductname().concat(" "+context.getResources().getString(R.string.from).concat(" "+PrefManager
  //      .getFirstName(context).concat(" "+PrefManager.getLastName(context)).concat(" "+context.getResources().getString(R.string.locatedat)+" "+product.getOfferPlace()))));
      //  myViewHolder.unit.setText(product.getWeight()+" "+product.getUnit());
        if(product.getOffertype().equals("1")){
            myViewHolder.status.setText(context.getResources().getString(R.string.don));
        }

        else if(product.getOffertype().equals("3")){
            myViewHolder.status.setText(context.getResources().getString(R.string.exchange));
        }
        else {
            myViewHolder.status.setText(product.getPrice().concat(" €"));
        }
        myViewHolder.title.setText(product.getProductname());
         if(product.getAvailable().equals("1")){
             myViewHolder.availabletxt.setText("Disponible");
             myViewHolder.green.setVisibility(View.VISIBLE);
             myViewHolder.red.setVisibility(View.GONE);
        }
        else if(product.getAvailable().equals("2")){

             myViewHolder.availabletxt.setText("Indisponible");
             myViewHolder.red.setVisibility(View.VISIBLE);
             myViewHolder.green.setVisibility(View.GONE);
        }


        if (product.getOffertype().equals("1")) {
            myViewHolder.btn.setVisibility(View.VISIBLE);
        }

        if (product.getOffertype().equals("3")) {
            myViewHolder.btn.setVisibility(View.VISIBLE);
        }


        myViewHolder.cv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Product product1=arrayList.get(i);
                Intent intent=new Intent(context, DonationaroundmedetailActivity.class);
                intent.putExtra("offerdata",product1);
                context.startActivity(intent);
//                Product product1=arrayList.get(i);
//                Intent intent=new Intent(context, AntiOfferDetailActivity.class);
//                intent.putExtra("offerdata",product1);
//                context.startActivity(intent);
            }
        });


        myViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(context, AntiAddofferActivity.class);
                mIntent.putExtra("Flag", "yes");
                mIntent.putExtra("Data", arrayList.get(i));
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
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("offerdelete"), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("offerdelete",response);
                                CustomUtil.DismissDialog(context);
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    if(jsonObject.getString("status").equals("1")){
                                        Intent intent=new Intent(context, UserAntigaspiHomeActivity.class);
                                        intent.putExtra("type","offer");
                                       context.startActivity(intent);
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
                                param.put("offer_id", arrayList.get(i).getOfferid());
                                param.put("user_id", PrefManager.getUserId(context));

                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);


                    }
                });



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
        LinearLayout btn;
        TextView edit,delete;
        TextView tv_distance,
                //unit,
                price,title,addr,status,availabletxt,type_dstxt;
        RatingBar ratingBar;
        CardView cv_main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            type_dstxt= itemView.findViewById(R.id.type_dstxt);
            title=itemView.findViewById(R.id.title);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);
            imgveg=itemView.findViewById(R.id.imgveg);
            btn=itemView.findViewById(R.id.btn);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            tv_distance=itemView.findViewById(R.id.distance);
            green=itemView.findViewById(R.id.green);
            availabletxt = itemView.findViewById(R.id.availabletxt);
            red=itemView.findViewById(R.id.red);
         //   unit=itemView.findViewById(R.id.unit);
            price=itemView.findViewById(R.id.price);
            addr=itemView.findViewById(R.id.addr);
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

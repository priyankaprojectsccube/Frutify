package com.app.fr.fruiteefy.user_picorear.Adapter;

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
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_picorear.PicoBookingDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.app.fr.fruiteefy.Util.BaseUrl.ANTIGASPIURL;

public class PicoBookingAdapter extends RecyclerView.Adapter<PicoBookingAdapter.MyViewHolder> {

    private ArrayList<Product> arrayList=new ArrayList<>();
    private Context context;


    public PicoBookingAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public PicoBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_anti_offer, viewGroup, false);

        return new PicoBookingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PicoBookingAdapter.MyViewHolder myViewHolder, final int i) {
        final Product product=arrayList.get(i);

        if(!product.getProductimg().equals("null")){
            Picasso.with(context).load(ANTIGASPIURL.concat(product.getProductimg())).into(myViewHolder.imgveg);
        }
        myViewHolder.tv_distance.setText("");
        myViewHolder.addr.setText(product.getDesc());
//        myViewHolder.unit.setText(product.getWeight()+" "+product.getUnit());
//        if(product.getOffertype().equals("1")){
//            myViewHolder.status.setText(context.getResources().getString(R.string.don));
//        }
//        else {
//            myViewHolder.status.setText(product.getPrice().concat(" €"));
//        }
        myViewHolder.title.setText(product.getProductname());
         if(product.getAvailable().equals("Reserved")){
             myViewHolder.green.setVisibility(View.VISIBLE);
             myViewHolder.red.setVisibility(View.GONE);
        }
        else if(product.getAvailable().equals("Collected")){
             myViewHolder.red.setVisibility(View.VISIBLE);
             myViewHolder.green.setVisibility(View.GONE);
        }

        myViewHolder.cv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Product product1=arrayList.get(i);
                Intent intent=new Intent(context, PicoBookingDetailActivity.class);
                intent.putExtra("offerdata",product1);
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

        private ImageView imgveg,green,red;
        TextView tv_distance,
                //unit,
                price,title,addr,status;
        RatingBar ratingBar;
        CardView cv_main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            imgveg=itemView.findViewById(R.id.imgveg);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            tv_distance=itemView.findViewById(R.id.distance);
            green=itemView.findViewById(R.id.green);
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

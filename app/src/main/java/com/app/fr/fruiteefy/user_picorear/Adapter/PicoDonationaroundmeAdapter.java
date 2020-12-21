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
import com.app.fr.fruiteefy.Util.ChooseHellperClass;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_picorear.DonationaroundmedetailActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class PicoDonationaroundmeAdapter extends RecyclerView.Adapter<PicoDonationaroundmeAdapter.MyViewHolder> {

    private ArrayList<Product> arrayList=new ArrayList<>();
    private Context context;



    public  PicoDonationaroundmeAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;


    }


    @NonNull
    @Override
    public PicoDonationaroundmeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_pico_donationaroundme, viewGroup, false);

        return new PicoDonationaroundmeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PicoDonationaroundmeAdapter.MyViewHolder myViewHolder, final int i) {
        final Product product=arrayList.get(i);




        if(!product.getProductimg().equals("null")){
            Picasso.with(context).load(product.getProductimg()).into(myViewHolder.imgveg);
        }

        myViewHolder.addr.setText(product.getAddress());
        Log.d("itemaddress",product.getAddress());
       // myViewHolder.unit.setText(product.getWeight()+" "+product.getUnit());


//        if(product.getOffertype().equals("1")){
//            myViewHolder.status.setText(context.getResources().getString(R.string.don));
//        }
//
//        else if(product.getOffertype().equals("3")){
//            myViewHolder.status.setText(context.getResources().getString(R.string.exchange));
//        }
//        else {
//            myViewHolder.status.setText(product.getPrice().concat(" €"));
//        }

        myViewHolder.weight.setText(product.getWeight());
        myViewHolder.title.setText(product.getProductname());
        myViewHolder.type_dstxt.setText(product.getType()+" "+product.getNom());
       // myViewHolder.tv_distance.setText(product.get);
         if(product.getAvailable().equalsIgnoreCase("Available")){
myViewHolder.availabletxt.setText("Disponible");
             myViewHolder.green.setVisibility(View.VISIBLE);
             myViewHolder.red.setVisibility(View.GONE);
        }
        else if(product.getAvailable().equals("Reserved")){
             myViewHolder.availabletxt.setText("Indisponible");
             myViewHolder.red.setVisibility(View.VISIBLE);
             myViewHolder.green.setVisibility(View.GONE);
        }

        myViewHolder.cv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Product product1=arrayList.get(i);
                Intent intent=new Intent(context, DonationaroundmedetailActivity.class);
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
                weight,title,addr,status,type_dstxt,availabletxt;
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
            type_dstxt = itemView.findViewById(R.id.type_dstxt);
         //   unit=itemView.findViewById(R.id.unit);
            weight=itemView.findViewById(R.id.weight);
            addr=itemView.findViewById(R.id.addr);
            cv_main=itemView.findViewById(R.id.cv_main);
            status=itemView.findViewById(R.id.status);
            availabletxt= itemView.findViewById(R.id.availabletxt);

        }
    }



    public void setFilter(List<Product> countryModels) {
        arrayList = new ArrayList<>();
        arrayList.addAll(countryModels);
        notifyDataSetChanged();
    }

}

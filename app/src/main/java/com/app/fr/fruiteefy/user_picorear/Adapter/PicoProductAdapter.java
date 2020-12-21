package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_picorear.AddOfferActivity;
import com.app.fr.fruiteefy.user_picorear.PicoOfferDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PicoProductAdapter extends RecyclerView.Adapter<PicoProductAdapter.MyViewHolder> {

    private ArrayList<Product> arrayList=new ArrayList<>();
    private Context context;

    public PicoProductAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_picoproduct, viewGroup, false);

        return new PicoProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Product product=arrayList.get(i);
        myViewHolder.desc.setText(product.getDesc());


        Log.d("dsfdsfd",product.getStatus());

        if(product.getStatus().equals("1")){
            myViewHolder.green.setVisibility(View.VISIBLE);
        }
        else{
            myViewHolder.red.setVisibility(View.VISIBLE);
        }

        myViewHolder.product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getStatus().equals("0")){



                    Intent mIntent = new Intent(context, AddOfferActivity.class);
                    mIntent.putExtra("From", "Add");
                    context.startActivity(mIntent);

                }

                else if(product.getStatus().equals("1")){

                    Intent intent=new Intent(context, PicoOfferDetailActivity.class);
                    intent.putExtra("offerdata",product.getProductid());
                    context.startActivity(intent);
                }
            }
        });

        if(!product.getOfferimg().equals("null")) {
            Picasso.with(context)
                    .load(product.getOfferimg())
                    .fit()
                    .into(myViewHolder.offer_img);
        }


        if(!product.getProductimg().equals("null")) {
            Picasso.with(context)
                    .load(product.getProductimg())
                    .fit()
                    .into(myViewHolder.product_img);
     }



    }

    @Override
    public int getItemCount() {
        Log.d("dfdfds", String.valueOf(arrayList.size()));

        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private ImageView  offer_img,product_img,green,red;
        TextView desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            offer_img=itemView.findViewById(R.id.offer_img);
            product_img=itemView.findViewById(R.id.product_img);
            desc=itemView.findViewById(R.id.desc);
            green=itemView.findViewById(R.id.green);
            red=itemView.findViewById(R.id.red);
        }
    }


}

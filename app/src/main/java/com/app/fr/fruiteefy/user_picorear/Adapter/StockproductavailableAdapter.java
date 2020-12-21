package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.user_client.products.ProductPojo;

import java.util.ArrayList;

public class StockproductavailableAdapter extends RecyclerView.Adapter<StockproductavailableAdapter.MyViewHolder> {

    private ArrayList<ProductPojo> arrayList=new ArrayList<>();
    private Context context;

    public StockproductavailableAdapter(ArrayList<ProductPojo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    ;




    @NonNull
    @Override
    public StockproductavailableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_product_receipt_detail, viewGroup, false);

        return new StockproductavailableAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockproductavailableAdapter.MyViewHolder myViewHolder, int i) {

        ProductPojo productPojo=arrayList.get(i);

        myViewHolder.txtone.setText(productPojo.getStock());
        myViewHolder.txttwo.setText(productPojo.getIngweight());
        myViewHolder.txtthree.setText(productPojo.getIngunit());
        myViewHolder.txtfour.setText(productPojo.getPrice());


        productPojo.getIngname();


    }

    @Override
    public int getItemCount() {
        Log.d("hjhjgj", String.valueOf(arrayList.size()));
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView txtone,txttwo,txtthree,txtfour;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtone=itemView.findViewById(R.id.txtone);
            txttwo=itemView.findViewById(R.id.txttwo);
            txtthree=itemView.findViewById(R.id.txtthree);
            txtfour=itemView.findViewById(R.id.txtfour);


        }
    }
}


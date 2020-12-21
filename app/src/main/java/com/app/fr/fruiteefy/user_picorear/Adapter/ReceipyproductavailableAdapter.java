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

public class ReceipyproductavailableAdapter extends RecyclerView.Adapter<ReceipyproductavailableAdapter.MyViewHolder> {

    private ArrayList<ProductPojo> arrayList=new ArrayList<>();
    private Context context;

    public ReceipyproductavailableAdapter(ArrayList<ProductPojo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    ;




    @NonNull
    @Override
    public ReceipyproductavailableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_receipy_detail, viewGroup, false);

        return new ReceipyproductavailableAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceipyproductavailableAdapter.MyViewHolder myViewHolder, int i) {

        ProductPojo productPojo=arrayList.get(i);

        myViewHolder.txtone.setText(productPojo.getStock());
        myViewHolder.txttwo.setText(productPojo.getPrice());
        myViewHolder.txtthree.setText(productPojo.getIngunit());



        productPojo.getIngname();


    }

    @Override
    public int getItemCount() {
        Log.d("hjhjgj", String.valueOf(arrayList.size()));
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView txtone,txttwo,txtthree;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtone=itemView.findViewById(R.id.txtone);
            txttwo=itemView.findViewById(R.id.txttwo);
            txtthree=itemView.findViewById(R.id.txtthree);



        }
    }
}


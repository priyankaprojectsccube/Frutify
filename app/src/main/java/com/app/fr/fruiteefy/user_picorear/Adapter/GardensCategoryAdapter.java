package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_client.adapter.CategoryDetailAdapter;

import java.util.ArrayList;

public class GardensCategoryAdapter extends RecyclerView.Adapter<GardensCategoryAdapter.MyViewHolder> {

    private ArrayList<Product> arrayList = new ArrayList<>();
    private Context context;



    public GardensCategoryAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public GardensCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_gardencategory, viewGroup, false);

        return new GardensCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GardensCategoryAdapter.MyViewHolder myViewHolder, int i) {


         Product product = arrayList.get(i);
        myViewHolder.title.setText(product.getProductname());


        CategoryDetailAdapter adapter1 = new CategoryDetailAdapter( product.getmArrSubList(), context);
        myViewHolder.recvewcat.setLayoutManager(new GridLayoutManager(context,3));
        myViewHolder.recvewcat.setAdapter(adapter1);






    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView title;
        RecyclerView recvewcat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            recvewcat = itemView.findViewById(R.id.recvewcat);

        }
    }
}
package com.app.fr.fruiteefy.user_client.mywallet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.Product;

import java.util.ArrayList;
import java.util.List;


public class MyBalancesheetAdapter extends RecyclerView.Adapter<MyBalancesheetAdapter.MyViewHolder> {
    ArrayList<Product> allProductPojos;
    Context mctx;


    public MyBalancesheetAdapter(ArrayList<Product> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public MyBalancesheetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_balancesheetadapteritem, parent, false);

        return new MyBalancesheetAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyBalancesheetAdapter.MyViewHolder holder, int position) {
        final Product items=allProductPojos.get(position);


     holder.tv_transactionid.setText(items.getProductid());
     holder.tv_transactiontypes.setText(items.getProductname());
     holder.tv_amount.setText(items.getOfferimg());
     holder.tv_creation.setText(items.getDate());
     holder.tv_status.setText(items.getRating());

    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_transactionid,tv_creation,tv_transactiontypes,tv_amount,tv_status;

        public MyViewHolder(View view) {
            super(view);

            tv_transactionid = (TextView) view.findViewById(R.id.tv_transactionid);
            tv_creation= (TextView) view.findViewById(R.id.tv_creation);
            tv_transactiontypes= (TextView) view.findViewById(R.id.tv_transactiontypes);
            tv_amount= (TextView) view.findViewById(R.id.tv_amount);
            tv_status= (TextView) view.findViewById(R.id.tv_status);




        }
    }


    public void setFilter(List<Product> countryModels) {
        allProductPojos = new ArrayList<>();
        allProductPojos.addAll(countryModels);
        notifyDataSetChanged();
    }

}

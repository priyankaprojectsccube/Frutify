package com.app.fr.fruiteefy.user_client.adapter;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {

    private ArrayList<Product> arrayList=new ArrayList<>();
    private Context context;


    public WalletAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public WalletAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_wallet, viewGroup, false);

        return new WalletAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.MyViewHolder myViewHolder, final int i) {
        final Product product=arrayList.get(i);
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(product.getDate());
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


     myViewHolder.ordernumber.setText(context.getResources().getString(R.string.command)+"-"+product.getProductid());
     myViewHolder.dateoforder.setText(str);
     myViewHolder.status.setText(product.getStatus());
     myViewHolder.orderby.setText(product.getName());
     myViewHolder.totalorder.setText(product.getPrice()+" "+context.getResources().getString(R.string.currency));






    }

    @Override
    public int getItemCount() {
        Log.d("dfdfds", String.valueOf(arrayList.size()));

        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView ordernumber,dateoforder,totalorder,orderby,status;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ordernumber=itemView.findViewById(R.id.ordernumber);
            dateoforder=itemView.findViewById(R.id.dateoforder);
            totalorder=itemView.findViewById(R.id.totalorder);
            orderby=itemView.findViewById(R.id.orderby);
            status=itemView.findViewById(R.id.status);



        }
    }


    public void setFilter(List<Product> countryModels) {
        arrayList = new ArrayList<>();
        arrayList.addAll(countryModels);
        notifyDataSetChanged();
    }


}

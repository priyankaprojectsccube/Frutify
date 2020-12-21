package com.app.fr.fruiteefy.user_client.mywallet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.Invoice;
import com.app.fr.fruiteefy.user_client.mywallet.InvoiceDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;


public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.MyViewHolder> {

    ArrayList<Invoice> invoices;
    Context mctx;


    public InvoiceAdapter(ArrayList<Invoice> invoices, Context mctx) {
        this.invoices = invoices;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public InvoiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_invoice, parent, false);

        return new InvoiceAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceAdapter.MyViewHolder holder, int position) {


        holder.txttitle.setText(invoices.get(position).getOrderdetail());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mctx, InvoiceDetailActivity.class);
                intent.putExtra("data", (Serializable) invoices.get(position));
                mctx.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {

        return invoices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txttitle;
        CardView card_view;

        public MyViewHolder(View view) {
            super(view);

            txttitle = (TextView) view.findViewById(R.id.txttitle);
            card_view=view.findViewById(R.id.card_view);




        }
    }



}

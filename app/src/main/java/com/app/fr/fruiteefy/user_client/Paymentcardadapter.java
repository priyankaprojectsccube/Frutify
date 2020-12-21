package com.app.fr.fruiteefy.user_client;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.OnItemClickListener;
import com.app.fr.fruiteefy.Util.cardpogo;

import java.util.ArrayList;


public class Paymentcardadapter  extends RecyclerView.Adapter<Paymentcardadapter.MyViewHolder> {
    ArrayList<cardpogo> allProductPojos;
    Context mctx;
    OnItemClickListener onItemClickListener;



    public Paymentcardadapter(ArrayList<cardpogo> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public Paymentcardadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardlayout, parent, false);

        return new Paymentcardadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Paymentcardadapter.MyViewHolder holder, int position) {
        final cardpogo items=allProductPojos.get(position);

        holder.cardno.setText(items.getCardno());

    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cardno;
        RadioButton radiobutton;


        public MyViewHolder(View view) {
            super(view);

            cardno =  view.findViewById(R.id.cardno);
            radiobutton = view.findViewById(R.id.radio);
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.OnItemClick(getAdapterPosition());
            }
        }
    }




}

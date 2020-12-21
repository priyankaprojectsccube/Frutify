package com.app.fr.fruiteefy.user_client.cart;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.PositionInterfaceforCart;
import com.app.fr.fruiteefy.user_client.cart.modelClass.MyCartModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {
    List<MyCartModel> myCartModelList;
    Context mctx;
    PositionInterfaceforCart positionInterfaceforCart;

    public MyCartAdapter(List<MyCartModel> myCartModelList, Context mctx, PositionInterfaceforCart positionInterfaceforCart) {
        this.myCartModelList = myCartModelList;
        this.mctx = mctx;
        this.positionInterfaceforCart = positionInterfaceforCart;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_my_cart, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyCartModel items = myCartModelList.get(position);
        holder.tv_product_name.setText(items.getName());
        holder.mEdtCartValue.setText(items.getQty());
        // holder.mEdtCartValue.setTag(position);
        Log.d("dsdsadd", items.getPrice());
        holder.price.setText(items.getPrice().concat(" " + mctx.getResources().getString(R.string.currency)));
        holder.subtotal.setText(items.getSubtotal().concat(" " + mctx.getResources().getString(R.string.currency)));


        holder.dltbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionInterfaceforCart.onClick(2, 0, position);

            }
        });


        Picasso.with(mctx).load(items.getImg()).into(holder.header_image);
        holder.mBtnPlus.setTag(position);
        holder.mBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                int a= Integer.parseInt(holder.mEdtCartValue.getText().toString());
                int b=Integer.parseInt(myCartModelList.get(i).getStock());
                Log.d("hhjhh",a+" "+b);
                if(a<b) {
                    myCartModelList.get(i).setQty("" + (Integer.parseInt(myCartModelList.get(i).getQty()) + 1));
                    positionInterfaceforCart.onClick(1, 2, position);
                }
            }
        });
        holder.mBtnMinus.setTag(position);
        holder.mBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = (int) v.getTag();
                if (Integer.parseInt(myCartModelList.get(i).getQty())>1) {
                    myCartModelList.get(i).setQty("" + (Integer.parseInt(myCartModelList.get(i).getQty()) - 1));
                    positionInterfaceforCart.onClick(1, 2, position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(myCartModelList.size()));
        return myCartModelList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_product_name, price, subtotal;
        ImageView header_image, dltbutton;
        Button mBtnPlus, mBtnMinus;
        EditText mEdtCartValue;

        public MyViewHolder(View view) {
            super(view);

            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            price = (TextView) view.findViewById(R.id.price);
            subtotal = (TextView) view.findViewById(R.id.subtotal);
            header_image = (ImageView) view.findViewById(R.id.iv_product_image);
            dltbutton = (ImageView) view.findViewById(R.id.dltbutton);
            mBtnMinus = view.findViewById(R.id.minusbtn);
            mBtnPlus = view.findViewById(R.id.plusbutton);
            mEdtCartValue = view.findViewById(R.id.edtcartvalue);


        }
    }


    public void setFilter(List<MyCartModel> countryModels) {
        myCartModelList = new ArrayList<>();
        myCartModelList.addAll(countryModels);
        notifyDataSetChanged();
    }
}
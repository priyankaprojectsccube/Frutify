package com.app.fr.fruiteefy.user_client.mywallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.PositionInterface;

import java.util.ArrayList;
import java.util.HashMap;


public class MyBankListAdapter extends RecyclerView.Adapter<MyBankListAdapter.MyViewHolder> {
    ArrayList<HashMap<String, String>> mCardListArr;
    Context mctx;
    PositionInterface positionInterface;

    public MyBankListAdapter(ArrayList<HashMap<String, String>> mCardListArr, Context mctx, PositionInterface positionInterface) {
        this.mCardListArr = mCardListArr;
        this.mctx = mctx;
        this.positionInterface = positionInterface;
    }

    @NonNull
    @Override
    public MyBankListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_banklistitem, parent, false);

        return new MyBankListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBankListAdapter.MyViewHolder holder, int position) {
        final HashMap<String, String> items = mCardListArr.get(position);

        holder.mTxtAccountName.setText(items.get("OwnerName"));

        if(items.get("status").equals("INACTIVE")){
            holder.mTxtStatus.setText(mctx.getResources().getString(R.string.inactive));
        }

        holder.mTxtAccountNumbar.setText(" "+items.get("account_no"));

        if (items.get("status").equalsIgnoreCase("ACTIVE")) {
            holder.mTxtStatus.setVisibility(View.GONE);
            holder.iv_delete.setVisibility(View.VISIBLE);
        } else {
            holder.mTxtStatus.setVisibility(View.VISIBLE);
            holder.iv_delete.setVisibility(View.GONE);
        }
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionInterface.onClick(position);
            }
        });

        // Picasso.with(mctx).load(items.getProductimg()).into(holder.iv_product_image);

    }

    @Override
    public int getItemCount() {

        return mCardListArr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_delete;
        private TextView mPaymentType;
        private TextView mTxtAccountName, mTxtStatus, mTxtAccountNumbar;

        public MyViewHolder(View view) {
            super(view);
            iv_delete = (ImageView) view.findViewById(R.id.delete);
            //  mPaymentType = (TextView) view.findViewById(R.id.methodtype);
            mTxtAccountName = view.findViewById(R.id.nameofholder);
            mTxtStatus = view.findViewById(R.id.status);
            mTxtAccountNumbar = view.findViewById(R.id.accountnumber);

        }
    }


}

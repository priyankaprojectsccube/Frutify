package com.app.fr.fruiteefy.user_client.mywallet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PositionInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyPaymentMethodAdapter extends RecyclerView.Adapter<MyPaymentMethodAdapter.MyViewHolder> {
    ArrayList<HashMap<String, String>> mCardListArr;
    Context mctx;
    PositionInterface positionInterface1;

    public MyPaymentMethodAdapter(ArrayList<HashMap<String, String>> mCardListArr, Context mctx, PositionInterface positionInterface1) {
        this.mCardListArr = mCardListArr;
        this.mctx = mctx;
        this.positionInterface1 = positionInterface1;
    }

    @NonNull
    @Override
    public MyPaymentMethodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_paymentmethoditem, parent, false);

        return new MyPaymentMethodAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPaymentMethodAdapter.MyViewHolder holder, int position) {
        final HashMap<String, String> items = mCardListArr.get(position);

        holder.mPaymentType.setText(items.get("CardType") + " : " + items.get("Alias"));
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
                RequestQueue requestQueue = Volley.newRequestQueue(mctx);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("deactivate_card"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Log.d("sdsadd", response);
                        try {
                            // myCartModelList.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("1")) {

                                mCardListArr.remove(position);
                                notifyItemRemoved(position);

                                Toast.makeText(mctx, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mctx, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sdsadd", error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("card_id", items.get("Id"));

                        // Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
                        return param;
                    }
                };

                requestQueue.add(stringRequest);
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
        private TextView mTxtStatus;

        public MyViewHolder(View view) {
            super(view);
            iv_delete = (ImageView) view.findViewById(R.id.delete);
            mPaymentType = (TextView) view.findViewById(R.id.methodtype);
            mTxtStatus = view.findViewById(R.id.status);
        }
    }


}

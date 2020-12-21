package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.PicoriarHelperClass;
import com.app.fr.fruiteefy.Util.PositionInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddPicoImageAdapter extends RecyclerView.Adapter<AddPicoImageAdapter.MyViewHolder> {

    private ArrayList<PicoriarHelperClass> arrayList = new ArrayList<>();
    private Context context;
    private PositionInterface positionInterface;

    public AddPicoImageAdapter(ArrayList<PicoriarHelperClass> arrayList, Context context, PositionInterface positionInterface) {

        this.arrayList = arrayList;
        this.context = context;
        this.positionInterface = positionInterface;

    }


    @NonNull
    @Override
    public AddPicoImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_item_image, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (arrayList.get(i).getmImgFrom().equalsIgnoreCase("Api")) {
            myViewHolder.mImgProduct.setTag(i);

            Log.d("dfdf",arrayList.get(i).getmImvByte());
            Picasso.with(context).load(arrayList.get(i).getmImvByte()).into(myViewHolder.mImgProduct);
//            Picasso.with(context).load(arrayList.get(i).getmImvByte()).into(new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                    int pos = (int) myViewHolder.mImgProduct.getTag();
//                    arrayList.get(pos).setmBitmap(bitmap);
//                    myViewHolder.mImgProduct.setImageBitmap(bitmap);
//                }
//
//                @Override
//                public void onBitmapFailed(Drawable errorDrawable) {
//
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                }
//            });

        } else {
            myViewHolder.mImgProduct.setImageBitmap(arrayList.get(i).getmBitmap());
        }


        myViewHolder.mImvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionInterface.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgProduct, mImvDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgProduct = itemView.findViewById(R.id.productimg);
            mImvDelete = itemView.findViewById(R.id.imvdelete);

        }
    }
}
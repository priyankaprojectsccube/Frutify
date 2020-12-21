package com.app.fr.fruiteefy.Util;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class DynamicClass {

    private Spinner mSpn_UnitMeasure;
    private EditText mEdtWightValume, mEdtPrice, mEdtNumberAvailable;
    private ImageView mImv_deleteView;

    public String getmStrID() {
        return mStrID;
    }

    public void setmStrID(String mStrID) {
        this.mStrID = mStrID;
    }

    private String mStrID;

    public TextView getmTxtUpdate() {
        return mTxtUpdate;
    }

    public void setmTxtUpdate(TextView mTxtUpdate) {
        this.mTxtUpdate = mTxtUpdate;
    }

    private TextView mTxtUpdate;
    public ImageView getmImv_deleteView() {
        return mImv_deleteView;
    }

    public void setmImv_deleteView(ImageView mImv_deleteView) {
        this.mImv_deleteView = mImv_deleteView;
    }


    public Spinner getmSpn_UnitMeasure() {
        return mSpn_UnitMeasure;
    }

    public void setmSpn_UnitMeasure(Spinner mSpn_UnitMeasure) {
        this.mSpn_UnitMeasure = mSpn_UnitMeasure;
    }

    public EditText getmEdtWightValume() {
        return mEdtWightValume;
    }

    public void setmEdtWightValume(EditText mEdtWightValume) {
        this.mEdtWightValume = mEdtWightValume;
    }

    public EditText getmEdtPrice() {
        return mEdtPrice;
    }

    public void setmEdtPrice(EditText mEdtPrice) {
        this.mEdtPrice = mEdtPrice;
    }

    public EditText getmEdtNumberAvailable() {
        return mEdtNumberAvailable;
    }

    public void setmEdtNumberAvailable(EditText mEdtNumberAvailable) {
        this.mEdtNumberAvailable = mEdtNumberAvailable;
    }

    public EditText getmEdtIngredientName() {
        return mEdtIngredientName;
    }

    public void setmEdtIngredientName(EditText mEdtIngredientName) {
        this.mEdtIngredientName = mEdtIngredientName;
    }

    public EditText getmEdtIngredientWeight() {
        return mEdtIngredientWeight;
    }

    public void setmEdtIngredientWeight(EditText mEdtIngredientWeight) {
        this.mEdtIngredientWeight = mEdtIngredientWeight;
    }

    public Spinner getmSp_UnitIngredient() {
        return mSp_UnitIngredient;
    }

    public void setmSp_UnitIngredient(Spinner mSp_UnitIngredient) {
        this.mSp_UnitIngredient = mSp_UnitIngredient;
    }

    private EditText mEdtIngredientName, mEdtIngredientWeight;
    private Spinner mSp_UnitIngredient;


}

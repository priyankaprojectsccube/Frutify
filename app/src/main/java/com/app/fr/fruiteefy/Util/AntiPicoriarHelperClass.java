package com.app.fr.fruiteefy.Util;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class AntiPicoriarHelperClass implements Serializable {
    public ArrayList<AntiPicoriarHelperClass> getmImgArr() {
        return mImgArr;
    }

    public void setmImgArr(ArrayList<AntiPicoriarHelperClass> mImgArr) {
        this.mImgArr = mImgArr;
    }

    public String getmImgId() {
        return mImgId;
    }

    public void setmImgId(String mImgId) {
        this.mImgId = mImgId;
    }

    public String getmImgFrom() {
        return mImgFrom;
    }

    public void setmImgFrom(String mImgFrom) {
        this.mImgFrom = mImgFrom;
    }

    public String getmImvByte() {
        return mImvByte;
    }

    public void setmImvByte(String mImvByte) {
        this.mImvByte = mImvByte;
    }

    private ArrayList<AntiPicoriarHelperClass> mImgArr = new ArrayList<>();
    private String mImgId, mImgFrom;
    private String mImvByte;

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    private Bitmap mBitmap;
}

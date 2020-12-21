package com.app.fr.fruiteefy.user_picorear.dataclasses;

import java.io.Serializable;

public class CategoryHelperClass implements Serializable {
    public String getmStrCatId() {
        return mStrCatId;
    }

    public void setmStrCatId(String mStrCatId) {
        this.mStrCatId = mStrCatId;
    }

    public String getmStrCatName() {
        return mStrCatName;
    }

    public void setmStrCatName(String mStrCatName) {
        this.mStrCatName = mStrCatName;
    }

    public String getmStrReceipyId() {
        return mStrReceipyId;
    }

    public void setmStrReceipyId(String mStrReceipyId) {
        this.mStrReceipyId = mStrReceipyId;
    }

    private String mStrCatId, mStrCatName, mStrReceipyId;
}

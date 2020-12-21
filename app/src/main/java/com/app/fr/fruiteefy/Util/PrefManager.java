package com.app.fr.fruiteefy.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static  String IS_LOGIN = "islogin";
    private static String SOCIAL_LOgINID="socialloginid";
    private static String API_TOKEN="api_token";
    private static String DOB="dob";
    private static String USER_ID="user_id";
    private static String FIRST_NAME="first_name";
    private static String LAST_NAME="last_name";
    private static String EMAIL_ID="EAIL_id";
    private static String MOBILE="mobile";
    private static String ADDRESS="address";
    private static String PROF_IMG="profimg";
    private static String IS_SUBSCRIBE="is_subscribe";

    private static String LAT="lat";
    private static String LNG="lng";
    private static String CURRENTLAT="currentlat";
    private static String CURRENTLNG="currentlng";
    private static String COUNTRY="country";
    private static String STATE="state";
    private static String CITY="city";
    private static String PROFILEPIC="profpic";
    private static String ISPICO="ispico";
    private static String ISANTI="isanti";
    private static String ISCLIENT="iscliant";
    private static String USERTYPE="usertype";
    private static String ZIP="zip";
    private static String IAM="iam";
    private static String COMPANYNAME="companyname";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
    }



    public static String getIam(Context context) {
        return getSharedPreferences(context).getString(IAM , "");
    }

    public static void setIam(String iam,Context context) {

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(IAM ,iam);
        editor.commit();
    }

    public static String getIsCompanyname(Context context) {
        return getSharedPreferences(context).getString(COMPANYNAME , "");
    }

    public static void setCompanyname(String companyname,Context context) {

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(COMPANYNAME ,companyname );
        editor.commit();
    }

    public static String getIsCliant(Context context) {
        return getSharedPreferences(context).getString(ISCLIENT , "");
    }

    public static void setISCliant(String iscliant,Context context) {

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ISCLIENT ,iscliant );
        editor.commit();
    }

    public static String getIsAnti(Context context) {
        return getSharedPreferences(context).getString(ISANTI , "");
    }

    public static void setIsAnti(String isanti,Context context) {

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ISANTI ,isanti );
        editor.commit();
    }


    public static String getIsPico(Context context) {
        return getSharedPreferences(context).getString(ISPICO , "");
    }

    public static void setIsPico(String ispico,Context context) {

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ISPICO ,ispico );
        editor.commit();
    }


    public static String getUsertype(Context context) {
        return getSharedPreferences(context).getString(USERTYPE , "");
    }

    public static void setUsertype(String usertype,Context context) {

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(USERTYPE , usertype);
        editor.commit();
    }


    public static String getdob(Context context) {
        return getSharedPreferences(context).getString(DOB , "");
    }

    public static void setdob(String dob,Context context) {

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(DOB , dob);
        editor.commit();
    }


    public static String getZip(Context context) {
        return getSharedPreferences(context).getString(ZIP , "");
    }

    public static void setZip(String zip,Context context) {

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ZIP , zip);
        editor.commit();
    }


    public static String getState(Context context) {
        return getSharedPreferences(context).getString(STATE , "");
    }

    public static void setSTATE(String state,Context context) {


        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(STATE , state);
        editor.commit();
    }


    public static String getCOUNTRY(Context context) {
        return getSharedPreferences(context).getString(COUNTRY , "");
    }

    public static void setCOUNTRY(String country,Context context) {


        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(COUNTRY , country);
        editor.commit();
    }
    public static String getCity(Context context) {
        return getSharedPreferences(context).getString(CITY , "");
    }

    public static void setCity(String city,Context context) {


        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(CITY , city);
        editor.commit();
    }



    public static String getSocialloginId(Context context) {
        return getSharedPreferences(context).getString(SOCIAL_LOgINID , "");
    }

    public static void setSocialloginId(String socialloginid,Context context) {

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(SOCIAL_LOgINID , socialloginid);
        editor.commit();

    }


    public static String getLAT(Context context) {
        return getSharedPreferences(context).getString(LAT , "");
    }


    public static void setLAT(String lat,Context context) {


        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(LAT , lat);
        editor.commit();
    }


    public static String getCURRENTLAT(Context context) {
        return getSharedPreferences(context).getString(CURRENTLAT , "");
    }


    public static void setCURRENTLAT(String currentlat,Context context) {


        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(CURRENTLAT , currentlat);
        editor.commit();
    }

    public static String getCURRENTLNG(Context context) {
        return getSharedPreferences(context).getString(CURRENTLNG , "");
    }


    public static void setCURRENTLNG(String currentlng,Context context) {


        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(CURRENTLNG , currentlng);
        editor.commit();
    }



    public static String getLNG(Context context) {

        return getSharedPreferences(context).getString(LNG , "");
    }

    public static void setLNG(String lng,Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(LNG , lng);
        editor.commit();
    }





    public static void setApiToken(String api_token,Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(API_TOKEN , api_token);
        editor.commit();

    }

    public static String getApiToken(Context context) {
        return getSharedPreferences(context).getString(API_TOKEN , "");
    }


    public static void setLastName(String last_name,Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(LAST_NAME , last_name);
        editor.commit();

    }

    public static String getLastName(Context context) {
        return getSharedPreferences(context).getString(LAST_NAME , "");
    }

    public static void setProfilePic(String prof_pic,Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PROF_IMG , prof_pic);
        editor.commit();

    }

    public static String getProfPic(Context context) {
        return getSharedPreferences(context).getString(PROF_IMG , "");
    }


    public static void setAddress(String address,Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ADDRESS , address);
        editor.commit();

    }

    public static String getAddress(Context context) {
        return getSharedPreferences(context).getString(ADDRESS , "");
    }

    public static void setEmailId(String email_id,Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(EMAIL_ID , email_id);
        editor.commit();

    }

    public static String getEmailId(Context context) {
        return getSharedPreferences(context).getString(EMAIL_ID , "");
    }

    public static void setMobile(String mob_no,Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(MOBILE , mob_no);
        editor.commit();

    }

    public static String getMobile(Context context) {
        return getSharedPreferences(context).getString(MOBILE , "");
    }

    public static void setUserId(String user_id,Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(USER_ID , user_id);
        editor.commit();
    }

    public static String getUserId(Context context) {
        return getSharedPreferences(context).getString(USER_ID , "");
    }

    public static void setFirstName(String firstName,Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(FIRST_NAME , firstName);
        editor.commit();
    }

    public static String getFirstName(Context context) {
        return getSharedPreferences(context).getString(FIRST_NAME , "");
    }


    public static void setSubscribepico(String subscribe,Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(IS_SUBSCRIBE , subscribe);
        editor.commit();
    }

    public static String getSubscribepico(Context context) {
        return getSharedPreferences(context).getString(IS_SUBSCRIBE , "");
    }



    public static boolean IsLogin(Context context) {
        return getSharedPreferences(context).getBoolean(IS_LOGIN , false);
    }


    public static void setIsLogin(Context context, boolean newValue) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(IS_LOGIN , newValue);
        editor.commit();
    }

    public static void LogOut(Context context)
    {
        getSharedPreferences(context).edit().clear().commit();
    }



}

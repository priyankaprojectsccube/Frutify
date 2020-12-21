package com.app.fr.fruiteefy.Util;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {

    String productid;
    String offername;
    String offerid;
    String rating;
    String Date;
    String bookby;
    String action;
    String variety;
    String don_address_id_fk;




    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    String firstname;
    String readstatus;
    String token;
    String usertype;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    String lastname;
    String pricestatus;
    String actionclass;
    String value;
    String name;
    String Userid;
    String deliverystatus,validationstatus;

    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public String getValidationstatus() {
        return validationstatus;
    }

    public void setValidationstatus(String validationstatus) {
        this.validationstatus = validationstatus;
    }
    public String getReadstatus() {
        return readstatus;
    }

    public void setReadstatus(String readstatus) {
        this.readstatus = readstatus;
    }
    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getmStrQuickAvailbality() {
        return mStrQuickAvailbality;
    }

    public void setmStrQuickAvailbality(String mStrQuickAvailbality) {
        this.mStrQuickAvailbality = mStrQuickAvailbality;
    }

    String mStrQuickAvailbality;

    public String getmPreferedPico() {
        return mPreferedPico;
    }

    public void setmPreferedPico(String mPreferedPico) {
        this.mPreferedPico = mPreferedPico;
    }

    String mPreferedPico;

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    String mPrice;

    public String getmStock() {
        return mStock;
    }

    public void setmStock(String mStock) {
        this.mStock = mStock;
    }

    public String getmStockUnit() {
        return mStockUnit;
    }

    public void setmStockUnit(String mStockUnit) {
        this.mStockUnit = mStockUnit;
    }

    String mStock, mStockUnit;

    public String getmIsTreated() {
        return mIsTreated;
    }

    public void setmIsTreated(String mIsTreated) {
        this.mIsTreated = mIsTreated;
    }

    public String getIsTreadedProductList() {
        return isTreadedProductList;
    }

    public void setIsTreadedProductList(String isTreadedProductList) {
        this.isTreadedProductList = isTreadedProductList;
    }

    public String getIsTreaded_Desc() {
        return isTreaded_Desc;
    }

    public void setIsTreaded_Desc(String isTreaded_Desc) {
        this.isTreaded_Desc = isTreaded_Desc;
    }

    String mIsTreated, isTreadedProductList, isTreaded_Desc;

    public String getmOfferType() {
        return mOfferType;
    }

    public void setmOfferType(String mOfferType) {
        this.mOfferType = mOfferType;
    }

    String mOfferType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ArrayList<Product> antisubprodlist;

    public ArrayList<Product> getmArrSubList() {
        return mArrSubList;
    }

    public void setmArrSubList(ArrayList<Product> mArrSubList) {
        this.mArrSubList = mArrSubList;
    }

    ArrayList<Product> mArrSubList = new ArrayList<>();

    public ArrayList<Product> getAntisubprodlist() {
        return antisubprodlist;
    }

    public void setAntisubprodlist(ArrayList<Product> antisubprodlist) {
        this.antisubprodlist = antisubprodlist;
    }

    public String getActionclass() {
        return actionclass;
    }

    public void setActionclass(String actionclass) {
        this.actionclass = actionclass;
    }


    public String getPricestatus() {
        return pricestatus;
    }

    public void setPricestatus(String pricestatus) {
        this.pricestatus = pricestatus;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBookby() {
        return bookby;
    }

    public void setBookby(String bookby) {
        this.bookby = bookby;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String reservationid;
    String iscollected;
    String productimg;
    String offerimg;
    String collectdate;
    String status;
    String offertime;
    String Desc;
    String Available;
    String Offertype;
    String OfferPlace;
    String offer_cat_id;
    String mSalePick;
    String mAvailableType;
    String type;
    String nom;
    String acces;
    String address;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAcces() {
        return acces;
    }

    public void setAcces(String acces) {
        this.acces = acces;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }




    public String getmSalePick() {
        return mSalePick;
    }

    public void setmSalePick(String mSalePick) {
        this.mSalePick = mSalePick;
    }

    public String getmLat() {
        return mLat;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public String getmLang() {
        return mLang;
    }

    public void setmLang(String mLang) {
        this.mLang = mLang;
    }

    public String getmZipCode() {
        return mZipCode;
    }

    public void setmZipCode(String mZipCode) {
        this.mZipCode = mZipCode;
    }

    public String getmAvailableType() {
        return mAvailableType;
    }

    public void setmAvailableType(String mAvailableType) {
        this.mAvailableType = mAvailableType;
    }

    public String getmOfferAvailableData() {
        return mOfferAvailableData;
    }

    public void setmOfferAvailableData(String mOfferAvailableData) {
        this.mOfferAvailableData = mOfferAvailableData;
    }

    public String getmOfferAvailableTime() {
        return mOfferAvailableTime;
    }

    public void setmOfferAvailableTime(String mOfferAvailableTime) {
        this.mOfferAvailableTime = mOfferAvailableTime;
    }

    public String getmPrefered_picoreur() {
        return mPrefered_picoreur;
    }

    public void setmPrefered_picoreur(String mPrefered_picoreur) {
        this.mPrefered_picoreur = mPrefered_picoreur;
    }

    String mLat, mLang, mZipCode, mOfferAvailableData, mOfferAvailableTime, mPrefered_picoreur;

    public String getOffer_cat_id() {
        return offer_cat_id;
    }

    public void setOffer_cat_id(String offer_cat_id) {
        this.offer_cat_id = offer_cat_id;
    }

    public String getOffer_subcat_id() {
        return offer_subcat_id;
    }

    public void setOffer_subcat_id(String offer_subcat_id) {
        this.offer_subcat_id = offer_subcat_id;
    }

    String offer_subcat_id;

    public String getOfferPlace() {
        return OfferPlace;
    }

    public void setOfferPlace(String offerPlace) {
        OfferPlace = offerPlace;
    }

    public String getOffertype() {
        return Offertype;
    }

    public void setOffertype(String offertype) {
        Offertype = offertype;
    }

    public String getAvailable() {
        return Available;
    }

    public void setAvailable(String available) {
        Available = available;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    String lat, lng, stock, unit, weight, price,statusai;

    public String getStatusai() {
        return statusai;
    }

    public void setStatusai(String statusai) {
        this.statusai = statusai;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    String productname;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getOffername() {
        return offername;
    }

    public void setOffername(String offername) {
        this.offername = offername;
    }

    public String getOfferid() {
        return offerid;
    }

    public void setOfferid(String offerid) {
        this.offerid = offerid;
    }

    public String getReservationid() {
        return reservationid;
    }

    public void setReservationid(String reservationid) {
        this.reservationid = reservationid;
    }

    public String getIscollected() {
        return iscollected;
    }

    public void setIscollected(String iscollected) {
        this.iscollected = iscollected;
    }

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }

    public String getOfferimg() {
        return offerimg;
    }

    public void setOfferimg(String offerimg) {
        this.offerimg = offerimg;
    }

    public String getCollectdate() {
        return collectdate;
    }

    public void setCollectdate(String collectdate) {
        this.collectdate = collectdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOffertime() {
        return offertime;
    }



    public void setOffertime(String offertime) {
        this.offertime = offertime;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getDon_address_id_fk() {
        return don_address_id_fk;
    }

    public void setDon_address_id_fk(String don_address_id_fk) {
        this.don_address_id_fk = don_address_id_fk;
    }
}

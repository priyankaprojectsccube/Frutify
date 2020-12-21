package com.app.fr.fruiteefy.Util;

import java.io.Serializable;
import java.util.ArrayList;

public class Invoice implements Serializable {

    String orderdetail;
    String address,selleraddress,orderid,orderdate,orderno,shippingcost,
    subtotal,promocode,servicecharge,finaltotal;
    ArrayList<Product> mArrSubList = new ArrayList<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSelleraddress() {
        return selleraddress;
    }

    public void setSelleraddress(String selleraddress) {
        this.selleraddress = selleraddress;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getShippingcost() {
        return shippingcost;
    }

    public void setShippingcost(String shippingcost) {
        this.shippingcost = shippingcost;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public String getServicecharge() {
        return servicecharge;
    }

    public void setServicecharge(String servicecharge) {
        this.servicecharge = servicecharge;
    }

    public String getFinaltotal() {
        return finaltotal;
    }

    public void setFinaltotal(String finaltotal) {
        this.finaltotal = finaltotal;
    }

    public String getOrderdetail() {
        return orderdetail;
    }

    public void setOrderdetail(String orderdetail) {
        this.orderdetail = orderdetail;
    }

    public ArrayList<Product> getmArrSubList() {
        return mArrSubList;
    }

    public void setmArrSubList(ArrayList<Product> mArrSubList) {
        this.mArrSubList = mArrSubList;
    }


}

package com.app.fr.fruiteefy.user_client.products;

public class ProductPojo {

    String ingname;
    String ingweight;
    String ingunit;
    String price;
    String title;
    String nickname;
    String review;
    String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    String stock;

    public String getIngname() {
        return ingname;
    }

    public void setIngname(String ingname) {
        this.ingname = ingname;
    }

    public String getIngweight() {
        return ingweight;
    }

    public void setIngweight(String ingweight) {
        this.ingweight = ingweight;
    }

    public String getIngunit() {
        return ingunit;
    }

    public void setIngunit(String ingunit) {
        this.ingunit = ingunit;
    }
}

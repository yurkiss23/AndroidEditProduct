package com.example.cwork.productview.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDTO {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("imageBase64")
    @Expose
    private String imageBase64;

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}

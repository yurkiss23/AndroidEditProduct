package com.example.cwork.productview.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCreateDTO {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("imageBase64")
    @Expose
    private String imageBase64;

    public ProductCreateDTO(String title, String price, String imageBase64) {
        this.title = title;
        this.price = price;
        this.imageBase64 = imageBase64;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    @Override
    public String toString() {
        return "ProductCreateDTO{" +
                "title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", imageBase64='" + imageBase64 + '\'' +
                '}';
    }
}

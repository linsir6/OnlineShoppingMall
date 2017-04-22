package com.wenxin.wenxin.Model;

/**
 * Created by linSir on 17/3/14.产品model
 */
public class GoodsModel {

    private String id;
    private String name;
    private String price;
    private String type;
    private String img;
    private String details;

    public GoodsModel(String id, String name, String price, String type, String img, String details) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.img = img;
        this.details = details;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

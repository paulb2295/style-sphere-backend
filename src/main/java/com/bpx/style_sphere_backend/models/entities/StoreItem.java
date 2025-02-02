package com.bpx.style_sphere_backend.models.entities;

import com.bpx.style_sphere_backend.enums.StoreItemsCategories;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "store_items")
public class StoreItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageUrl;
    private Double price;
    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private StoreItemsCategories category;




    public StoreItem(Long id, String name, String imageUrl, Double price, StoreItemsCategories category) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public StoreItem() {}

    public Long getId() {
        return id;
    }

    public void setId(Long storeItemID) {
        this.id = storeItemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public StoreItemsCategories getCategory() {
        return category;
    }

    public void setCategory(StoreItemsCategories category) {
        this.category = category;
    }


    public static class Builder{
        private Long id;
        private String name;
        private String imageUrl;
        private Double price;
        private StoreItemsCategories category;

        public Builder id (Long id){
            this.id = id;
            return this;
        }

        public Builder name (String name){
            this.name = name;
            return this;
        }

        public Builder imageUrl (String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder price (double price){
            this.price = price;
            return this;
        }

        public Builder category (StoreItemsCategories category){
            this.category = category;
            return this;
        }

        public StoreItem build(){
            StoreItem storeItem = new StoreItem();
            storeItem.id = this.id;
            storeItem.name = this.name;
            storeItem.imageUrl = this.imageUrl;
            storeItem.price = this.price;
            storeItem.category = this.category;
            return storeItem;
        }


    }
}

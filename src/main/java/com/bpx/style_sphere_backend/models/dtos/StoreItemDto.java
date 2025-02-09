package com.bpx.style_sphere_backend.models.dtos;
import com.bpx.style_sphere_backend.enums.StoreItemsCategories;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class StoreItemDto implements Serializable {

    private Long id;
    @NotBlank(message = "Product name Mandatory!")
    private String name;
    @NotBlank(message = "Product image url Mandatory!")
    private String imageUrl;
    @Min(1)
    private Double price;

    @NotNull(message = "Product category Mandatory!")
    private StoreItemsCategories category;

    public StoreItemDto() {
    }

    public StoreItemDto(Long id, String name, String imageUrl, Double price, StoreItemsCategories category) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

        public StoreItemDto build(){
            StoreItemDto storeItemDto = new StoreItemDto();
            storeItemDto.id = this.id;
            storeItemDto.name = this.name;
            storeItemDto.imageUrl = this.imageUrl;
            storeItemDto.price = this.price;
            storeItemDto.category = this.category;
            return storeItemDto;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StoreItemDto that = (StoreItemDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(price, that.price) && category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, price, category);
    }
}

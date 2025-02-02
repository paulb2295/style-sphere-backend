package com.bpx.style_sphere_backend.models.dtos;

public class OrderItemInputDTO {
    Long storeItemId;
    int quantity;

    public Long getStoreItemId() {
        return storeItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderItemInputDTO(Long storeItemId, int quantity) {
        this.storeItemId = storeItemId;
        this.quantity = quantity;
    }

    public OrderItemInputDTO() {
    }
}

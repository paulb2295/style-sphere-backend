package com.bpx.style_sphere_backend.models.dtos;

public class OrderItemOutputDTO {
    private StoreItemDto storeItemDto;
    private int quantity;

    public OrderItemOutputDTO(StoreItemDto storeItemDto, int quantity) {
        this.storeItemDto = storeItemDto;
        this.quantity = quantity;
    }

    public StoreItemDto getStoreItemDto() {
        return storeItemDto;
    }

    public void setStoreItemDto(StoreItemDto storeItemDto) {
        this.storeItemDto = storeItemDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

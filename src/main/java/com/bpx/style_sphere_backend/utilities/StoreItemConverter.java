package com.bpx.style_sphere_backend.utilities;

import com.bpx.style_sphere_backend.models.dtos.StoreItemDto;
import com.bpx.style_sphere_backend.models.entities.StoreItem;

public class StoreItemConverter {

    public static StoreItem toEntity(StoreItemDto storeItemDto){
        return new StoreItem.Builder()
                .id(storeItemDto.getId())
                .name(storeItemDto.getName())
                .imageUrl(storeItemDto.getImageUrl())
                .price(storeItemDto.getPrice())
                .category(storeItemDto.getCategory())
                .build();
    }

    public static StoreItemDto toDto (StoreItem storeItem){
        return new StoreItemDto.Builder()
                .id(storeItem.getId())
                .name(storeItem.getName())
                .imageUrl(storeItem.getImageUrl())
                .price(storeItem.getPrice())
                .category(storeItem.getCategory())
                .build();
    }
}

package com.bpx.style_sphere_backend.services.interfaces;

import com.bpx.style_sphere_backend.enums.StoreItemsCategories;
import com.bpx.style_sphere_backend.models.dtos.StoreItemDto;

import java.util.List;

public interface StoreItemService {
    boolean createStoreItem(StoreItemDto storeItemDto);

    boolean createStoreItems(List<StoreItemDto> storeItemDtoList);

    List<StoreItemDto> getItemsByCategory (StoreItemsCategories category);

    List<StoreItemDto> getAllItems();

    boolean editStoreItem(StoreItemDto storeItemDto);

    void deleteStoreItem(Long id);
}

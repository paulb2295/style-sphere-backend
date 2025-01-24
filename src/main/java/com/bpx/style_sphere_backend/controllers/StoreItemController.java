package com.bpx.style_sphere_backend.controllers;

import com.bpx.style_sphere_backend.enums.StoreItemsCategories;
import com.bpx.style_sphere_backend.models.dtos.StoreItemDto;
import com.bpx.style_sphere_backend.services.interfaces.StoreItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class StoreItemController {

    private final StoreItemService storeItemService;

    public StoreItemController(StoreItemService storeItemService) {
        this.storeItemService = storeItemService;
    }

    @PostMapping("/api/admin/storeItem")
    public ResponseEntity<Boolean> addStoreItem(@RequestBody @Valid StoreItemDto storeItemDto){
        return ResponseEntity.status(201).body(storeItemService.createStoreItem(storeItemDto));
    }

    @PostMapping("/api/admin/storeItems")
    public ResponseEntity<Boolean> addStoreItems(@RequestBody @Valid List<StoreItemDto> storeItemDtoList){
        return ResponseEntity.status(201).body(storeItemService.createStoreItems(storeItemDtoList));
    }

    @GetMapping("/api/storeItems/categories")
    public ResponseEntity<List<StoreItemDto>> getItemsByCategory(@RequestParam StoreItemsCategories category){
        return ResponseEntity.ok(storeItemService.getItemsByCategory(category));
    }

    @GetMapping("/api/storeItems/all")
    public ResponseEntity<List<StoreItemDto>> getAllItems(){
        return ResponseEntity.ok(storeItemService.getAllItems());
    }

    @PutMapping("/api/admin/storeItem")
    public ResponseEntity<Boolean> editStoreItem(@RequestBody @Valid StoreItemDto storeItemDto){
        return ResponseEntity.status(201).body(true);
    }

    @DeleteMapping("/api/admin/storeItem/{id}")
    public ResponseEntity<Boolean> deleteStoreItem(@PathVariable Long id){
        storeItemService.deleteStoreItem(id);
        return ResponseEntity.status(200).body(true);
    }
}

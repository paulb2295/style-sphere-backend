package com.bpx.style_sphere_backend.repositories;

import com.bpx.style_sphere_backend.models.entities.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {

    @Query(value = "SELECT * FROM store_items WHERE store_items.category = :category", nativeQuery = true)
    List<StoreItem> getItemsByCategory(@Param("category")String category);
}

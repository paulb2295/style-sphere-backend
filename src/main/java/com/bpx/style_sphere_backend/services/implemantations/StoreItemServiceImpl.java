package com.bpx.style_sphere_backend.services.implemantations;

import com.bpx.style_sphere_backend.enums.StoreItemsCategories;
import com.bpx.style_sphere_backend.exceptions.InvalidStoreItemException;
import com.bpx.style_sphere_backend.models.dtos.StoreItemDto;
import com.bpx.style_sphere_backend.models.entities.StoreItem;
import com.bpx.style_sphere_backend.repositories.StoreItemRepository;
import com.bpx.style_sphere_backend.services.interfaces.StoreItemService;
import com.bpx.style_sphere_backend.utilities.StoreItemConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreItemServiceImpl implements StoreItemService {

    public final StoreItemRepository storeItemRepository;

    public StoreItemServiceImpl(StoreItemRepository storeItemRepository) {
        this.storeItemRepository = storeItemRepository;
    }

    @Override
    public boolean createStoreItem(StoreItemDto storeItemDto) {
        StoreItem savedStoreItem = storeItemRepository.save(StoreItemConverter.toEntity(storeItemDto));
        return savedStoreItem.getId() != null;
    }

    @Override
    public boolean createStoreItems(List<StoreItemDto> storeItemDtoList) {
        List<StoreItem> savedItemsList = storeItemRepository.saveAll(storeItemDtoList.stream()
                .map(item -> StoreItemConverter.toEntity(item))
                .toList());
        return !savedItemsList.isEmpty();
    }

    @Override
    public List<StoreItemDto> getItemsByCategory(StoreItemsCategories category){
        return storeItemRepository.getItemsByCategory(category.name()).stream()
                .map((item) -> StoreItemConverter.toDto(item))
                .toList();
    }

    @Override
    public List<StoreItemDto> getAllItems(){
        return storeItemRepository.findAll().stream()
                .map((item) -> StoreItemConverter.toDto(item))
                .toList();
    }

    @Override
    public boolean editStoreItem(StoreItemDto storeItemDto){
        StoreItem savedEditedItem = storeItemRepository.save(StoreItemConverter.toEntity(storeItemDto));
        return storeItemDto.equals(StoreItemConverter.toDto(savedEditedItem));
    }

    @Override
    public void deleteStoreItem(Long id){
        Optional<StoreItem> optionalStoreItem = storeItemRepository.findById(id);
        if(optionalStoreItem.isPresent()){
            storeItemRepository.deleteById(id);
        }else{
            throw new InvalidStoreItemException("Product doesn't exist!");
        }

    }
}

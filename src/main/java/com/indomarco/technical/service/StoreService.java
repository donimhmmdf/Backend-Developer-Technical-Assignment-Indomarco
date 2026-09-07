package com.indomarco.technical.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.indomarco.technical.entity.Store;
import com.indomarco.technical.model.StoreResponse;
import com.indomarco.technical.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public Page<StoreResponse> getAll(Pageable pageable) {
        Page<Store> stores = storeRepository.findAll(PageRequest.of(0, 5));
        return stores.map(this::toStoreResponse);
    }

    public Page<StoreResponse> findByProvince(String province, Pageable pageable) {
        Page<Store> stores = storeRepository.findByProvinceName(province, pageable);

        if (stores.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No stores found for province: " + province);
        }

        return stores.map(this::toStoreResponse);
    }

    public String updateWhitelist(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No stores found for id: " + id));
        store.setIsWhitelisted(!store.getIsWhitelisted());

        storeRepository.save(store);

        return "OK";
    }

    private StoreResponse toStoreResponse(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .branchName(store.getBranch().getName())
                .isWhitelisted(store.getIsWhitelisted())
                .provinceName(store.getBranch().getProvince().getName())
                .build();
    }

}

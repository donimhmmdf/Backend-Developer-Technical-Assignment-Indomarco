package com.indomarco.technical.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indomarco.technical.model.StoreResponse;
import com.indomarco.technical.model.WebResponse;
import com.indomarco.technical.service.StoreService;
import com.indomarco.technical.utility.ResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    private final ResponseUtil responseUtil;

    @GetMapping("")
    public ResponseEntity<WebResponse<Page<StoreResponse>>> getAll(Pageable pageable) {
        return responseUtil.successResponse("Success", storeService.getAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<WebResponse<Page<StoreResponse>>> getStoreByProvince(
            @RequestParam String province, Pageable pageable) {
        return responseUtil.successResponse("Success", storeService.findByProvince(province, pageable));
    }

    @PutMapping("/whitelist/{id}")
    public ResponseEntity<WebResponse<String>> updateWhitelist(@PathVariable Long id) {
        return responseUtil.successResponse("Success", storeService.updateWhitelist(id));
    }

}

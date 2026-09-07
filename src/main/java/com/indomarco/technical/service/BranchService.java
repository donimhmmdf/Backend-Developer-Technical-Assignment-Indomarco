package com.indomarco.technical.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.indomarco.technical.entity.Branch;
import com.indomarco.technical.entity.Province;
import com.indomarco.technical.entity.Store;
import com.indomarco.technical.model.BranchResponse;
import com.indomarco.technical.model.BranchUpdateRequest;
import com.indomarco.technical.repository.BranchRepository;
import com.indomarco.technical.repository.ProvinceRepository;
import com.indomarco.technical.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final ProvinceRepository provinceRepository;
    private final ValidationService validationService;
    private final StoreRepository storeRepository;

    public List<BranchResponse> getAll() {
        List<Branch> branches = branchRepository.findAllByIsDeletedFalse();
        return branches.stream()
                .map(this::toBranchResponse)
                .toList();
    }

    @Transactional
    public String update(Long id, BranchUpdateRequest request) {
        validationService.validate(request);

        Branch branch = branchRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch with id " + id + " not found."));
        Province province = provinceRepository.findById(request.getProvinceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Province with id " + request.getProvinceId() + " not found."));

        branch.setName(request.getName());
        branch.setProvince(province);
        branchRepository.save(branch);

        return "OK";
    }

    @Transactional
    public String delete(Long id) {
        Branch branch = branchRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch with id " + id + " not found."));

        List<Store> stores = storeRepository.findByBranchId(branch.getId());

        stores.forEach(store -> store.setIsDeleted(true));

        branch.setIsDeleted(true);
        branchRepository.save(branch);
        return "OK";

    }

    private BranchResponse toBranchResponse(Branch branch) {
        return BranchResponse.builder()
                .id(branch.getId())
                .name(branch.getName())
                .province(branch.getProvince().getName())
                .build();
    }

}

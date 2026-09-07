package com.indomarco.technical.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indomarco.technical.model.BranchResponse;
import com.indomarco.technical.model.BranchUpdateRequest;
import com.indomarco.technical.model.WebResponse;
import com.indomarco.technical.service.BranchService;
import com.indomarco.technical.utility.ResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;
    private final ResponseUtil responseUtil;

    @GetMapping("")
    public ResponseEntity<WebResponse<List<BranchResponse>>> getAllBranch() {
        return responseUtil.successResponse("Success", branchService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<String>> updateBranch(@PathVariable Long id,
            @RequestBody BranchUpdateRequest request) {
        return responseUtil.successResponse("Success", branchService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<String>> deleteOneBranch(@PathVariable Long id) {
        return responseUtil.successResponse("Success", branchService.delete(id));
    }

}

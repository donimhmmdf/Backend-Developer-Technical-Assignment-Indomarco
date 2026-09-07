package com.indomarco.technical.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponse {

    private Long id;

    private String name;

    private String branchName;

    private Boolean isWhitelisted;

    private String provinceName;
}

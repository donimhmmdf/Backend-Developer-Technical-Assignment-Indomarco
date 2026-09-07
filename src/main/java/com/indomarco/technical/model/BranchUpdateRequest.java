package com.indomarco.technical.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchUpdateRequest {

    @NotBlank(message = "Name branch cannot be null.")
    private String name;

    @NotNull(message = "Province Id cannot be null.")
    private Long provinceId;
}

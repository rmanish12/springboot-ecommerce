package com.ecomm.ecommservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserRoleRequestDto {
    @NotNull(message = "Roles should not be null")
    private List<String> roles;
}

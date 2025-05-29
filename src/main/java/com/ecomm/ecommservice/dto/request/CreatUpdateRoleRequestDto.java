package com.ecomm.ecommservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreatUpdateRoleRequestDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private List<String> permissions;
}

package com.ecomm.ecommservice.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    private HttpStatus statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public static ErrorResponse buildErrorResponseWithMessage (String message, HttpStatus statusCode) {
        return ErrorResponse.builder()
                .message(message)
                .statusCode(statusCode)
                .build();
    }

    public static ErrorResponse buildErrorResponseWithErrors (Map<String, String> errors, HttpStatus statusCode) {
        return ErrorResponse.builder()
                .errors(errors)
                .statusCode(statusCode)
                .build();
    }
}

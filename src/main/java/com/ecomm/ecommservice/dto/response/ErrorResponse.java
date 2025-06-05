package com.ecomm.ecommservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    private HttpStatus statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public static ErrorResponse buildErrorResponseWithMessage (String message, HttpStatus statusCode) {
        return ErrorResponse.builder()
                .message(message)
                .statusCode(statusCode)
                .build();
    }

    public static ErrorResponse buildErrorResponseWithErrors (List<String> errors, HttpStatus statusCode) {
        return ErrorResponse.builder()
                .errors(errors)
                .statusCode(statusCode)
                .build();
    }
}

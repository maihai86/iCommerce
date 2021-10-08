package com.maitienhai.icommerce.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ApiResponse<T> {

    private String errorCode;

    private String errorMessage;

    private T data;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> error(String errorCode, String errorMessage, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        response.setData(data);
        return response;
    }

}

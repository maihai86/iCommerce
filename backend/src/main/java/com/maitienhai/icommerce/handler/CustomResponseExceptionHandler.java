package com.maitienhai.icommerce.handler;

import com.maitienhai.icommerce.dto.response.common.ApiResponse;
import com.maitienhai.icommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Object> handleAppException(BusinessException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                ApiResponse.error(ex.getErrorCode(), messageSource.getMessage(ex.getErrorCode(), null, LocaleContextHolder.getLocale()), null),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}

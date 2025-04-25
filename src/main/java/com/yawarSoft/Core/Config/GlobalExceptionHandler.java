package com.yawarSoft.Core.Config;

import com.yawarSoft.Core.Errors.ResourceNotFoundException;
import com.yawarSoft.Core.Dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException(AccessDeniedException ex) {
        log.error("Acceso denegado", ex);
        ApiResponse response = new ApiResponse(HttpStatus.FORBIDDEN, "No tienes permisos para acceder a este recurso");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleUnauthorizedException(AuthenticationException ex) {
        log.error("Error de credenciales invalidos", ex);
        ApiResponse response = new ApiResponse(HttpStatus.UNAUTHORIZED, "Credenciales invalidos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(Exception ex) {

        log.error("Error IllegalArgumentException", ex);
        ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(Exception ex) {

        log.error("Error IllegalArgumentException", ex);
        ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex, HttpServletRequest request) throws Exception {

        log.error("Error interno del servidor", ex);
        ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

package com.yawarSoft.Controllers;

import com.yawarSoft.Controllers.Dto.AuthCreateUserRequest;
import com.yawarSoft.Controllers.Dto.AuthLoginRequest;
import com.yawarSoft.Controllers.Dto.AuthResponse;
import com.yawarSoft.Services.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest){
        return new ResponseEntity<>(userDetailService.createUser(authCreateUserRequest),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

        @PostMapping("/loginCookie")
    public ResponseEntity<?> loginCookie(@RequestBody @Valid AuthLoginRequest userRequest){
        AuthResponse authResponse = userDetailService.loginUser(userRequest);
        String token = authResponse.jwt();

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)  // Evita acceso desde JavaScript (protección XSS)
//                .secure(true)  // Requiere HTTPS (importante en producción)
                .path("/")  // Disponible en toda la aplicación
                .maxAge(Duration.ofHours(2))  // Expira en 2 horas
                .sameSite("Strict")  // Protección contra CSRF
                .build();

        // 🔥 Retornar el `username` en el body, pero el JWT en la cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString()) // Enviar la cookie en el header
                .body(Map.of("username", authResponse.username())); // Enviar solo el username en el body
    }
}
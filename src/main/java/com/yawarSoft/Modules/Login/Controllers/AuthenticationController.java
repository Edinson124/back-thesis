package com.yawarSoft.Modules.Login.Controllers;

import com.yawarSoft.Core.Utils.Constants;
import com.yawarSoft.Modules.Login.Dto.AuthLoginRequest;
import com.yawarSoft.Modules.Login.Dto.AuthResponse;
import com.yawarSoft.Modules.Login.Dto.UserRoleDTO;
import com.yawarSoft.Modules.Login.Services.Implementations.UserDetailServiceImpl;
import com.yawarSoft.Modules.Login.Services.Interfaces.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginCookie(@RequestBody @Valid AuthLoginRequest userRequest){
        AuthResponse authResponse = userDetailService.loginUser(userRequest);
        String token = authResponse.jwt();

        long expirationTimestamp = Constants.getTimeToken();

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)  // Evita acceso desde JavaScript (protección XSS)
//                .secure(true)  // Requiere HTTPS (importante en producción)
                .path("/")  // Disponible en toda la aplicación
                .maxAge(expirationTimestamp-10000)  // Expira en 2 horas
                .sameSite("Strict")  // Protección contra CSRF
                .build();

        List<String> reponse = authService.getUserFullNameAndRoleName(authResponse.username());
        // 🔥 Retornar el `username` en el body, pero el JWT en la cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString()) // Enviar la cookie en el header
                .body(Map.of("username", authResponse.username(), "session_time", expirationTimestamp
                ,"fullName", reponse.getFirst(),"role",reponse.get(1)));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Crear una cookie con el mismo nombre pero vacía y expirada
        ResponseCookie expiredCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
//                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)  // Expira inmediatamente
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }

    @GetMapping("/me")
    public ResponseEntity<?> meRoleAndPermission(){
        UserRoleDTO userRoleDTO = authService.meRoleAndPermission();
        return ResponseEntity.ok()
                .body(userRoleDTO);
    }

}
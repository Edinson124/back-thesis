package com.yawarSoft.Config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yawarSoft.Dto.ApiResponse;
import com.yawarSoft.Utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Slf4j
//OncePerRequestFilter, clase abstracta que va a ejecutar el filtro por cada request
public class JWTTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    public JWTTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            //Obtenemos token del header del request Authorization
            String jwtToken = null;
            if (request.getCookies() != null) {
                Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies())
                        .filter(cookie -> "jwt".equals(cookie.getName()))
                        .findFirst();

                if (jwtCookie.isPresent()) {
                    jwtToken = jwtCookie.get().getValue();
                }
            }

            if (jwtToken != null) {
                //validamos token
                DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
                //extraemos userName
                String userName = jwtUtils.extractUserName(decodedJWT);
                //obtenemos authorities en un string separado por comas, esto es por como lo hemos programado
                String authoritiesString = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

                //Se usa AuthorityUtils de spring security que devuelve un collection de Authorities a partir del string separado por comas
                Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesString);

                //Se obtiene el contexto, y se inserta la authentication en el SecurityContextHolder
                SecurityContext context = SecurityContextHolder.getContext();
                Authentication authentication = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

            }
            filterChain.doFilter(request, response);
        }catch(JWTVerificationException ex){
            log.error("Token invalido", ex);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token invalido\"}");
        }catch(Exception e){
            log.error("Error interno del servidor", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Error interno en autorización\"}");
        }

    }
}
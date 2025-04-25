package com.yawarSoft.Core.Config;

import com.yawarSoft.Core.Config.filter.JWTTokenValidator;
import com.yawarSoft.Modules.Login.Services.Implementations.UserDetailServiceImpl;
import com.yawarSoft.Core.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource corsConfigurationSource) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) //Vulnerabildiad explotada en formularios
                .authorizeHttpRequests(http -> {
                    // EndPoints publicos
                    http.requestMatchers("/customError").permitAll();
                    http.requestMatchers("/error").permitAll();
                    http.requestMatchers("/access-denied").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/ubication/**").permitAll();

//                    // EndPoints Privados
//                    http.requestMatchers(HttpMethod.GET, "/method/get").hasAuthority("READ");
//                    http.requestMatchers(HttpMethod.POST, "/method/post").hasAuthority("CREATE");
//                    http.requestMatchers(HttpMethod.DELETE, "/method/delete").hasAuthority("DELETE");
//                    http.requestMatchers(HttpMethod.PUT, "/method/put").hasAuthority("UPDATE");

                    //Por defecto spring security pone como .denyAll() todas las demas request
                    //SI SE VA A MEZCLAR EL USO DE ANOTACIONES CON authorizeHttpRequests SE DEBE PONER SI O SI esta linea.
                    http.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Sesion sin estado, no manejamos la sesion internamente, si no con tokens por ejemplo
                //Agregamos el filtro del JWT creado, y se pone antes del filtro de authentication
                .addFilterBefore(new JWTTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                            response.getWriter().write("No autorizado: Credenciales invalidas");
//                            response.getWriter().flush();
//                            response.getWriter().close();
//                        })
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                            response.setContentType("application/json");
//                            response.getWriter().write("{\"status\":403, \"message\":\"Acceso SECURITY\"}");
//                            response.getWriter().flush();
//                            response.getWriter().close();
//                        })
//                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
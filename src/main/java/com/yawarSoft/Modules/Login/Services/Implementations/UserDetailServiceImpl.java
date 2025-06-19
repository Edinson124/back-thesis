package com.yawarSoft.Modules.Login.Services.Implementations;


import com.yawarSoft.Core.Entities.RoleEntity;
import com.yawarSoft.Core.Errors.UserInactiveException;
import com.yawarSoft.Core.Errors.UserNotBloodBankException;
import com.yawarSoft.Modules.Admin.Enums.RoleEnum;
import com.yawarSoft.Modules.Admin.Enums.UserStatus;
import com.yawarSoft.Modules.Login.Dto.AuthLoginRequest;
import com.yawarSoft.Modules.Login.Dto.AuthResponse;
import com.yawarSoft.Core.Entities.AuthEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Modules.Login.Models.CustomUserDetails;
import com.yawarSoft.Modules.Login.Repositories.AuthRepository;
import com.yawarSoft.Core.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//Se crea userDetailService personalizado para obtener usuarios de BD, se requiere implementar UserDetailsService de spring security
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthRepository authRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Obteniendo userEntity de Base de datos
        AuthEntity authEntity = authRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no existe"));

        UserEntity userEntity = authEntity.getUser();
        RoleEntity roleEntity = userEntity.getRole();

        if(userEntity.getStatus().equals(UserStatus.INACTIVE)){
            throw new UserInactiveException("Usuario inactivo");
        }

        if ((roleEntity == null || roleEntity.getId() != RoleEnum.ADMINISTRADOR.getId()) &&
                        userEntity.getBloodBank() == null
        ) {
            throw new UserNotBloodBankException("El usuario no tiene banco de sangre asignado.");
        }


        //Mapeando a User de Spring Security (clase que implementa interfaz UserDetails)

        //Mapeando permisos (authorities) en SimpleGrantedAuthority, clase que implementa interfaz GrantedAuthority, tipo de dato que necesita
        // el User de spring security private final Set<GrantedAuthority> authorities;
        List<SimpleGrantedAuthority> authorityList = getAuthoritiesOfUserEntity(userEntity);

        //Se retorna y arma el objeto User de spring security
        return new CustomUserDetails(
                userEntity.getId(),  // Aquí pasamos el ID del usuario
                authEntity.getUsername(),
                authEntity.getPassword(),
                authEntity.isEnabled(),
                authEntity.isAccountNoExpired(),
                authEntity.isCredentialNoExpired(),
                authEntity.isAccountNoLocked(),
                authorityList
        );
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest){
        String userName = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.createToken(authentication);

        return new AuthResponse(userName,"Usuario logueado exitosamente",token,true);

    }

    private Authentication authenticate(String user, String pass){
        try{
            CustomUserDetails userDetails = (CustomUserDetails) loadUserByUsername(user);

            if(!passwordEncoder.matches(pass,userDetails.getPassword())){
                throw new BadCredentialsException("Usuario o contraseña invalidos");
            }

            return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        } catch (Exception e) {
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }
    }

    private List<SimpleGrantedAuthority> getAuthoritiesOfUserEntity(UserEntity userEntity){
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        RoleEntity role = userEntity.getRole();

        if (role != null) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName())));
            role.getPermissionList()
                    .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
        }

        return authorityList;

//        userEntity.getRoles().forEach(role-> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));
//        userEntity.getRoles().stream()
//                .flatMap(role->role.getPermissionList().stream())
//                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
//
//        return authorityList;

    }
}
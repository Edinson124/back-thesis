package com.yawarSoft.Modules.Interoperability.Services.Interfaces;

import com.yawarSoft.Modules.Interoperability.Dtos.AuthExternalDTO;
import com.yawarSoft.Modules.Interoperability.Dtos.Request.AuthExternalSystemRequestDTO;

import java.util.Optional;

public interface AuthExternalSystemService {
    Optional<AuthExternalDTO> getAuthExternalByIdBloodBank(Integer idBloodBank);

    AuthExternalDTO saveAuthExternal(Integer idBloodBank, AuthExternalSystemRequestDTO authExternalRequestDTO);
}

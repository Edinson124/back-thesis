package com.yawarSoft.Modules.Interoperability.Services.Implementations;

import com.yawarSoft.Core.Entities.AuthExternalSystemEntity;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Modules.Admin.Dto.BloodBankDTO;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BloodBankService;
import com.yawarSoft.Modules.Interoperability.Dtos.AuthExternalDTO;
import com.yawarSoft.Modules.Interoperability.Dtos.Request.AuthExternalSystemRequestDTO;
import com.yawarSoft.Modules.Interoperability.Mappers.AuthExternalSystemMapper;
import com.yawarSoft.Modules.Interoperability.Repositories.AuthExternalSystemRepository;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.AuthExternalSystemService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthExternalSystemServiceImpl implements AuthExternalSystemService {

    private final AuthExternalSystemRepository authExternalSystemRepository;
    private final AuthExternalSystemMapper authExternalSystemMapper;
    private final BloodBankService bloodBankService;
    private final PasswordEncoder passwordEncoder;

    public AuthExternalSystemServiceImpl(AuthExternalSystemRepository authExternalSystemRepository, AuthExternalSystemMapper authExternalSystemMapper, BloodBankService bloodBankService, PasswordEncoder passwordEncoder) {
        this.authExternalSystemRepository = authExternalSystemRepository;
        this.authExternalSystemMapper = authExternalSystemMapper;
        this.bloodBankService = bloodBankService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AuthExternalDTO> getAuthExternalByIdBloodBank(Integer idBloodBank) {
        AuthExternalSystemEntity entity = authExternalSystemRepository.findByBloodBank_Id(idBloodBank);
        if (entity == null) {
            return Optional.empty();
        }
        AuthExternalDTO dto = authExternalSystemMapper.toDTO(entity);
        dto.setHasPassword(entity.getClientSecret() != null && !entity.getClientSecret().isBlank());
        return Optional.of(dto);
    }


    @Override
    public AuthExternalDTO saveAuthExternal(Integer idBloodBank, AuthExternalSystemRequestDTO requestDTO) {
        // 1️⃣ Intentamos obtener si existe
        AuthExternalSystemEntity entity = authExternalSystemRepository.findByBloodBank_Id(idBloodBank);

        if (entity == null) {
            // 🟢 NO EXISTE -> CREAR
            entity = new AuthExternalSystemEntity();

            BloodBankDTO bloodBankDTO = bloodBankService.getBloodBankById(idBloodBank);
            BloodBankEntity bloodBank = new BloodBankEntity();
            bloodBank.setName(bloodBankDTO.getName());
            bloodBank.setId(bloodBankDTO.getId());

            entity.setBloodBank(bloodBank);
            entity.setDescription(requestDTO.getDescription());
            entity.setClientUser(requestDTO.getClientUser());
            try {
                entity.setClientSecret(passwordEncoder.encode(requestDTO.getClientSecret()));
            } catch (Exception e) {
                throw new IllegalStateException("Error al encriptar la contraseña", e);
            }
            entity.setIsActive(true);
        } else {
            // 🔵 EXISTE -> ACTUALIZAR
            entity.setDescription(requestDTO.getDescription());
            entity.setClientUser(requestDTO.getClientUser());
            entity.setClientSecret(requestDTO.getClientSecret());
        }

        if (requestDTO.getClientSecret() != null && !requestDTO.getClientSecret().isBlank()) {
            try {
            entity.setClientSecret(passwordEncoder.encode(requestDTO.getClientSecret()));
            } catch (Exception e) {
                throw new IllegalStateException("Error al encriptar la contraseña", e);
            }
        }
        AuthExternalSystemEntity saved = authExternalSystemRepository.save(entity);

        return authExternalSystemMapper.toDTO(saved);
    }

}

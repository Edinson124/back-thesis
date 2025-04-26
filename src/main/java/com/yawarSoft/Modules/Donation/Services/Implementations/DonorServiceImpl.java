package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.DonorEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.HmacUtil;
import com.yawarSoft.Core.Utils.UserUtils;
import com.yawarSoft.Modules.Donation.Dto.DonorDTO;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.GetDonorRequest;
import com.yawarSoft.Modules.Donation.Mappers.DonorMapper;
import com.yawarSoft.Modules.Donation.Repositories.DonorRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonorService;
import org.springframework.stereotype.Service;

@Service
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;
    private final AESGCMEncryptionUtil aesGCMEncryptionUtil;
    private final DonorMapper donorMapper;
    private final HmacUtil hmacUtil;

    public DonorServiceImpl(DonorRepository donorRepository, AESGCMEncryptionUtil aesGCMEncryptionUtil, DonorMapper donorMapper, HmacUtil hmacUtil) {
        this.donorRepository = donorRepository;
        this.aesGCMEncryptionUtil = aesGCMEncryptionUtil;
        this.donorMapper = donorMapper;
        this.hmacUtil = hmacUtil;
    }

    @Override
    public DonorDTO createDonor(DonorDTO donorDTO) {
        UserEntity userAuth = UserUtils.getAuthenticatedUser();
        String combinedInfo = donorDTO.getDocumentType() + '|' + donorDTO.getDocumentNumber();
        String searchHash = hmacUtil.generateHmac(combinedInfo);

        if (donorRepository.existsBySearchHash(searchHash)) {
            throw new IllegalArgumentException("El documento ya existe en el sistema");
        }

        DonorEntity donorEntity = donorMapper.toEntity(donorDTO, aesGCMEncryptionUtil);
        donorEntity.setSearchHash(searchHash);
        donorEntity.setCreatedBy(userAuth);
        donorEntity.setStatus("ACTIVE");
        donorRepository.save(donorEntity);
        return donorDTO;
    }

    @Override
    public DonorGetDTO getDonor(GetDonorRequest infoDonorRequest) {
        String combinedInfo = infoDonorRequest.documentType() + '|' + infoDonorRequest.documentNumber();
        String searchHash = hmacUtil.generateHmac(combinedInfo);
        DonorEntity donorEntity = donorRepository.findBySearchHash(searchHash)
                .orElseThrow(() -> new IllegalArgumentException("Donante no encontrado con documento: " + infoDonorRequest.documentNumber()));

        return donorMapper.toGetDto(donorEntity, aesGCMEncryptionUtil);
    }
}

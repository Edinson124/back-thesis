package com.yawarSoft.Services.Interfaces;

import com.yawarSoft.Dto.NetworkDTO;
import org.springframework.data.domain.Page;

public interface BBNetworkService {
    Page<NetworkDTO> searchByNameWithActualBloodBank(String name, int page, int size);
    NetworkDTO getById(Integer id);
    void associateBloodBank(Integer networkId, Integer bloodBankId);
    void disassociateBloodBank(Integer networkId, Integer bloodBankId);
}

package com.yawarSoft.Modules.Admin.Services.Interfaces;

import com.yawarSoft.Modules.Admin.Dto.NetworkDTO;
import com.yawarSoft.Modules.Admin.Dto.Request.BBNetworkCreateDTO;
import org.springframework.data.domain.Page;

public interface BBNetworkService {
    Page<NetworkDTO> searchByNameWithActualBloodBank(Integer idBloodBank, String name, int page, int size);
    NetworkDTO getById(Integer id);
    void associateBloodBank(Integer networkId, Integer bloodBankId);
    void disassociateBloodBank(Integer networkId, Integer bloodBankId);

    Integer createNetworkBB(BBNetworkCreateDTO bbNetworkCreateDTO);
}

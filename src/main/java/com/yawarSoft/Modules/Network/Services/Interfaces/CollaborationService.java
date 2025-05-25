package com.yawarSoft.Modules.Network.Services.Interfaces;

import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.Response.OptionBloodBankNetworkDTO;
import com.yawarSoft.Modules.Network.Dto.Response.StockNetworkDTO;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface CollaborationService {
    Page<NetworkCollaborationDTO> searchNetworksByUserAndOptionalFilters(String name, Integer idBloodBank, int page, int size);

    StockNetworkDTO getUnitsStock(Integer idBloodBank, Integer idNetwork, int page, int size,
                                  LocalDate startEntryDate, LocalDate endEntryDate, LocalDate startExpirationDate,
                                  LocalDate endExpirationDate, String bloodType, String type);

    OptionBloodBankNetworkDTO getBBOptionsNetwork(Integer networkId);
}

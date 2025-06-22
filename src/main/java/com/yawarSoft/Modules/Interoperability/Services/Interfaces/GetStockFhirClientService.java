package com.yawarSoft.Modules.Interoperability.Services.Interfaces;

import com.yawarSoft.Modules.Interoperability.Dtos.StockResponseDTO;

import java.util.List;

public interface GetStockFhirClientService {
    List<StockResponseDTO> getObservationsFromExternalSystem(Integer idBloodBank);
}


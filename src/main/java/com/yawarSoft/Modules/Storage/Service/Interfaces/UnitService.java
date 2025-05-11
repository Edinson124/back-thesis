package com.yawarSoft.Modules.Storage.Service.Interfaces;

import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitExtractionDTO;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import com.yawarSoft.Modules.Storage.Dto.UnitDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface UnitService {
    Page<UnitListDTO> getUnitsQuarantined(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate,
                                          LocalDate startExpirationDate, LocalDate endExpirationDate, String bloodType,
                                          String type);

    Page<UnitListDTO> getUnitsTransformation(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate,
                                          LocalDate startExpirationDate, LocalDate endExpirationDate, String bloodType,
                                          String type);
    boolean updateUnitsReactiveTestSerologyById(Long id, String result);
    boolean updateUnitsNoReactiveTestSerologyById(Long id, String result);

    UnitDTO getUnitById(Long id);

    Page<UnitListDTO> getUnitsStock(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate, LocalDate startExpirationDate, LocalDate endExpirationDate, String bloodType, String type, String status);

    List<UnitExtractionDTO> getUnitsByDonation(Long idDonation);

    UnitExtractionDTO saveUnitDonation(Long idDonation, UnitExtractionDTO unit);

    UnitExtractionDTO editUnit(Long idUnit, UnitExtractionDTO unit);
}

package com.yawarSoft.Modules.Storage.Service.Interfaces;

import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface UnitService {
    Page<UnitListDTO> getUnitsQuarantined(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate,
                                          LocalDate startExpirationDate, LocalDate endExpirationDate, String bloodType,
                                          String type);

    boolean updateUnitsReactiveTestSerologyById(Long id, String result);
    boolean updateUnitsNoReactiveTestSerologyById(Long id, String result);
}

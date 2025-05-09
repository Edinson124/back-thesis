package com.yawarSoft.Modules.Transfusion.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.Response.DonationByDonorDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TranfusionListDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionByPatientDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface TransfusionService  {
    Page<TransfusionByPatientDTO> getDonationsByDonor(String documentType, String documentNumber, int page, int size);

    Page<TranfusionListDTO> getTransfusions(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate, Long code, String status);
}

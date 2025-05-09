package com.yawarSoft.Modules.Transfusion.Dto.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusionByPatientDTO {

    private Long id;
    private String bloodBankName;
    private String attendingDoctorName;
    private LocalDate date;
    private String status;
}

package com.yawarSoft.Modules.Transfusion.Dto.Response;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranfusionListDTO {

    private Long id;
    private String patientDocumentNumber;
    private String patientName;
    private String attendingDoctorName;
    private LocalDate date;
    private String status;
}

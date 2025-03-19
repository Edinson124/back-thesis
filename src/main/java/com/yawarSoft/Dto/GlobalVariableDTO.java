package com.yawarSoft.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GlobalVariableDTO {

    private Integer id;
    private String groupName;
    private String name;
    private String code;
    private String dataType;
    private String value;
}

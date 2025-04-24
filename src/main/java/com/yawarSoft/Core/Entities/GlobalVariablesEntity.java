package com.yawarSoft.Entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "global_variables")
public class GlobalVariablesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_name")
    private String groupName;
    private String name;
    private String code;
    @Column(name = "data_type")
    private String dataType;
    private String value;
}

package com.yawarSoft.Core.Entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "external_endpoints")
public class ExternalEndpointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_external_system", referencedColumnName = "id")
    private ExternalSystemEntity externalSystem;

    @ManyToOne
    @JoinColumn(name = "id_blood_bank", referencedColumnName = "id")
    private BloodBankEntity bloodBank;

    @Column(name = "resource_name", nullable = false)
    private String resourceName;

    @Column(name = "endpoint_path", nullable = false)
    private String endpointPath;

    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}

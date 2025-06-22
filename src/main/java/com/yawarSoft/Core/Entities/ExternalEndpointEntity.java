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

    // FK al sistema externo
    @ManyToOne
    @JoinColumn(name = "id_external_system", nullable = false)
    private ExternalSystemEntity externalSystem;

    // Ahora NO existe id_blood_bank en esta tabla
    // Si antes lo tenías, debes ELIMINARLO.

    @Column(name = "path_base", nullable = false)
    private String pathBase;

    @Column(name = "resource_name", nullable = false, length = 64)
    private String resourceName;

    @Column(name = "interaction_type", nullable = false, length = 32)
    private String interactionType;

    @Column(name = "parameters_template", columnDefinition = "jsonb")
    private String parametersTemplate;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}

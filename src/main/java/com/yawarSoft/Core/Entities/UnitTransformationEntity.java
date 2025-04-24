package com.yawarSoft.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "unit_transformations")
public class UnitTransformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_origin_unit", nullable = false)
    private UnitEntity originUnit;

    @ManyToOne
    @JoinColumn(name = "id_generated_unit", nullable = false)
    private UnitEntity generatedUnit;

    @Column(name = "transformation_date", nullable = false)
    private LocalDateTime transformationDate;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private UserEntity updatedBy; //

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

package com.yawarSoft.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "interview_question_structures")
public class InterviewQuestionStructureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false, columnDefinition = "jsonb")
    private String questions; // Contenido JSONB como texto plano (puedes usar ObjectMapper si necesitas manipularlo)

    @Column(length = 20, nullable = false)
    private String status;
}

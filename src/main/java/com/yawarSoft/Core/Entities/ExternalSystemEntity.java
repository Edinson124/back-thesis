package com.yawarSoft.Core.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "external_systems")
public class ExternalSystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "base_url", nullable = false)
    private String baseUrl;

    @Column(name = "auth_url", nullable = false)
    private String authUrl;

    @Column(name = "auth_method", nullable = false)
    private String authMethod;

    @Column(name = "auth_body_template", nullable = false, columnDefinition = "jsonb")
    private String authBodyTemplate;

    @Column(name = "auth_headers", columnDefinition = "jsonb")
    private String authHeaders ;

    @Column(name = "auth_credentials_encrypted", nullable = false)
    private String authCredentialsEncrypted;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "token_expires_at")
    private LocalDateTime tokenExpiresAt;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}

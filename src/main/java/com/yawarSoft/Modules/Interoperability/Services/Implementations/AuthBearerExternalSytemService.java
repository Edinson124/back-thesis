package com.yawarSoft.Modules.Interoperability.Services.Implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yawarSoft.Core.Entities.ExternalSystemEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.LoginExternalSystemService;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AuthBearerExternalSytemService implements LoginExternalSystemService {

    private final AESGCMEncryptionUtil aesGCMEncryptionUtil;

    public AuthBearerExternalSytemService(AESGCMEncryptionUtil aesGCMEncryptionUtil) {
        this.aesGCMEncryptionUtil = aesGCMEncryptionUtil;
    }

    @Override
    public String obtainToken(ExternalSystemEntity externalSystem) {
        try {
            String decrypted = aesGCMEncryptionUtil.decrypt(externalSystem.getAuthCredentialsEncrypted());
            String[] parts = decrypted.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Credenciales no válidas para el sistema externo " + externalSystem.getId());
            }
            String username = parts[0];
            String password = parts[1];
            String finalBody = externalSystem.getAuthBodyTemplate()
                    .replace("{{user}}", username)
                    .replace("{{pass}}", password);

            HttpHeaders headers = buildHeaders(externalSystem.getAuthHeaders());
            return callAuthEndpoint(externalSystem.getAuthUrl(), externalSystem.getAuthMethod(), finalBody, headers);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener token para sistema externo " + externalSystem.getId(), e);
        }
    }

    private HttpHeaders buildHeaders(String authHeaders) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        if (authHeaders != null) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> authHeaderMap = mapper.readValue(authHeaders, new TypeReference<>() {
            });
            authHeaderMap.forEach(headers::add);
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String callAuthEndpoint(String authUrl, String authMethod, String finalBody, HttpHeaders headers) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(finalBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(authUrl,
                HttpMethod.valueOf(authMethod),
                entity,
                Map.class);
        if (response.getBody() != null && response.getBody().get("token") != null) {
            return response.getBody().get("token").toString();
        } else {
            throw new RuntimeException("No se pudo obtener el token de autenticación para " + authUrl);
        }
    }
}

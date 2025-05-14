package com.yawarSoft.Core.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

@Service
public class AwsParameterService {

    private final SsmClient ssmClient;

    public AwsParameterService(SsmClient ssmClient) {
        this.ssmClient = ssmClient;
    }

    public String getParameterValue(String parameterName) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name(parameterName)
                .withDecryption(true)  // Si el parámetro está cifrado
                .build();

        GetParameterResponse response = ssmClient.getParameter(request);

        return response.parameter().value();
    }
}

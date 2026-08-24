package academy.devdojo.service;

import academy.devdojo.config.BrasilApiConfigurationProperties;
import academy.devdojo.dto.response.CepGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class BrasilApiService {
    private final RestClient.Builder brasilApiClient;
    private final BrasilApiConfigurationProperties brasilApiConfigurationProperties;

    public CepGetResponse findCep(String cep){
        return brasilApiClient.build()
                .get()
                .uri(brasilApiConfigurationProperties.cepUri(), cep)
                .retrieve()
                .body(CepGetResponse.class);
    }
}

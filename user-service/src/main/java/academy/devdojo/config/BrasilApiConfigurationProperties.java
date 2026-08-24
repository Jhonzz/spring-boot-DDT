package academy.devdojo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "brasil-api")
public record BrasilApiConfigurationProperties(String baseUrl, String cepUri) {
    // RestTemplate (old and probably deprecated), WebClient(reactive), RestClient (RestTemplate but with more performance and less code), Apache Camel etc.
}

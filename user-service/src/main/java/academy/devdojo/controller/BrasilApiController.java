package academy.devdojo.controller;

import academy.devdojo.config.BrasilApiConfigurationProperties;
import academy.devdojo.dto.response.CepGetResponse;
import academy.devdojo.service.BrasilApiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/brasil-api/cep")
@Log4j2
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
public class BrasilApiController {
    private final BrasilApiService service;

    @GetMapping("/{cep}")
    public ResponseEntity<CepGetResponse> findByCep(@PathVariable String cep){
        log.info("request received to find cep: {}", cep);
        var cepGetResponse = service.findCep(cep);

        return ResponseEntity.ok(cepGetResponse);
    }
}

package academy.devdojo.controller;

import academy.devdojo.DTO.request.ProducerPostRequest;
import academy.devdojo.DTO.response.ProducerGetResponse;
import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;
    @GetMapping()
    public List<Producer> listAllProducers(@RequestParam(required = false) String producerName) {
        if (producerName == null) {
            return Producer.getProducers();
        }
        return Producer.getProducers().stream().filter(producer -> producerName.equalsIgnoreCase(producer.getName())).toList();
    }

    @GetMapping("{id}")
    public Producer findById(@PathVariable Long id) {
        return Producer.
                getProducers().
                stream().
                filter(Producer -> id.equals(Producer.getId())).
                findFirst().
                orElse(null);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> saveAnime(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {

        Producer producer = MAPPER.toProducer(producerPostRequest);
        ProducerGetResponse response = MAPPER.toProducerGetResponse(producer);

        Producer.getProducers().add(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

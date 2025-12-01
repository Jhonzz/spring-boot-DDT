package academy.devdojo.controller;

import academy.devdojo.DTO.request.ProducerPostRequest;
import academy.devdojo.DTO.request.ProducerPutRequest;
import academy.devdojo.DTO.response.ProducerGetResponse;
import academy.devdojo.domain.Anime;
import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<ProducerGetResponse>> listAllProducers(@RequestParam(required = false) String producerName) {

        List<ProducerGetResponse> producerGetResponseList = MAPPER.toProducerGetResponseList(Producer.getProducers());
        if (producerName == null) return ResponseEntity.ok(producerGetResponseList);

        List<Producer> producerList = Producer.getProducers().stream().filter(producer -> producerName.equalsIgnoreCase(producer.getName())).toList();
        List<ProducerGetResponse> response = MAPPER.toProducerGetResponseList(producerList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {

        Producer producer = Producer.
                getProducers().
                stream().
                filter(producers -> id.equals(producers.getId())).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

        return ResponseEntity.ok(MAPPER.toProducerGetResponse(producer));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> saveAnime(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {

        Producer producer = MAPPER.toProducer(producerPostRequest);
        ProducerGetResponse response = MAPPER.toProducerGetResponse(producer);

        Producer.getProducers().add(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Deleting producer by id: {}", id);
        Producer producerToDelete = Producer.getProducers().
                stream().
                filter(producers -> id.equals(producers.getId())).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
        Producer.getProducers().remove(producerToDelete);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ProducerPutRequest> update(@RequestBody ProducerPutRequest request) {
        log.info("Trying update producer by id: {}", request.getId());

        var producerToRemove = Producer.getProducers().
                stream().
                filter(producer -> request.getId().equals(producer.getId())).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

        Producer.getProducers().remove(producerToRemove);
        Producer producerUpdated = MAPPER.toProducer(request, producerToRemove.getCreatedAt());
        Producer.getProducers().add(producerUpdated);

        return ResponseEntity.ok(request);
    }
}

package academy.devdojo.producer.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.producer.DTO.request.ProducerPostRequest;
import academy.devdojo.producer.DTO.request.ProducerPutRequest;
import academy.devdojo.producer.DTO.response.ProducerGetResponse;
import academy.devdojo.producer.DTO.response.ProducerPostResponse;
import academy.devdojo.producer.DTO.response.ProducerPutResponse;
import academy.devdojo.producer.mapper.ProducerMapper;
import academy.devdojo.producer.service.ProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@RequiredArgsConstructor
@Slf4j
public class ProducerController {
    private final ProducerMapper MAPPER;
    private final ProducerService producerService;

    @GetMapping()
    public ResponseEntity<List<ProducerGetResponse>> findAll(@RequestParam(required = false) String producerName) {

        var producerList = producerService.findAll(producerName);
        var response = MAPPER.toProducerGetResponseList(producerList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {

        Producer producer = producerService.findByIdOrThrowNotFound(id);

        return ResponseEntity.ok(MAPPER.toProducerGetResponse(producer));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody @Valid ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {

        Producer producer = MAPPER.toProducer(producerPostRequest);

        producerService.save(producer);

        ProducerPostResponse response = MAPPER.toProducerPostResponse(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Deleting producer by id: {}", id);

        producerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ProducerPutResponse> update(@RequestBody @Valid ProducerPutRequest request) {
        log.info("Trying update producer by id: {}", request.getId());

        Producer producer = MAPPER.toProducer(request);
        producerService.update(producer);
        ProducerPutResponse response = MAPPER.toProducerPutResponse(producer);

        return ResponseEntity.ok(response);
    }
}

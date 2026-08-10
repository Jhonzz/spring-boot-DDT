package academy.devdojo.producer.controller;

import academy.devdojo.api.ProducerControllerApi;
import academy.devdojo.domain.Producer;
import academy.devdojo.dto.*;
import academy.devdojo.producer.mapper.ProducerMapper;
import academy.devdojo.producer.service.ProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@RequiredArgsConstructor
@Slf4j
public class ProducerController implements ProducerControllerApi {
    private final ProducerMapper MAPPER;
    private final ProducerService producerService;

    @GetMapping()
    public ResponseEntity<List<ProducerGetResponse>> findAllProducers(@RequestParam(required = false) String producerName) {

        var producerList = producerService.findAll(producerName);
        var response = MAPPER.toProducerGetResponseList(producerList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> findProducerById(@PathVariable Long id) {

        Producer producer = producerService.findByIdOrThrowNotFound(id);

        return ResponseEntity.ok(MAPPER.toProducerGetResponse(producer));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")
    @Override
    public ResponseEntity<ProducerPostResponse> saveProducer(@RequestBody @Valid ProducerPostRequest producerPostRequest) {

        Producer producer = MAPPER.toProducer(producerPostRequest);

        producerService.save(producer);

        ProducerPostResponse response = MAPPER.toProducerPostResponse(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProducerById(@PathVariable Long id) {
        log.info("Deleting producer by id: {}", id);

        producerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ProducerPutResponse> updateProducer(@RequestBody @Valid ProducerPutRequest request) {
        log.info("Trying update producer by id: {}", request.getId());

        Producer producer = MAPPER.toProducer(request);
        producerService.update(producer);
        ProducerPutResponse response = MAPPER.toProducerPutResponse(producer);

        return ResponseEntity.ok(response);
    }
}

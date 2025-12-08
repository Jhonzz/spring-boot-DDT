package academy.devdojo.controller;

import academy.devdojo.DTO.request.ProducerPostRequest;
import academy.devdojo.DTO.request.ProducerPutRequest;
import academy.devdojo.DTO.response.ProducerGetResponse;
import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.service.ProducerService;
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
    private ProducerService producerService;
    public ProducerController(){
        this.producerService = new ProducerService();
    }

    @GetMapping()
    public ResponseEntity<List<ProducerGetResponse>> listAll(@RequestParam(required = false) String producerName) {

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
    public ResponseEntity<ProducerGetResponse> saveAnime(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {

        Producer producer = MAPPER.toProducer(producerPostRequest);
        ProducerGetResponse response = MAPPER.toProducerGetResponse(producer);

        producerService.save(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Deleting producer by id: {}", id);

        producerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ProducerPutRequest> update(@RequestBody ProducerPutRequest request) {
        log.info("Trying update producer by id: {}", request.getId());

        Producer producer = MAPPER.toProducer(request);
        producerService.update(producer);

        return ResponseEntity.ok(request);
    }
}

package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {

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
    public Producer saveAnime(@RequestBody Producer producer, @RequestHeader HttpHeaders headers) {
        log.info("HEADERS>>>>>>>>>>>>>>> {}", headers);
        producer.setId(ThreadLocalRandom.current().nextLong(1, 1000));
        Producer.getProducers().add(producer);
        return producer;
    }
}

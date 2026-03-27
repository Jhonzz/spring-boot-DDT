package academy.devdojo.producer.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.exception.NotFoundException;
import academy.devdojo.producer.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ProducerRepository repository;

    public List<Producer> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Producer findByIdOrThrowNotFound(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Producer not found"));
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public void delete(Long id) {
        var producer = findByIdOrThrowNotFound(id);
        repository.delete(producer);
    }

    public Producer update(Producer producerToUpdate) {
        assertProducerExists(producerToUpdate.getId());
        producerToUpdate.setCreatedAt(producerToUpdate.getCreatedAt());
        return repository.save(producerToUpdate);
    }

    public void assertProducerExists(Long id){
        findByIdOrThrowNotFound(id);
    }
}

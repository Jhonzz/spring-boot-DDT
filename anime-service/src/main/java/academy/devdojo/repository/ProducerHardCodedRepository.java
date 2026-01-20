package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import externalDependency.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Log4j2
public class ProducerHardCodedRepository {
    private final ProducerData producerData;

    public List<Producer> findAll() {
        return producerData.getProducers();
    }

    public Optional<Producer> findById(Long id) {
        return producerData.getProducers().stream().filter(producer -> id.equals(producer.getId())).findFirst();
    }

    public List<Producer> findByName(String name) {
        return producerData.getProducers().stream().filter(p -> name != null && name.equalsIgnoreCase(p.getName())).toList();
    }

    public Producer save(Producer producer) {
        producerData.getProducers().add(producer);
        return producer;
    }

    public void delete(Producer producer) {
        producerData.getProducers().remove(producer);
    }

    public Producer update(Producer producer) {
        delete(producer);
        save(producer);
        return producer;
    }
}

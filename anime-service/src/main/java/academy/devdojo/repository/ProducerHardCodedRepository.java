package academy.devdojo.repository;

import academy.devdojo.config.ConnectionConfiguration;
import academy.devdojo.domain.Producer;
import externalDependency.Connection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Log4j2
public class ProducerHardCodedRepository {
    @Getter
    private static final List<Producer> PRODUCERS = new ArrayList<>();

    @Qualifier(value = "connectionMySql") //Will search for a bean or function with this name
    private final Connection connection;

    static {
        var mappa = Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var saru = Producer.builder().id(2L).name("Saru").createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        PRODUCERS.addAll(List.of(mappa, saru, madhouse));
    }

    public List<Producer> findAll() {
        return PRODUCERS;
    }

    public Optional<Producer> findById(Long id) {
        return PRODUCERS.stream().filter(producers -> id.equals(producers.getId())).findFirst();
    }

    public List<Producer> findByName(String name){
        log.debug(connection);
        return PRODUCERS.stream().filter(producer -> name.equalsIgnoreCase(producer.getName())).toList();
    }

    public Producer save(Producer producer){
        PRODUCERS.add(producer);
        return producer;
    }
    public void delete(Producer producer){
        PRODUCERS.remove(producer);
    }
    public Producer update(Producer producer){
        delete(producer);
        save(producer);
        return producer;
    }
}

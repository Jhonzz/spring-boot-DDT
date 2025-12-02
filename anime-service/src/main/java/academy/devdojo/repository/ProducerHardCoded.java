package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerHardCoded {
    @Getter
    private static final List<Producer> PRODUCERS = new ArrayList<>();

    static {
        var mappa = Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var saru = Producer.builder().id(2L).name("Saru").createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        PRODUCERS.addAll(List.of(mappa, saru, madhouse));
    }

    public static List<Producer> findAll() {
        return PRODUCERS;
    }

    public Optional<Producer> findById(Long id) {
        return PRODUCERS.stream().filter(producers -> id.equals(producers.getId())).findFirst();
    }

    public List<Producer> findByName(String name){
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

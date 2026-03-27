package academy.devdojo.producer.repository;

import academy.devdojo.domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    List<Producer> findByName(String name);
}

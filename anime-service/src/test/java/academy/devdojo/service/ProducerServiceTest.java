package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {
    @InjectMocks
    private ProducerService service;
    @Mock
    private ProducerHardCodedRepository repository;
    private List<Producer> producerList;

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
        producerList = new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }

    @Test
    @DisplayName("FindAll returns a list of producers when parameter is null")
    @Order(1)
    void findAll_ReturnsAll_WhenParameterIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(producerList);

        var producers = service.findAll(null);
        Assertions.assertThat(producers).isNotEmpty().hasSameElementsAs(producerList);
    }

    @Test
    @DisplayName("FindAll returns all producers when null")
    @Order(2)
    void findByName_ReturnsProducer_WhenParameterIsNotNull() {
        var producer = producerList.getFirst();
        var expectedProducer = Collections.singletonList(producer);
        BDDMockito.when(repository.findByName(producer.getName())).thenReturn(expectedProducer);

        var producersFound = service.findAll(producer.getName());
        Assertions.assertThat(producersFound).containsAll(expectedProducer);
    }

    @Test
    @DisplayName("FindAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound(){
        var name = "not_found";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var emptyList = service.findAll(name);
        Assertions.assertThat(emptyList).isNotNull().isEmpty();
    }
}
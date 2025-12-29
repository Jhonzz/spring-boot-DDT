package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodedRepositoryTest {

    @InjectMocks
    private ProducerHardCodedRepository repository;
    @Mock
    private ProducerData producerData;
    private List<Producer> producerList;

    @BeforeEach
    void init(){
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
        producerList = new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }

    @Test
    @DisplayName("findAll returns a list with all producers")
    @Order(1)
    void findAll_returnsAllProducers_WhenSuccessfull(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);
    }
    @Test
    @DisplayName("findById returns a producer with given id")
    @Order(2)
    void findById_returnsProducerById_WhenSuccessfull(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var expectedProducer = producerList.getFirst();
        var producerFound = repository.findById(expectedProducer.getId());

        Assertions.assertThat(producerFound).isPresent().contains(expectedProducer);
    }
    @Test
    @DisplayName("findByName returns empty list when name is null")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var producers = repository.findByName(null);

        Assertions.assertThat(producers).isNotNull().isEmpty();
    }
    @Test
    @DisplayName("findByName returns a list with found producer when name exists")
    @Order(4)
    void findByName_ReturnsFoundProducerInList_WhenNameIsFound(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var expectedProducer = producerList.getFirst();
        var producers = repository.findByName(expectedProducer.getName());

        Assertions.assertThat(producers).contains(expectedProducer);
    }
    @Test
    @DisplayName("save creates a producer")
    @Order(5)
    void save_CreatesProducer_WhenSucessful(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var producerTosave = Producer.builder().id(99L).name("MAPPA").createdAt(LocalDateTime.now()).build();
        var producer = repository.save(producerTosave);

        Assertions.assertThat(producer).isEqualTo(producerTosave).hasNoNullFieldsOrProperties();
        Optional<Producer> producerSavedOptional = repository.findById(producerTosave.getId());

        Assertions.assertThat(producerSavedOptional).isPresent().contains(producerTosave);
    }
    @Test
    @DisplayName("update updates a producer")
    @Order(7)
    void update_UpdateProducer_WhenSucessful(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producerToUpdate = producerList.getFirst();
        producerToUpdate.setName("Madhouse");
        repository.update(producerToUpdate);

        Assertions.assertThat(producerList).contains(producerToUpdate);

        var producerUpdatedOptional = repository.findById(producerToUpdate.getId());

        Assertions.assertThat(producerUpdatedOptional).isPresent();
        Assertions.assertThat(producerUpdatedOptional.get().getName()).isEqualTo(producerToUpdate.getName());
    }


}
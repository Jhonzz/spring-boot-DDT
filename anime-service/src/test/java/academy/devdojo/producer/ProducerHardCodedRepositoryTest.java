package academy.devdojo.producer;

import academy.devdojo.commons.ProducerUtils;
import academy.devdojo.domain.Producer;
import academy.devdojo.producer.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @InjectMocks
    private ProducerUtils producerUtils;

    @BeforeEach
    void init(){
        producerList = producerUtils.newProducerList();
    }

    @Test
    @DisplayName("findAll returns a list with all producers")
    @Order(1)
    void findAll_returnsAllProducers_WhenSuccessful(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);
    }
    @Test
    @DisplayName("findById returns a producer with given id")
    @Order(2)
    void findById_returnsProducerById_WhenSuccessful(){
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

        var producerTosave = producerUtils.newProducerToSave();
        var producer = repository.save(producerTosave);

        Assertions.assertThat(producer).isEqualTo(producerTosave).hasNoNullFieldsOrProperties();
        Optional<Producer> producerSavedOptional = repository.findById(producerTosave.getId());

        Assertions.assertThat(producerSavedOptional).isPresent().contains(producerTosave);
    }
    @Test
    @DisplayName("update updates a producer")
    @Order(6)
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

    @Test
    @DisplayName("delete removes a producer")
    @Order(7)
    void delete_RemoveProducer_WhenSuccessful(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var producerToDelete = producerList.getFirst();
        repository.delete(producerToDelete);

        var producers = repository.findAll();

        Assertions.assertThat(producers).isNotEmpty().doesNotContain(producerToDelete);
    }



}
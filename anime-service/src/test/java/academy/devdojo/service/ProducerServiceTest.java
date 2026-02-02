package academy.devdojo.service;

import academy.devdojo.commons.ProducerUtils;
import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {
    @InjectMocks
    private ProducerService service;
    @Mock
    private ProducerHardCodedRepository repository;
    private List<Producer> producerList;
    @InjectMocks
    private ProducerUtils producerUtils;

    @BeforeEach
    void init() {
        producerList = producerUtils.newProducerList();
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

    @Test
    @DisplayName("findById returns a producer when given id")
    @Order(4)
    void findById_ReturnsProducer_WhenSucessful(){
        var expectedProducer = producerList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));

        var producer = service.findByIdOrThrowNotFound(expectedProducer.getId());
        Assertions.assertThat(producer).isEqualTo(expectedProducer);
    }
    @Test
    @DisplayName("findById throws ResponseStatusException when producer is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound(){
        var expectedProducer = producerList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates a producer")
    @Order(6)
    void save_CreatesAProducer_WhenSucessful(){
        var producerToSave = producerUtils.newProducerToSave();
        BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);

        var savedProducer = service.save(producerToSave);

        Assertions.assertThat(savedProducer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
    }
    @Test
    @DisplayName("delete removes a producer")
    @Order(7)
    void delete_RemovesAProducer_WhenSucessful(){
        var producerToDelete = producerList.getFirst();
        BDDMockito.when(repository.findById(producerToDelete.getId())).thenReturn(Optional.of(producerToDelete));


        Assertions.assertThatNoException().isThrownBy(() -> service.delete(producerToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound(){
        var producerToDelete = producerList.getFirst();
        BDDMockito.when(repository.findById(producerToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(producerToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("Update updates a producer")
    @Order(9)
    void update_UpdatesProducer_WhenSucessful(){
        var producerToUpdate = producerList.getFirst();
        producerToUpdate.setName("SARU");

        BDDMockito.when(repository.findById(producerToUpdate.getId())).thenReturn(Optional.of(producerToUpdate));

        service.update(producerToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(producerToUpdate));
    }

    @Test
    @DisplayName("Update throws ResponseStatusException when producer is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound(){
        var producerToUpdate = producerList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(producerToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }
}
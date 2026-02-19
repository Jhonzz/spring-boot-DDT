package academy.devdojo.repository;

import academy.devdojo.commons.UserUtils;
import academy.devdojo.domain.User;
import academy.devdojo.service.UserData;
import org.assertj.core.api.Assert;
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
class UserHardCodedRepositoryTest {

    @InjectMocks
    private UserHardCodedRepository repository;
    @Mock
    private UserData userData;
    private List<User> userList;
    @InjectMocks
    private UserUtils userUtils;

    @BeforeEach
    void init(){
        userList = userUtils.newUserList();
    }

    @Test
    @DisplayName("findAll returns a list with all users")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenSuccessful(){
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var users = repository.findAll();
        Assertions.assertThat(users).isNotNull().hasSameElementsAs(userList);
    }

    @Test
    @DisplayName("findById returns a User with given id")
    @Order(2)
    void findById_ReturnsUser_WhenSucessful(){
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var expectedUser = userList.getFirst();
        var user = repository.findByid(expectedUser.getId());
        Assertions.assertThat(user).isPresent().contains(expectedUser);
    }

    @Test
    @DisplayName("findByFirstName returns a empty list when first name is null")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenFirstNameIsNull(){
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var expectedUser = repository.findByFirstName(null);
        Assertions.assertThat(expectedUser).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByFirstName returns list with found user when first name exists")
    @Order(4)
    void findByName_ReturnsListWithUserFound_WhenSucessful(){
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var expectedUser = userList.getFirst();
        var users = repository.findByFirstName(expectedUser.getFirstName());
        Assertions.assertThat(users).isNotEmpty().contains(expectedUser);
    }

    @Test
    @DisplayName("save creates an user")
    @Order(5)
    void save_CreatesUser_WhenSucessful(){
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var userToSave = userUtils.userToSave();
        var user = repository.save(userToSave);

        Assertions.assertThat(user).isEqualTo(userToSave).hasNoNullFieldsOrProperties();

        var userSavedOptional = repository.findByid(user.getId());

        Assertions.assertThat(userSavedOptional).isPresent().contains(userToSave);
    }

    @Test
    @DisplayName("update updates an user")
    @Order(6)
    void update_UpdatesUser_WhenSuccessful(){
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        var userToUpdate = userList.getFirst();
        userToUpdate.setFirstName("Okarun");

        repository.update(userToUpdate);

        Assertions.assertThat(userList).contains(userToUpdate);

        var userFound = repository.findByid(userToUpdate.getId());

        Assertions.assertThat(userFound).isPresent();
        Assertions.assertThat(userFound.get().getFirstName()).isEqualTo(userToUpdate.getFirstName());
    }

    @Test
    @DisplayName("delete removes an user")
    @Order(7)
    void delete_RemovesUser_WhenSuccessful(){
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var userToDelete = userList.getFirst();
        repository.delete(userToDelete);

        var users = repository.findAll();

        Assertions.assertThat(users).isNotEmpty().doesNotContain(userToDelete);
    }
}
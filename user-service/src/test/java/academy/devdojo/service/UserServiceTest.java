package academy.devdojo.service;

import academy.devdojo.commons.UserUtils;
import academy.devdojo.domain.User;
import academy.devdojo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;
    
    @Mock
    private UserRepository repository;

    private List<User> userList;
    @InjectMocks
    private UserUtils userUtils;

    @BeforeEach
    void init(){
        userList = userUtils.newUserList();
    }

    @Test
    @DisplayName("findAll returns all users when firstName is null")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenFirstNameIsNull(){
        BDDMockito.when(repository.findAll()).thenReturn(userList);

        var users = service.findAll(null);
        Assertions.assertThat(users).isNotNull().hasSameElementsAs(userList);
    }

    @Test
    @DisplayName("findByName returns a list with the users found with the given firstName")
    @Order(2)
    void findAll_ReturnsFoundUser_WhenFirstNameIsNotNull(){
        var user = userList.getFirst();
        var expectedUser = Collections.singletonList(user);
        BDDMockito.when(repository.findByFirstNameIgnoreCase(user.getFirstName())).thenReturn(expectedUser);

        var usersFound = service.findAll(user.getFirstName());
        Assertions.assertThat(usersFound).contains(user);
    }

    @Test
    @DisplayName("findByName returns a empty list when firstName is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenFirstNameIsNotFound(){
        var name = "not_found";
        BDDMockito.when(repository.findByFirstNameIgnoreCase(name)).thenReturn(Collections.emptyList());

        var userNotFound = service.findAll(name);
        Assertions.assertThat(userNotFound).isEmpty();
    }

    @Test
    @DisplayName("findById returns a user with given id")
    @Order(4)
    void findById_ReturnsUser_WhenParameterIsNotNull(){
        var expectedUser = userList.getFirst();
        BDDMockito.when(repository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));

        var user = service.findByIdOrNotFoundException(expectedUser.getId());
        Assertions.assertThat(user).isNotNull().isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("findById throws ResponseStatusException when user is not found")
    @Order(5)
    void findById_ThrowsReponseStatusException_WhenUserIsNotFound(){
        var expectedUser = userList.getFirst();
        BDDMockito.when(repository.findById(expectedUser.getId())).thenReturn(Optional.empty());

        Assertions.
                assertThatThrownBy(() -> service.findByIdOrNotFoundException(expectedUser.getId())).
                isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates an user")
    @Order(6)
    void save_CreatesUser_WhenSuccessful(){
        var userToSave = userUtils.userToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(userToSave);

        var savedUser = repository.save(userToSave);
        Assertions.assertThat(savedUser).isEqualTo(userToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes an user")
    @Order(7)
    void delete_RemovesUser_WhenSuccessful(){
        var expectedUser = userList.getFirst();
        BDDMockito.when(repository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(expectedUser.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException 404 when user is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenUserNotFound(){
        var expectedUser = userList.getFirst();
        BDDMockito.when(repository.findById(expectedUser.getId())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.delete(expectedUser.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update updates an user")
    @Order(9)
    void update_UpdatesUser_WhenSuccessful(){
        var userToUpdate = userList.getFirst();
        userToUpdate.setFirstName("Okarun");

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(userToUpdate));
        BDDMockito.when(repository.save(userToUpdate)).thenReturn(userToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(userToUpdate));
    }

    @Test
    @DisplayName("update throws ResponseStatusException when user is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenUserNotFound(){
        var user = userList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(user))
                .isInstanceOf(ResponseStatusException.class);
    }

}
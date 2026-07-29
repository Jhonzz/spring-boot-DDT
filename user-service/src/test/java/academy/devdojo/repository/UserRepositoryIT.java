package academy.devdojo.repository;

import academy.devdojo.commons.UserUtils;
import academy.devdojo.config.IntegrationTestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Jpa won't replace the in memory database
@Import(UserUtils.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Transactional(propagation = Propagation.NOT_SUPPORTED) //it wont rollback when do a test before execute next test
public class UserRepositoryIT extends IntegrationTestConfig {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserUtils userUtils;

    @Test
    @DisplayName("save creates an user")
    @Order(1)
    void save_CreatesUser_WhenSuccessful() {
        var userToSave = userUtils.userToSave();
        var savedUser = repository.save(userToSave);

        Assertions.assertThat(savedUser).hasNoNullFieldsOrProperties();
        Assertions.assertThat(savedUser.getId()).isEqualTo(4L);
    }

    @Test
    @DisplayName("findAll returns a list with all")
    @Order(2)
    @Sql("/sql/user/init_one_user.sql")
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        var users = repository.findAll();
        Assertions.assertThat(users).isNotEmpty();
    }
}
package academy.devdojo.service;

import academy.devdojo.commons.ProfileUtils;
import academy.devdojo.commons.UserProfileUtils;
import academy.devdojo.commons.UserUtils;
import academy.devdojo.domain.UserProfile;
import academy.devdojo.repository.UserProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserProfileServiceTest {
    @InjectMocks
    private UserProfileService service;
    @Mock
    private UserProfileRepository repository;
    private List<UserProfile> userProfileList;
    @InjectMocks
    private UserProfileUtils userProfileUtils;
    @Spy //it works as an autowired, but using mockito
    private UserUtils userUtils;
    @Spy
    private ProfileUtils profileUtils;

    @BeforeEach
    void init(){
        userProfileList = userProfileUtils.newUserProfileList();
    }

    @Test
    @DisplayName("findAll returns all user profiles")
    @Order(1)
    void findAll_ReturnsAllUserProfiles_WhenSuccessful(){
        BDDMockito.when(repository.retrieveAll()).thenReturn(userProfileList);

        var userProfiles = service.findAll();
        Assertions.assertThat(userProfiles)
                .isNotNull()
                .hasSameElementsAs(userProfileList);
        userProfiles.forEach(userProfile -> Assertions.assertThat(userProfile).hasNoNullFieldsOrProperties());
    }

    @Test
    @DisplayName("findAllUsersByProfileId returns all users by profile id")
    @Order(2)
    void findUsersByProfileId_ReturnsAllUsersWithGivenProfileId_WhenSuccessful(){
        var profileId = 99L;
        var usersByProfile = userProfileList
                .stream()
                .filter(userProfile -> userProfile.getProfile().getId().equals(profileId)).map(UserProfile::getUser)
                .toList();

        BDDMockito.when(repository.findAllUsersByProfileId(profileId)).thenReturn(usersByProfile);

        var users = service.findAllUsersByProfileId(profileId);

        Assertions.assertThat(users).hasSize(1)
                .doesNotContainNull()
                .hasSameElementsAs(users);

        users.forEach(user -> Assertions.assertThat(user).hasNoNullFieldsOrProperties().isNotNull());
    }
}

package academy.devdojo.service;

import academy.devdojo.commons.ProfileUtils;
import academy.devdojo.domain.Profile;
import academy.devdojo.mapper.PasswordEncoderMapper;
import academy.devdojo.repository.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileServiceTest {
    @InjectMocks
    private ProfileService service;
    @Mock
    private ProfileRepository repository;
    private List<Profile> profileList;
    @InjectMocks
    private ProfileUtils profileUtils;

    @BeforeEach
    void init(){
        profileList = profileUtils.newProfileList();
    }

    @Test
    @DisplayName("findAll returns all profiles")
    @Order(1)
    void findAll_ReturnsAllProfiles_WhenSuccessful(){
        BDDMockito.when(repository.findAll()).thenReturn(profileList);

        var expectedProfiles = service.findAll();
        Assertions.assertThat(expectedProfiles).isNotNull().hasSameElementsAs(profileList);
    }

    @Test
    @DisplayName("save creates profile")
    @Order(2)
    void save_CreatesProfile_WhenSuccessful(){
        var newProfile = profileUtils.newProfileToSave();
        var profileSaved = profileUtils.newProfileSaved();

        BDDMockito.when(repository.save(newProfile)).thenReturn(profileSaved);

        var savedProfile = repository.save(newProfile);

        Assertions.assertThat(savedProfile).isEqualTo(profileSaved).hasNoNullFieldsOrProperties();
    }
}

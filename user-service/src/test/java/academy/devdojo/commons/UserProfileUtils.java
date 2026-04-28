package academy.devdojo.commons;

import academy.devdojo.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserProfileUtils {
    private final UserUtils userUtils;
    private final ProfileUtils profileUtils;

    public List<UserProfile> newUserProfileList() {
        var regularUserSaved = newUserprofileSaved();
        return new ArrayList<>(List.of(regularUserSaved));
    }

     public UserProfile newUserprofileToSave(){
       return UserProfile.builder()
               .profile(profileUtils.newProfileSaved())
               .user(userUtils.newUserSaved())
               .build();
     }

    public UserProfile newUserprofileSaved(){
        return UserProfile.builder()
                .id(1L)
                .profile(profileUtils.newProfileSaved())
                .user(userUtils.newUserSaved())
                .build();
    }
}

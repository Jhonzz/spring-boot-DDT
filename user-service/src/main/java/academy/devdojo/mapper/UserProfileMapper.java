package academy.devdojo.mapper;

import academy.devdojo.dto.response.UserProfileGetResponse;
import academy.devdojo.dto.response.UserProfileUserGetResponse;
import academy.devdojo.domain.User;
import academy.devdojo.domain.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {
    List<UserProfileGetResponse> toUserProfileGetResponse(List<UserProfile> userProfileList);

    List<UserProfileUserGetResponse> toUserProfileUserGetResponse(List<User> userList);
}

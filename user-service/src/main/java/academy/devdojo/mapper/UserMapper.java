package academy.devdojo.mapper;

import academy.devdojo.annotation.EncodedMapping;
import academy.devdojo.domain.User;
import academy.devdojo.dto.request.UserPostRequest;
import academy.devdojo.dto.request.UserPutRequest;
import academy.devdojo.dto.response.UserGetResponse;
import academy.devdojo.dto.response.UserPostResponse;
import academy.devdojo.dto.response.UserPutResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
uses = PasswordEncoderMapper.class)
public interface UserMapper {
    @Mapping(target = "roles", constant = "USER")
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User toUser(UserPostRequest userPostRequest);

    User toUser(UserPutRequest request);

    UserPutResponse toUserPutResponse(User user);

    UserPostResponse toUserPostResponse(User user);

    List<UserGetResponse> toUserGetResponseList(List<User> users);

    UserGetResponse toUserResponse(User user);

    @Mapping(target = "password", source = "rawPassword", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "roles", source = "savedUser.roles")
    @Mapping(target = "id", source = "userToUpdate.id")
    @Mapping(target = "firstName", source = "userToUpdate.firstName")
    @Mapping(target = "lastName", source = "userToUpdate.lastName")
    @Mapping(target = "email", source = "userToUpdate.email")
    User toUserWithPasswordAndRoles(User userToUpdate, String rawPassword, User savedUser);

    @AfterMapping
    default void setPasswordIfNull(@MappingTarget User user, String rawPassword, User savedUser){
        if (rawPassword == null){
            user.setPassword(savedUser.getPassword());
        }
    }
}

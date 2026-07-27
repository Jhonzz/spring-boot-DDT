package academy.devdojo.mapper;

import academy.devdojo.annotation.EncodedMapping;
import academy.devdojo.domain.User;
import academy.devdojo.dto.request.UserPostRequest;
import academy.devdojo.dto.request.UserPutRequest;
import academy.devdojo.dto.response.UserGetResponse;
import academy.devdojo.dto.response.UserPostResponse;
import academy.devdojo.dto.response.UserPutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
uses = PasswordEncoderMapper.class)
public interface UserMapper {
    @Mapping(target = "roles", constant = "USER")
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User toUser(UserPostRequest userPostRequest);

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User toUser(UserPutRequest request);

    UserPutResponse toUserPutResponse(User user);

    UserPostResponse toUserPostResponse(User user);

    List<UserGetResponse> toUserGetResponseList(List<User> users);

    UserGetResponse toUserResponse(User user);
}

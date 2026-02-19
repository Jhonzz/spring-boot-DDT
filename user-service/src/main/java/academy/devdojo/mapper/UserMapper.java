package academy.devdojo.mapper;

import academy.devdojo.DTO.request.UserPostRequest;
import academy.devdojo.DTO.request.UserPutRequest;
import academy.devdojo.DTO.response.UserGetResponse;
import academy.devdojo.DTO.response.UserPostResponse;
import academy.devdojo.DTO.response.UserPutResponse;
import academy.devdojo.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    User toUser(UserPostRequest userPostRequest);

    User toUser(UserPutRequest request);

    UserPutResponse toUserPutResponse(User user);

    UserPostResponse toUserPostResponse(User user);

    List<UserGetResponse> toUserGetResponseList(List<User> users);

    UserGetResponse toUserResponse(User user);
}

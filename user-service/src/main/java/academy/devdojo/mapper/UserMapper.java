package academy.devdojo.mapper;

import academy.devdojo.dto.request.UserPostRequest;
import academy.devdojo.dto.request.UserPutRequest;
import academy.devdojo.dto.response.UserGetResponse;
import academy.devdojo.dto.response.UserPostResponse;
import academy.devdojo.dto.response.UserPutResponse;
import academy.devdojo.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserPostRequest userPostRequest);

    User toUser(UserPutRequest request);

    UserPutResponse toUserPutResponse(User user);

    UserPostResponse toUserPostResponse(User user);

    List<UserGetResponse> toUserGetResponseList(List<User> users);

    UserGetResponse toUserResponse(User user);
}

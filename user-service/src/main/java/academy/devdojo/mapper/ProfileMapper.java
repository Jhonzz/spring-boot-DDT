package academy.devdojo.mapper;

import academy.devdojo.DTO.request.ProfilePostRequest;
import academy.devdojo.DTO.response.ProfileGetResponse;
import academy.devdojo.DTO.response.ProfilePostResponse;
import academy.devdojo.domain.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {

    Profile toProfile(ProfilePostRequest profilePostRequest);

    ProfilePostResponse toProfilePostResponse(Profile profile);

    List<ProfileGetResponse> toProfileGetResponseList(List<Profile> profiles);
}

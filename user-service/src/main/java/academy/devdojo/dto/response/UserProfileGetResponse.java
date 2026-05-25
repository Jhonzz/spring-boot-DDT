package academy.devdojo.dto.response;

import lombok.Data;

@Data
public class UserProfileGetResponse {
    //won't be private because mapper will return error if private(needs to be public)
    public record User(Long id, String firstName) {
    }

    public record Profile(Long id, String name) {
    }

    private Long id;
    private User user;
    private Profile profile;
}

package academy.devdojo.controller;

import academy.devdojo.dto.response.UserProfileGetResponse;
import academy.devdojo.dto.response.UserProfileUserGetResponse;
import academy.devdojo.mapper.UserProfileMapper;
import academy.devdojo.service.UserProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/user-profiles")
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class UserProfileController {
    private final UserProfileService service;
    private final UserProfileMapper MAPPER;

    @GetMapping
    public ResponseEntity<List<UserProfileGetResponse>> findAll(){
        log.info("Request received to list all profiles");

        var userProfiles = service.findAll();
        var response = MAPPER.toUserProfileGetResponse(userProfiles);

        return ResponseEntity.ok(response);
    }

    @GetMapping("profiles/{id}/users")
    public ResponseEntity<List<UserProfileUserGetResponse>> findUsersByProfileId(@PathVariable Long id){
        log.info("Request received to list all users with given profile id '{}'", id);

        var foundUsers = service.findAllUsersByProfileId(id);
        var response = MAPPER.toUserProfileUserGetResponse(foundUsers);

        return ResponseEntity.ok(response);
    }
}

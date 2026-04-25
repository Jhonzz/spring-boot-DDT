package academy.devdojo.controller;

import academy.devdojo.DTO.response.UserProfileGetResponse;
import academy.devdojo.domain.UserProfile;
import academy.devdojo.mapper.UserProfileMapper;
import academy.devdojo.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/user-profiles")
@Slf4j
@RequiredArgsConstructor
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
}

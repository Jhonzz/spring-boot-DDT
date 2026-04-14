package academy.devdojo.controller;

import academy.devdojo.domain.UserProfile;
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

    @GetMapping
    public ResponseEntity<List<UserProfile>> findAll(){
        log.info("Request received to list all profiles");

        var userProfiles = service.findAll();

        return ResponseEntity.ok(userProfiles);
    }
}

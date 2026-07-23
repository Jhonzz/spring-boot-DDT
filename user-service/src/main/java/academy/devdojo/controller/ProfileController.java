package academy.devdojo.controller;

import academy.devdojo.dto.request.ProfilePostRequest;
import academy.devdojo.dto.response.ProfileGetResponse;
import academy.devdojo.dto.response.ProfilePostResponse;
import academy.devdojo.mapper.ProfileMapper;
import academy.devdojo.service.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/profiles")
@RequiredArgsConstructor
@Log4j2
@SecurityRequirement(name = "basicAuth")
public class ProfileController {
    private final ProfileService service;
    private final ProfileMapper MAPPER;

    @GetMapping
    private ResponseEntity<List<ProfileGetResponse>> findAll(){
        log.info("Sending request to find all profiles");

        var profiles = service.findAll();
        var profileGetResponseList = MAPPER.toProfileGetResponseList(profiles); //mapping to response DTO

        return ResponseEntity.ok(profileGetResponseList);
    }

    @PostMapping
    private ResponseEntity<ProfilePostResponse> save(@RequestBody @Valid ProfilePostRequest profilePostRequest){
        log.info("Creating new profile");

        var profile = MAPPER.toProfile(profilePostRequest);
        var profileToSave = service.save(profile);
        var profilePostResponse = MAPPER.toProfilePostResponse(profileToSave);

        return ResponseEntity.status(HttpStatus.CREATED).body(profilePostResponse);
    }
}

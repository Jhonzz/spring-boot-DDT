package academy.devdojo.controller;

import academy.devdojo.DTO.request.UserPostRequest;
import academy.devdojo.DTO.request.UserPutRequest;
import academy.devdojo.DTO.response.UserGetResponse;
import academy.devdojo.DTO.response.UserPostResponse;
import academy.devdojo.DTO.response.UserPutResponse;
import academy.devdojo.domain.User;
import academy.devdojo.mapper.UserMapper;
import academy.devdojo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping()
    private ResponseEntity<List<UserGetResponse>> findAll(@RequestParam(required = false) String firstName) {
        log.debug("Request received to list all users, param first name '{}'", firstName);

        var users = service.findAll(firstName);
        var response = mapper.toUserGetResponseList(users);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    private ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find User by id {}", id);

        var userFound = service.findByIdOrNotFoundException(id);
        var response = mapper.toUserResponse(userFound);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    private ResponseEntity<UserPostResponse> save(@RequestBody @Valid UserPostRequest request) {
        log.debug("Request to save user: {}", request);
        var userToSave = mapper.toUser(request);

        var savedUser = service.save(userToSave);

        var response = mapper.toUserPostResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    private ResponseEntity<UserPutResponse> update(@RequestBody @Valid UserPutRequest request) {
        var userToUpdate = mapper.toUser(request);

        service.update(userToUpdate);

        var response = mapper.toUserPutResponse(userToUpdate);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id){
        log.debug("Request to delete user: {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}

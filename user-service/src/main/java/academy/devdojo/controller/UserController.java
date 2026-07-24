package academy.devdojo.controller;

import academy.devdojo.dto.request.UserPostRequest;
import academy.devdojo.dto.request.UserPutRequest;
import academy.devdojo.dto.response.UserGetResponse;
import academy.devdojo.dto.response.UserPostResponse;
import academy.devdojo.dto.response.UserPutResponse;
import academy.devdojo.exception.ApiError;
import academy.devdojo.exception.DefaultErrorMessage;
import academy.devdojo.mapper.UserMapper;
import academy.devdojo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "User API", description = "User related endpoints")
@SecurityRequirement(name = "basicAuth")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find all users", description = "Find all users available in the system", //swagger
            responses = {
                    @ApiResponse(description = "List all users", //description for response
                            responseCode = "200", //status code response
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserGetResponse.class))) //mediaType and schema showed in response data
                    )
            })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserGetResponse>> findAll(@RequestParam(required = false) String firstName) {
        log.debug("Request received to list all users, param first name '{}'", firstName);

        var users = service.findAll(firstName);
        var response = mapper.toUserGetResponseList(users);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    @Operation(summary = "Find user by id",
            responses = {
                    @ApiResponse(description = "Find user by id",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserGetResponse.class)
                            )),
                    @ApiResponse(description = "User not found",
                            responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DefaultErrorMessage.class))
                    )}
    )
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find User by id {}", id);

        var userFound = service.findByIdOrNotFoundException(id);
        var response = mapper.toUserResponse(userFound);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Creates user",
            responses = {
                    @ApiResponse(description = "Save user in the database",
                            responseCode = "201",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserPostResponse.class))),
                    @ApiResponse(description = "Bad request",
                    responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class)))
            })
    public ResponseEntity<UserPostResponse> save(@RequestBody @Valid UserPostRequest request) {
        log.debug("Request to save user: {}", request);
        var userToSave = mapper.toUser(request);

        var savedUser = service.save(userToSave);

        var response = mapper.toUserPostResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<UserPutResponse> update(@RequestBody @Valid UserPutRequest request) {
        var userToUpdate = mapper.toUser(request);

        service.update(userToUpdate);

        var response = mapper.toUserPutResponse(userToUpdate);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Request to delete user: {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}

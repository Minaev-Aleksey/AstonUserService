package com.example.UserService.controller;

import com.example.UserService.dto.UserRequestDTO;
import com.example.UserService.dto.UserResponseDTO;
import com.example.UserService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name = "User API", description = "Users controller")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create new user", description = "Creates a new user and writes it to the database")
    @ApiResponse(responseCode = "200", description = "New user is created")
    @ApiResponse(responseCode = "404", description = "New user is not created")
    @PostMapping("/users")
    public ResponseEntity<EntityModel<UserResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createUser = userService.createUser(userRequestDTO);
        return ResponseEntity.created(
                        linkTo(methodOn(UserController.class).getUser(createUser.getId())).toUri())
                .body(toEntityModel(createUser));
    }

    @Operation(summary = "Updating user data", description = "Updates the user's data by the specified ID")
    @ApiResponse(responseCode = "200", description = "The user's data has been updated")
    @ApiResponse(responseCode = "404", description = "User data has not been updated")
    @PutMapping("/users/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO userRequestDTO) {
        userRequestDTO.setId(id);
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(toEntityModel(updatedUser));
    }

    @Operation(summary = "Get a user by ID", description = "Returns the user by the specified ID")
    @ApiResponse(responseCode = "200", description = "The user has been found")
    @ApiResponse(responseCode = "404", description = "The user has not been found")
    @GetMapping("/users/{id}")
    public EntityModel<UserResponseDTO> getUser(@PathVariable Long id) {
        UserResponseDTO user = userService.findByIdUser(id);
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
    }

    @Operation(summary = "Getting all users", description = "Returns all users from the database")
    @ApiResponse(responseCode = "200", description = "Users found")
    @ApiResponse(responseCode = "404", description = "Users not found")
    @GetMapping("/users")
    public CollectionModel<EntityModel<UserResponseDTO>> getAllUsers() {
        List<EntityModel<UserResponseDTO>> users = userService.findAllUsers().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).getAllUsers()).withRel("users")))
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @Operation(summary = "Deleting a user by ID", description = "Deletes a user from the database with the specified ID")
    @ApiResponse(responseCode = "200", description = "The user has been deleted")
    @ApiResponse(responseCode = "404", description = "The user has not been deleted")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<UserResponseDTO> toEntityModel(UserResponseDTO userResponseDTO) {
        Long userId = userResponseDTO.getId();

        EntityModel<UserResponseDTO> model = EntityModel.of(userResponseDTO);
        model.add(getSelfLink(userId));
        model.add(linkTo(methodOn(UserController.class).updateUser(userId, null)).withRel("update"));
        model.add(linkTo(methodOn(UserController.class).deleteUser(userId)).withRel("delete"));
        model.add(getAllUsersLink().withRel("users"));

        return model;
    }

    private Link getSelfLink(Long id) {
        return linkTo(methodOn(UserController.class).getUser(id)).withSelfRel();
    }

    private Link getAllUsersLink() {
        return linkTo(methodOn(UserController.class).getAllUsers()).withRel("users");
    }
}
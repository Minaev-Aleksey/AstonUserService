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
@Tag(name = "User API", description = "Управление пользователями")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Создание нового пользователя", description = "Создает нового пользователя и записывает его в БД")
    @ApiResponse(responseCode = "200", description = "Новый пользователь создан")
    @ApiResponse(responseCode = "404", description = "Новый пользователь не создан")
    @PostMapping("/users")
    public ResponseEntity<EntityModel<UserResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createUser = userService.createUser(userRequestDTO);
        return ResponseEntity.created(
                        linkTo(methodOn(UserController.class).getUser(createUser.getId())).toUri())
                .body(toEntityModel(createUser));
    }

    @Operation(summary = "Обновление данных пользователя", description = "Обновляет данные пользователя по указанному ID")
    @ApiResponse(responseCode = "200", description = "Данные пользователя обновлены")
    @ApiResponse(responseCode = "404", description = "Данные пользователя не обновлены")
    @PutMapping("/users/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO userRequestDTO) {
        userRequestDTO.setId(id);
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(toEntityModel(updatedUser));
    }

    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя по указанному ID")
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("/users/{id}")
    public EntityModel<UserResponseDTO> getUser(@PathVariable Long id) {
        UserResponseDTO user = userService.findByIdUser(id);
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
    }

    @Operation(summary = "Получение всех пользователей", description = "Возвращает всех пользователй из БД")
    @ApiResponse(responseCode = "200", description = "Пользователи найдены")
    @ApiResponse(responseCode = "404", description = "Пользователи не найдены")
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

    @Operation(summary = "Удаление пользователя по ID", description = "Удаляет пользователя из БД с указанным ID")
    @ApiResponse(responseCode = "200", description = "Пользователь удален")
    @ApiResponse(responseCode = "404", description = "Пользователь для удаления не найден")
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
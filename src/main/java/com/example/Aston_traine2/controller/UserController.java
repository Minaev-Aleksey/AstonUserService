package com.example.Aston_traine2.controller;

import com.example.Aston_traine2.dto.UserRequestDTO;
import com.example.Aston_traine2.dto.UserResponseDTO;
import com.example.Aston_traine2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createUser = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Обновление данных пользователя", description = "Обновляет данные пользователя по указанному ID")
    @ApiResponse(responseCode = "200", description = "Данные пользователя обновлены")
    @ApiResponse(responseCode = "404", description = "Данные пользователя не обновлены")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO updateUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updateUser);
    }

    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя по указанному ID")
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        UserResponseDTO user = userService.findByIdUser(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Получение всех пользователей", description = "Возвращает всех пользователй из БД")
    @ApiResponse(responseCode = "200", description = "Пользователи найдены")
    @ApiResponse(responseCode = "404", description = "Пользователи не найдены")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Удаление пользователя по ID", description = "Удаляет пользователя из БД с указанным ID")
    @ApiResponse(responseCode = "200", description = "Пользователь удален")
    @ApiResponse(responseCode = "404", description = "Пользователь для удаления не найден")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
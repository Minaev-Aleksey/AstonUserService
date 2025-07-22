//package com.example.UserService.controller;
//
//
//import com.example.UserService.dto.UserRequestDTO;
//import com.example.UserService.dto.UserResponseDTO;
//import com.example.UserService.exception.UserNotFoundException;
//import com.example.UserService.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private UserService userService;
//
//    @Test
//    public void testGetAllUsers() throws Exception {
//
//        UserResponseDTO user1 = new UserResponseDTO(1L, "user1", "user1@example.com", 111, LocalDate.now());
//        UserResponseDTO user2 = new UserResponseDTO(2L, "user2", "user2@example.com", 222, LocalDate.now());
//
//        Mockito.when(userService.findAllUsers()).thenReturn(Arrays.asList(user1, user2));
//
//        mockMvc.perform(get("/api/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].name", is("user1")))
//                .andExpect(jsonPath("$[1].id", is(2)))
//                .andExpect(jsonPath("$[1].name", is("user2")));
//    }
//
//    @Test
//    public void testGetUserById() throws Exception {
//
//        UserResponseDTO user = new UserResponseDTO(1L, "testUser", "test@example.com", 111, LocalDate.now());
//        Mockito.when(userService.findByIdUser(1L)).thenReturn(user);
//
//        mockMvc.perform(get("/api/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.name", is("testUser")))
//                .andExpect(jsonPath("$.email", is("test@example.com")));
//    }
//
//    @Test
//    public void testAddNewUser() throws Exception {
//        UserResponseDTO user1 = new UserResponseDTO(1L, "user1", "user1@example.com", 111, LocalDate.now());
//        UserRequestDTO user2 = new UserRequestDTO(1L, "user1", "user1@example.com", 111, LocalDate.now());
//
//        when(userService.createUser(user2)).thenReturn(user1);
//
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(user2)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("user1"))
//                .andExpect(jsonPath("$.email").value("user1@example.com"));
//    }
//
//    @Test
//    public void testUpdateUser() throws Exception {
//        // Подготовка мок-данных
//        UserResponseDTO user1 = new UserResponseDTO(1L, "user1", "user1@example.com", 111, LocalDate.now());
//        user1.setName("updatedUser");
//        user1.setEmail("updated@example.com");
//
//        when(userService.updateUser(anyLong(), any(UserRequestDTO.class))).thenReturn(user1);
//
//
//        // Выполнение запроса и проверки
//        mockMvc.perform(put("/api/users/{id}", user1.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(user1)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("updatedUser"))
//                .andExpect(jsonPath("$.email").value("updated@example.com"));
//    }
//
//    @Test
//    public void testDeleteUser() throws Exception {
//        Long userId = 1L;
//
//        doNothing().when(userService).deleteUser(userId);
//
//        mockMvc.perform(delete("/api/users/{id}", userId))
//                .andExpect(status().isNoContent());
//
//        verify(userService, times(1)).deleteUser(userId);
//
//    }
//
//    @Test
//    public void getUserByIdNotFoundTest() throws Exception {
//        Long id = 99L;
//        when(userService.findByIdUser(id)).thenThrow(new UserNotFoundException("Пользователь с Id" + id + " не найден"));
//
//
//        mockMvc.perform(get("/users/{id}", id))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void getUserByIdNotFoundTest2() throws Exception {
//        Long id = 99L;
//
//        when(userService.findByIdUser(id)).thenThrow(new UserNotFoundException("Пользователь с Id" + id + " не найден"));
//
//        MvcResult result = mockMvc.perform(get("/users/{id}", id)).andReturn();
//
//        Assertions.assertEquals(404, result.getResponse().getStatus());
//    }
//
//}
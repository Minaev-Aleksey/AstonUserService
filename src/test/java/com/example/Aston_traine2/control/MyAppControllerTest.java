package com.example.Aston_traine2.control;

import com.example.Aston_traine2.entity.User;
import com.example.Aston_traine2.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MyAppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testGetAllUsers() throws Exception {

        User user1 = new User(1L,"user1", "user1@example.com", 111, LocalDate.now());
        User user2 = new User(2L,"user2", "user2@example.com", 222, LocalDate.now());
        Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("user1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("user2")));
    }

    @Test
    public void testGetUserById_Success() throws Exception {

        User user = new User(1L,"testUser", "test@example.com", 111, LocalDate.now());
        Mockito.when(userService.getUser(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("testUser")))
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    public void testAddNewUser() throws Exception {

        User newUser = new User(1L,"testUser", "test@example.com", 111, LocalDate.now());

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("testUser")))
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Подготовка мок-данных
        User updatedUser = new User(1L, "updatedUser", "updated@example.com",111, LocalDate.now());

        // Выполнение запроса и проверки
        mockMvc.perform(put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("updatedUser")))
                .andExpect(jsonPath("$.email", is("updated@example.com")));
    }

    @Test
    public void deleteUserTest() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);

        userService.deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
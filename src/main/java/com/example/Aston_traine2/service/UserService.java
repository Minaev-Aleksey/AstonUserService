package com.example.Aston_traine2.service;

import com.example.Aston_traine2.dto.UserRequestDTO;
import com.example.Aston_traine2.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    /**
     * Метод добавляет пользователя в таблицу dz_user
     *
     * @param userRequestDTO - конструктор с параметрами для добавления пользователя
     */
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    /**
     * Метод обновляет данные пользователя по указанному id
     * @param userRequestDTO  - конструктор с параметрами для обновления данных о пользователе
     */
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    /**
     * Метод возвращает id пользователя
     * @param id - числовой идентификатор пользователя
     * @return - возвращает найденного пользователя
     */
    UserResponseDTO findByIdUser(Long id);

    /**
     * Метод собирает всех пользователей таблицы dz_user, собирает в List и выводит в консоль
     * @return - возвращает список пользователей
     */
    List<UserResponseDTO> findAllUsers();

    /**
     * Метод удаляет пользователя из таблицы по указанному id
     * @param id - числовой идентификатор пользователя
     */
    void deleteUser(Long id);
}

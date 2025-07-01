package com.example.Aston_traine2.service;

import com.example.Aston_traine2.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Метод возвращает id пользователя
     * @param id - числовой идентификатор пользователя
     * @return - возвращает найденного пользователя
     */
    Optional<User> getUser(Long id);

    /**
     * Метод собирает всех пользователей таблицы dz_user, собирает в List и выводит в консоль
     * @return - возвращает список пользователей
     */
    public List<User> getAllUsers();

    /**
     * Метод добавляет пользователя в таблицу dz_user
     * @param user - конструктор с параметрами для добавления пользователя
     */
    void saveUser(User user);

    /**
     * Метод обновляет данные пользователя по указанному id
     * @param user  - конструктор с параметрами для обновления данных о пользователе
     * @return
     */
    void updateUser(User user);

    /**
     * Метод удаляет пользователя из таблицы по указанному id
     * @param id - числовой идентификатор пользователя
     */
    void deleteUser(Long id);
}

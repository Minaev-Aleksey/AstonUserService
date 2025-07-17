package com.example.Aston_traine2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Objects;

public class UserResponseDTO {
    @Schema(name = "Уникальный идентификатор", example = "1")
    private Long id;
    @Schema(name = "Имя пользователя", example = "Алексей")
    private String name;
    @Schema(name = "Электронная почта пользователя", example = "example@mail.ru")
    private String email;
    @Schema(name = "Возраст пользователя", example = "18")
    private int age;
    @Schema(name = "Дата создания пользователя устанавливается автоматически", example = "2025-07-07")
    private LocalDate localDate = LocalDate.now();

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String name, String email, int age, LocalDate localDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.localDate = localDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDTO that = (UserResponseDTO) o;
        return age == that.age && Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && Objects.equals(localDate, that.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, localDate);
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", localDate=" + localDate +
                '}';
    }
}

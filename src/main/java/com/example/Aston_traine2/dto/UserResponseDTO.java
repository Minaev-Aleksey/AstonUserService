package com.example.Aston_traine2.dto;

import java.time.LocalDate;
import java.util.Objects;

public class UserResponseDTO {
    private Long Id;
    private String name;
    private String email;
    private int age;
    private LocalDate localDate = LocalDate.now();

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String name, String email, int age, LocalDate localDate) {
        Id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.localDate = localDate;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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
        return age == that.age && Objects.equals(Id, that.Id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(localDate, that.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, email, age, localDate);
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", localDate=" + localDate +
                '}';
    }
}

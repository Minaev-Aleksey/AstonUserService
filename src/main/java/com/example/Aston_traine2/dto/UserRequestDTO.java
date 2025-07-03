package com.example.Aston_traine2.dto;

import java.time.LocalDate;
import java.util.Objects;

public class UserRequestDTO {

    private Long id;
    private String name;
    private String email;
    private int age;
    private LocalDate localDate = LocalDate.now();

    public UserRequestDTO() {
    }

    public UserRequestDTO(Long id, String name, String email, int age, LocalDate localDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        UserRequestDTO that = (UserRequestDTO) o;
        return age == that.age && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(localDate, that.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, localDate);
    }

    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", localDate=" + localDate +
                '}';
    }
}

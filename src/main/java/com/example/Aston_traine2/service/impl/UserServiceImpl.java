package com.example.Aston_traine2.service.impl;

import com.example.Aston_traine2.dto.UserRequestDTO;
import com.example.Aston_traine2.dto.UserResponseDTO;
import com.example.Aston_traine2.exception.UserNotFoundException;
import com.example.Aston_traine2.model.User;
import com.example.Aston_traine2.repository.UserRepository;
import com.example.Aston_traine2.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);
        User createNewUser = userRepository.save(user);
        return modelMapper.map(createNewUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + " не найден"));

        modelMapper.map(userRequestDTO, user);
        User updateUser = userRepository.save(user);
        return modelMapper.map(updateUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO findByIdUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + " не найден"));

        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь с id " + id + " не найден");
        }
        userRepository.deleteById(id);
    }
}

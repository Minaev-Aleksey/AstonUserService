package com.example.UserService.service.impl;

import com.example.UserService.dto.UserRequestDTO;
import com.example.UserService.dto.UserResponseDTO;
import com.example.UserService.exception.UserNotFoundException;
import com.example.UserService.kafka.KafkaMessageService;
import com.example.UserService.model.User;
import com.example.UserService.repository.UserRepository;
import com.example.UserService.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KafkaMessageService kafkaMessageService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, KafkaMessageService kafkaMessageService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.kafkaMessageService = kafkaMessageService;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);
        User createNewUser = userRepository.save(user);

        kafkaMessageService.sendUserEvent("USER_CREATED", createNewUser.getEmail());
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        String userEmail = user.getEmail();

        userRepository.delete(user);
        kafkaMessageService.sendUserEvent("USER_DELETED", userEmail);
    }
}

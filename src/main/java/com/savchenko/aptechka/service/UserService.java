package com.savchenko.aptechka.service;

import com.savchenko.aptechka.dto.Role;
import com.savchenko.aptechka.dto.UserDto;
import com.savchenko.aptechka.dto.UserSignupDto;
import com.savchenko.aptechka.entity.User;
import com.savchenko.aptechka.mapper.UserMapper;
import com.savchenko.aptechka.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto createLocalUser(String keycloakId, UserSignupDto dto) {
        User user = User.builder()
                .keycloakId(keycloakId)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .avatarUrl(dto.getAvatarUrl())
                .roles(Set.of(Role.ROLE_USER))
                .build();

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public UserDto findByKeycloakId(String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new EntityNotFoundException(
                    "User not found with keycloakId: " + keycloakId));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                    "User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

package com.savchenko.aptechka.controller;

import com.savchenko.aptechka.dto.AuthResponseDto;
import com.savchenko.aptechka.dto.TokenResponse;
import com.savchenko.aptechka.dto.UserDto;
import com.savchenko.aptechka.dto.UserLoginDto;
import com.savchenko.aptechka.dto.UserSignupDto;
import com.savchenko.aptechka.service.AuthService;
import com.savchenko.aptechka.service.KeycloakUserService;
import com.savchenko.aptechka.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
  private final AuthService authService;
  private final KeycloakUserService kcService;
  private final UserService userService;

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthResponseDto signup(@Valid @RequestBody UserSignupDto dto) {
    String kcId = kcService.createUser(dto);
    UserDto user = userService.createLocalUser(kcId, dto);
    TokenResponse token = authService.login(new UserLoginDto(dto.getUsername(), dto.getPassword()));
    return AuthResponseDto.builder()
            .user(user)
            .token(token)
            .build();
  }

  @PostMapping("/login")
  public AuthResponseDto login(@Valid @RequestBody UserLoginDto dto) {
    TokenResponse token = authService.login(dto);
    String kcId = authService.extractKeycloakId(token);
    UserDto user = userService.findByKeycloakId(kcId);
    return AuthResponseDto.builder()
            .token(token)
            .user(user)
            .build();
  }
}


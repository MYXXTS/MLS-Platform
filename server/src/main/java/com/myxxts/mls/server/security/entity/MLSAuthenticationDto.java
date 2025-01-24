package com.myxxts.mls.server.security.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MLSAuthenticationDto {

  @Size (min = 4, max = 20, message = "Nickname must be between 4 and 20 characters.")
  private String nickname;

  @Size (min = 4, max = 20, message = "Username must be between 4 and 20 characters.")
  private String username;

  @Size (min = 6, max = 20, message = "Password must be between 6 and 20 characters.")
  @Pattern (
    regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$",
    message = "Password must contain at least one letter and one number."
  )
  private String password;

  @Email
  @NotBlank (message = "Email cannot be empty.")
  private String email;
}

package edu.miu.cs545.waa.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Login Class to map user details while logged in
 *
 * @version 1.0.0
 */
@Setter
@Getter
public class LoginRequest {
  @NotNull
  @Email
  private String userEmail;
  @NotNull
  private String password;
  private boolean rememberMe;
}
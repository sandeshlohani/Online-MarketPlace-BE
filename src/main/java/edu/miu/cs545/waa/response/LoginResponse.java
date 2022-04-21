package edu.miu.cs545.waa.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * LoginResponse class holds login related response attributes
 *
 * @version 1.0.0
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class LoginResponse {
  private String type;
  private String token;
  private String role;
  private String refreshToken;
  private String message;
}

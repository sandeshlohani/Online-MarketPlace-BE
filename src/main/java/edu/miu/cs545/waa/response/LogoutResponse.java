package edu.miu.cs545.waa.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * LogoutResponse class holds logout related response attributes
 *
 * @version 1.0.0
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class LogoutResponse {
  private String type;
  private String message;
}
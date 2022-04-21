package edu.miu.cs545.waa.controller;

import edu.miu.cs545.waa.constant.ResponseMessageConstant;
import edu.miu.cs545.waa.constant.SecurityConstants;
import edu.miu.cs545.waa.domain.dto.LoginRequest;
import edu.miu.cs545.waa.domain.dto.RefreshTokenRequest;
import edu.miu.cs545.waa.exception.InvalidUserException;
import edu.miu.cs545.waa.response.LoginResponse;
import edu.miu.cs545.waa.response.LogoutResponse;
import edu.miu.cs545.waa.response.RefreshTokenResponse;
import edu.miu.cs545.waa.service.AuthService;
import edu.miu.cs545.waa.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {

  private final AuthService authService;

  @Autowired
  public AuthenticationController(AuthService authService) {
    this.authService = authService;
  }


  /**
   * API returns Login response
   *
   * @param login current user Authentication
   * @return loginResponse
   */
  @PostMapping
  public LoginResponse returnLoginResponse(@RequestBody LoginRequest login) {
    try {
      return authService.login(login);
    } catch (BadCredentialsException e) {
      log.info("Bad Credentials");
      throw new InvalidUserException("Invalid credentials.");

    } catch (AuthenticationException ex) {
      return new LoginResponse(ResponseMessageConstant.ERROR, "", "", "", ResponseMessageConstant.FAILURE_LOGIN);
    }
  }

  /**
   * Method to logout and return to login page
   *
   * @return LogoutResponse
   */
  @GetMapping("/logout")
  @PreAuthorize("hasAnyRole('" + SecurityConstants.ROLE_ADMIN + "','" + SecurityConstants.ROLE_USER + "')")
  public LogoutResponse logout(HttpServletRequest req) {
    JwtUtils.removeToken(JwtUtils.getToken(req));
    return new LogoutResponse(ResponseMessageConstant.SUCCESS, ResponseMessageConstant.SUCCESS_LOGOUT);
  }

  @PostMapping("/refreshToken")
  public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception {
    return authService.refreshToken(refreshTokenRequest);
  }
}
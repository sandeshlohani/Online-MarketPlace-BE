package edu.miu.cs545.waa.service.impl;

import edu.miu.cs545.waa.constant.ResponseMessageConstant;
import edu.miu.cs545.waa.domain.dto.LoginRequest;
import edu.miu.cs545.waa.domain.dto.RefreshTokenRequest;
import edu.miu.cs545.waa.response.LoginResponse;
import edu.miu.cs545.waa.response.RefreshTokenResponse;
import edu.miu.cs545.waa.service.AuthService;
import edu.miu.cs545.waa.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtUtils jwtUtil;

  @Autowired
  public AuthServiceImpl(AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService, JwtUtils jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    try {
      var authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(),
              loginRequest.getPassword())
      );
      final UserDetails userDetails = userDetailsService
          .loadUserByUsername(loginRequest.getUserEmail());

      final String accessToken = jwtUtil.generateJwtToken(authentication, loginRequest.isRememberMe());
      final String refreshToken = jwtUtil.generateRefreshToken(authentication);
      String role = authentication.getAuthorities().toString();
      role = role.substring(1, role.indexOf("]"));
      return new LoginResponse(ResponseMessageConstant.SUCCESS, accessToken, role, refreshToken,
          ResponseMessageConstant.SUCCESS_LOGIN);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException(e.getMessage());
    }
  }

  @Override
  public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception {
    boolean isRefreshTokenValid = jwtUtil.validateToken(refreshTokenRequest.getRefreshToken());
    final String newAccessToken = jwtUtil.doGenerateToken(jwtUtil.getSubject(refreshTokenRequest.getRefreshToken()),
        false);
    final String newRefreshToken = jwtUtil.doGenerateToken(
        jwtUtil.getSubject(refreshTokenRequest.getRefreshToken()), true);
    return new RefreshTokenResponse(newAccessToken, newRefreshToken);
  }
}

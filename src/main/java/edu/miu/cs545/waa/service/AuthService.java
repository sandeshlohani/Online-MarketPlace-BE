package edu.miu.cs545.waa.service;

import edu.miu.cs545.waa.domain.dto.LoginRequest;
import edu.miu.cs545.waa.domain.dto.RefreshTokenRequest;
import edu.miu.cs545.waa.response.LoginResponse;
import edu.miu.cs545.waa.response.RefreshTokenResponse;

public interface AuthService {
  LoginResponse login(LoginRequest loginRequest);

  RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception;
}

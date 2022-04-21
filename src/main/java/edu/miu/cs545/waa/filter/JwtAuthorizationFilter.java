package edu.miu.cs545.waa.filter;


import edu.miu.cs545.waa.constant.SecurityConstants;
import edu.miu.cs545.waa.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


/**
 * JwtAuthorizationFilter  class  it is responsible for  checking the authorization whether request is valid or spam
 *
 * @version 1.0.0
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  /**
   * Method doFilterInternal for filtering the user
   *
   * @param request     HttpServletRequest
   * @param filterChain FilterChain
   * @param response    HttpServletResponse
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {
    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
    String header = JwtUtils.getToken(request);
    if (StringUtils.isEmpty(header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }


  /**
   * Method getAuthentication for getting the token
   *
   * @param request HttpServletRequest
   * @return String cookie  value
   */
  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = JwtUtils.getToken(request);
    if (StringUtils.isNotEmpty(token)) {
      try {
        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        Jws<Claims> parsedToken = Jwts.parser()
            .setSigningKey(signingKey)
            .parseClaimsJws(token.replace("Bearer", ""));

        String username = parsedToken
            .getBody()
            .getSubject();

        List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
            .get("rol")).stream()
            .map(authority -> new SimpleGrantedAuthority((String) authority))
            .collect(Collectors.toList());

        if (StringUtils.isNotEmpty(username)) {
          if (JwtUtils.isValidToken(token)) {
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
          } else {
            return null;
          }

        }
      } catch (ExpiredJwtException exception) {
        log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
      } catch (UnsupportedJwtException exception) {
        log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
      } catch (MalformedJwtException exception) {
        log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
      } catch (SignatureException exception) {
        log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
      } catch (IllegalArgumentException exception) {
        log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
      }
    }

    return null;
  }

}

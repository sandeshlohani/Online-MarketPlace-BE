package edu.miu.cs545.waa.service;


import edu.miu.cs545.waa.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends BaseService<User>, UserDetailsService {

    User getActiveUser(String email);

}

package edu.miu.cs545.waa.repository;

import edu.miu.cs545.waa.domain.User;
import edu.miu.cs545.waa.enums.RoleType;
import edu.miu.cs545.waa.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRoleTypeAndStatus(RoleType roleType, Status status);

    List<User> findByRoleType(RoleType roleType);

    User findByIdAndRoleType(Long id, RoleType roleType);

    User findByEmail(String email);

}

package edu.miu.cs545.waa.repository;

import edu.miu.cs545.waa.domain.Role;
import edu.miu.cs545.waa.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Long> {
    public List<Role> findByRoleType(RoleType roleType);
}

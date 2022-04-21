package edu.miu.cs545.waa.domain;

import edu.miu.cs545.waa.domain.dto.Base;
import edu.miu.cs545.waa.enums.RoleType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User extends Base {

    private String name;
    private String email;
    private String password;
    private String declineReason;
    private long rewardPoint;

    //roleType for segregating users
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    //a user can have multiple roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<Role> roles;

//    @ManyToMany(mappedBy = "users")
//    private List<Product> products;
}

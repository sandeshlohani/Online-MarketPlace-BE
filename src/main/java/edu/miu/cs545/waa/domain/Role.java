package edu.miu.cs545.waa.domain;

import javax.persistence.*;

import edu.miu.cs545.waa.enums.RoleType;
import lombok.Data;


/**
 * Role for users
 */
@Entity
@Data
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Enumerated(EnumType.STRING)
  private RoleType roleType;
}

package edu.miu.cs545.waa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import edu.miu.cs545.waa.domain.dto.Base;
import lombok.Data;

@Entity
@Data
public class PointCollected extends Base {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long pointsCollected;
  private String comment;
}

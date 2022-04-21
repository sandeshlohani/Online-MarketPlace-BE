package edu.miu.cs545.waa.domain;

import edu.miu.cs545.waa.domain.dto.Base;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Follow extends Base {
    private Long sellerId;
}

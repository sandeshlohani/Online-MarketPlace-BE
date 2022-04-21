package edu.miu.cs545.waa.domain;

import edu.miu.cs545.waa.domain.dto.Base;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address extends Base {
    private String street;
    private String city;
    private String zipCode;
    private String state;
}

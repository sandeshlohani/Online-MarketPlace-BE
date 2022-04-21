package edu.miu.cs545.waa.domain;

import edu.miu.cs545.waa.domain.dto.Base;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Review extends Base {

    private String title;
    private String content;

    @ManyToOne
    private Product product;
}

package edu.miu.cs545.waa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.cs545.waa.domain.Product;
import edu.miu.cs545.waa.domain.dto.Base;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDto extends Base {
    private String title;
    private String content;
    private Long productId;
//    private Product product;
}

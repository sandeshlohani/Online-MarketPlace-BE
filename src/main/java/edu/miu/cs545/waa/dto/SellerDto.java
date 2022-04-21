package edu.miu.cs545.waa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.cs545.waa.domain.dto.Base;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SellerDto extends Base {
    private String name;
    private String email;
    private String declineReason;
    private long rewardPoint;
}

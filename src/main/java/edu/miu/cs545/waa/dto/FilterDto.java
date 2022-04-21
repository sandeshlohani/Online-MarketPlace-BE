package edu.miu.cs545.waa.dto;

import edu.miu.cs545.waa.enums.Status;
import lombok.Data;

@Data
public class FilterDto {
    private Status status;
}

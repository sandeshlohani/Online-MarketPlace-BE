package edu.miu.cs545.waa.service;

import edu.miu.cs545.waa.dto.FilterDto;
import edu.miu.cs545.waa.dto.FollowDto;
import edu.miu.cs545.waa.dto.SellerDto;

import java.util.List;

public interface SellerService {
    List<SellerDto> getSellers(FilterDto filterDto, String userId);

    SellerDto updateSeller(Long id, SellerDto sellerDto, String userId);

    void followSeller(FollowDto followDto, String userId);

    SellerDto getSeller(String userId);

    boolean isFollowed(Long sellerId, String userId);
}

package edu.miu.cs545.waa.service.impl;

import edu.miu.cs545.waa.domain.Follow;
import edu.miu.cs545.waa.domain.User;
import edu.miu.cs545.waa.dto.FilterDto;
import edu.miu.cs545.waa.dto.FollowDto;
import edu.miu.cs545.waa.dto.SellerDto;
import edu.miu.cs545.waa.enums.RoleType;
import edu.miu.cs545.waa.exception.DataNotFoundException;
import edu.miu.cs545.waa.repository.FollowRepository;
import edu.miu.cs545.waa.repository.UserRepository;
import edu.miu.cs545.waa.service.SellerService;
import edu.miu.cs545.waa.util.CustomNullAwareBeanUtils;
import edu.miu.cs545.waa.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    private UserRepository userRepository;
    private FollowRepository followRepository;
    private MapperUtils<SellerDto> mapperToSellerDto;
    private MapperUtils<User> mapperToSeller;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setFollowRepository(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Autowired
    public void setMapperToSellerDto(MapperUtils<SellerDto> mapperToSellerDto) {
        this.mapperToSellerDto = mapperToSellerDto;
    }

    @Autowired
    public void setMapperToSeller(MapperUtils<User> mapperToSeller) {
        this.mapperToSeller = mapperToSeller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SellerDto> getSellers(FilterDto filterDto, String userId) {
        List<User> users;
        if (filterDto != null && filterDto.getStatus() != null) {
            users = userRepository.findByRoleTypeAndStatus(RoleType.SELLER, filterDto.getStatus());
        } else {
            users = userRepository.findByRoleType(RoleType.SELLER);
        }
        return (List<SellerDto>) mapperToSellerDto.mapList(users, new SellerDto());
    }

    @Override
    public SellerDto updateSeller(Long id, SellerDto sellerDto, String userId) {
        User user = (User) mapperToSeller.getMap(sellerDto, new User());
        User currentUser = userRepository.findByIdAndRoleType(id, RoleType.SELLER);
        if (currentUser == null) {
            throw new DataNotFoundException(String.format("User with id %d not found", id));
        }
        CustomNullAwareBeanUtils.myCopyProperties(user, currentUser);
        userRepository.save(currentUser);
        return sellerDto;
    }

    @Override
    public void followSeller(FollowDto followDto, String userId) {
        Follow follow = followRepository.findBySellerIdAndUserId(followDto.getSellerId(), userId);
        if (follow == null && followDto.isFollow()) {
            follow = new Follow();
            follow.setSellerId(followDto.getSellerId());
            follow.setUserId(userId);
            followRepository.save(follow);
        }
        if (follow != null && !followDto.isFollow()) {
            followRepository.delete(follow);
        }
    }

    @Override
    public boolean isFollowed(Long sellerId, String userId) {
        Follow follow = followRepository.findBySellerIdAndUserId(sellerId, userId);
        return follow != null;
    }

    @Override
    public SellerDto getSeller(String userId) {
        User user = userRepository.findByEmail(userId);
        if (user != null) {
            return (SellerDto) mapperToSellerDto.getMap(user, new SellerDto());
        }
        return new SellerDto();
    }
}

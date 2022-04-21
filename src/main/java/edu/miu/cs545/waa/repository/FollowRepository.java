package edu.miu.cs545.waa.repository;

import edu.miu.cs545.waa.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findBySellerIdAndUserId(Long sellerId, String userId);
}

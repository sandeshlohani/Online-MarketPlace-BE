package edu.miu.cs545.waa.repository;

import edu.miu.cs545.waa.domain.Review;
import edu.miu.cs545.waa.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStatus(Status status);
}

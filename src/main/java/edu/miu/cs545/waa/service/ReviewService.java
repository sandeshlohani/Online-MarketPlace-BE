package edu.miu.cs545.waa.service;

import edu.miu.cs545.waa.dto.FilterDto;
import edu.miu.cs545.waa.dto.ReviewDetailDto;
import edu.miu.cs545.waa.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    public List<ReviewDto> getReviews(FilterDto filterDto, String userId);

    ReviewDto createReview(ReviewDto reviewDto, String userId);

    public ReviewDto updateReview(Long id, ReviewDto reviewDto);

    ReviewDetailDto getReviewsDetailById(Long id);
}

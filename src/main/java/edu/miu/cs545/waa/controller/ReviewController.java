package edu.miu.cs545.waa.controller;

import edu.miu.cs545.waa.dto.FilterDto;
import edu.miu.cs545.waa.dto.ReviewDto;
import edu.miu.cs545.waa.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDto> getReviews(FilterDto filterDto, Principal principal) {
        return reviewService.getReviews(filterDto, principal.getName());
    }

    @PostMapping
    public ReviewDto createReview(@RequestBody ReviewDto reviewDto, Principal principal) {
        return reviewService.createReview(reviewDto, principal.getName());
    }
}

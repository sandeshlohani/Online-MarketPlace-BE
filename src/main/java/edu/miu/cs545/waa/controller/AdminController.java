package edu.miu.cs545.waa.controller;

import edu.miu.cs545.waa.domain.User;
import edu.miu.cs545.waa.dto.FilterDto;
import edu.miu.cs545.waa.dto.ReviewDetailDto;
import edu.miu.cs545.waa.dto.ReviewDto;
import edu.miu.cs545.waa.dto.SellerDto;
import edu.miu.cs545.waa.service.ReviewService;
import edu.miu.cs545.waa.service.SellerService;
import edu.miu.cs545.waa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private SellerService sellerService;
    private ReviewService reviewService;
    private UserService userService;

    @Autowired
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sellers")
    public List<SellerDto> getSellers(FilterDto filterDto, Principal principal) {
        return sellerService.getSellers(filterDto, principal.getName());
    }

    @PutMapping("/sellers/{id}")
    public SellerDto updateSeller(@PathVariable Long id, @RequestBody SellerDto sellerDto, Principal principal) {
        return sellerService.updateSeller(id, sellerDto, principal.getName());
    }

    @GetMapping("/reviews")
    public List<ReviewDto> getReviews(FilterDto filterDto, Principal principal) {
        return reviewService.getReviews(filterDto, principal.getName());
    }

    @GetMapping("/reviews/{id}")
    public ReviewDetailDto getReviewsDetailById(@PathVariable Long id) {
        return reviewService.getReviewsDetailById(id);
    }

    @PutMapping("/reviews/{id}")
    public ReviewDto updateReviews(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        return reviewService.updateReview(id, reviewDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable long id) {
        userService.deleteById(id);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }


}

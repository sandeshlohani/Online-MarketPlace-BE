package edu.miu.cs545.waa.service.impl;

import edu.miu.cs545.waa.domain.Product;
import edu.miu.cs545.waa.domain.Review;
import edu.miu.cs545.waa.domain.User;
import edu.miu.cs545.waa.dto.*;
import edu.miu.cs545.waa.enums.Status;
import edu.miu.cs545.waa.exception.DataNotFoundException;
import edu.miu.cs545.waa.repository.ProductRepository;
import edu.miu.cs545.waa.repository.ReviewRepository;
import edu.miu.cs545.waa.repository.UserRepo;
import edu.miu.cs545.waa.service.ReviewService;
import edu.miu.cs545.waa.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private MapperUtils<ReviewDto> mapperToReviewDto;
    private MapperUtils<Review> mapperToReview;
    private MapperUtils<ProductDto> mapperToProductDto;
    private UserRepo userRepo;

    @Autowired
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setMapperToReviewDto(MapperUtils<ReviewDto> mapperToReviewDto) {
        this.mapperToReviewDto = mapperToReviewDto;
    }

    @Autowired
    public void setMapperToReview(MapperUtils<Review> mapperToReview) {
        this.mapperToReview = mapperToReview;
    }

    @Autowired
    public void setMapperToProductDto(MapperUtils<ProductDto> mapperToProductDto) {
        this.mapperToProductDto = mapperToProductDto;
    }

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ReviewDto> getReviews(FilterDto filterDto, String userId) {
        List<Review> reviews;
        if (filterDto != null && filterDto.getStatus() != null) {
            reviews = reviewRepository.findByStatus(filterDto.getStatus());
        } else {
            reviews = reviewRepository.findAll();
        }
        return (List<ReviewDto>) mapperToReviewDto.mapList(reviews, new ReviewDto());
    }

    @Override
    public ReviewDto createReview(ReviewDto reviewDto, String userId) {
        Optional<Product> product = productRepository.findById(reviewDto.getProductId());
        if (product.isPresent()) {
            Review review = (Review) mapperToReview.getMap(reviewDto, new Review());
            review.setStatus(Status.CREATED);
            review.setProduct(product.get());
            return (ReviewDto) mapperToReviewDto.getMap(reviewRepository.save(review), new ReviewDto());
        }
        return null;
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Optional<Review> currentReview = reviewRepository.findById(id);

        if (currentReview.isEmpty()) {
            throw new DataNotFoundException(String.format("User with id %d not found", id));
        }
        currentReview.get().setStatus(reviewDto.getStatus());
        reviewRepository.save(currentReview.get());
        return reviewDto;
    }

    @Override
    public ReviewDetailDto getReviewsDetailById(Long id) {
        Optional<Review> currentReview = reviewRepository.findById(id);
        if (currentReview.isPresent()) {
            User user = userRepo.findByEmail(currentReview.get().getUserId());
            ReviewDetailDto reviewDetailDto = new ReviewDetailDto();
            reviewDetailDto.setProductDto((ProductDto) mapperToProductDto.getMap(currentReview.get().getProduct(), new ProductDto()));
            BuyerDto buyerDto = new BuyerDto();
            buyerDto.setId(user.getId());
            buyerDto.setName(user.getName());
            buyerDto.setEmail(user.getEmail());
            reviewDetailDto.setBuyerDto(buyerDto);
            return reviewDetailDto;
        }
        return new ReviewDetailDto();
    }
}

package com.intern.OnlineBookStore.service;

import com.intern.OnlineBookStore.dto.NewReviewDto;
import com.intern.OnlineBookStore.dto.ReviewDto;
import com.intern.OnlineBookStore.model.Book;
import com.intern.OnlineBookStore.model.Review;
import com.intern.OnlineBookStore.model.User;
import com.intern.OnlineBookStore.repository.ReviewRepo;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    private final ReviewRepo reviewRepo;

    public ReviewService(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    public List<ReviewDto> getAllReviewsByBook(long bookId){
        List<Review> reviewList = reviewRepo.getReviewByBookId(bookId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDto reviewDto = new ReviewDto(review);
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    public Review getReviewByUserIdAndBookId(long userId, long bookId){
        return reviewRepo.getReviewByUserIdAndBookId(userId, bookId);
    }

    public ReviewDto addReview(NewReviewDto newReviewDto, int id, String name) {
        Book book = new Book();
        book.setBookId(newReviewDto.getBookId());

        User user = new User();
        user.setUserId(id);
        user.setUsername(name);

        Review newReview = new Review(user, book, newReviewDto.getRating(), newReviewDto.getComment());

        try {
            Review review = reviewRepo.save(newReview);
            return new ReviewDto(review);
        } catch (Exception exception) {
            return null;
        }
    }
    public ResponseWrapper updateReview(NewReviewDto newReviewDto, long userId, long reviewId) {
        Review prevReview = reviewRepo.findById(reviewId).orElse(null);

        if (prevReview == null) {
            return new ResponseWrapper(false, 400, "Can not found review at the moment",null );
        } else {
            if (prevReview.getUser().getUserId() != userId) {
                return new ResponseWrapper(false, 400, "Error updating review", null);
            } else {
                prevReview.setRating(newReviewDto.getRating());
                prevReview.setComment(newReviewDto.getComment());

                Review newReview =  reviewRepo.save(prevReview);

                ReviewDto reviewDto1 = new ReviewDto(newReview);
                return new ResponseWrapper(true, 200, "Successfully updated review", reviewDto1);
            }
        }
    }
    public Map<String, Object> getSingleBookRating(long bookId) {
        List<Map<String, Object>> reviews = reviewRepo.getOverallRatingAndNumReviewsForSingleBook(bookId);
        try {
            return reviews.get(0);
        } catch (IndexOutOfBoundsException ex) {
            Map<String, Object> emptyRating = new HashMap<>();
            emptyRating.put("book_id", bookId);
            emptyRating.put("overall_rating", 0);
            emptyRating.put("num_reviews", 0);
            return emptyRating;
        }
    }

    public List<Map<String, Object>> getRatingOfBooks(List<Long> bookIds) {
        return reviewRepo.getOverallRatingAndNumReviewsForBooks(bookIds);
    }
}



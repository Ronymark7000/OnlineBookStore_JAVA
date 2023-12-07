package com.intern.OnlineBookStore.controller;

import com.intern.OnlineBookStore.dto.NewReviewDto;
import com.intern.OnlineBookStore.dto.ReviewDto;
import com.intern.OnlineBookStore.model.Review;
import com.intern.OnlineBookStore.service.ReviewService;
import com.intern.OnlineBookStore.util.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping()
    public ResponseWrapper addReview(@Valid @RequestBody NewReviewDto newReviewDto, HttpServletRequest request){
        int userId = (int) request.getAttribute("userId");
        String username = (String) request.getAttribute("username") ;

        Review prevReview = reviewService.getReviewByUserIdAndBookId(userId, newReviewDto.getBookId());

        if(prevReview == null){
            ReviewDto newReview = reviewService.addReview(newReviewDto, userId, username);
            if(newReview == null){
                return new ResponseWrapper(false, 400, "Error adding review", null);
            }else{
                return new ResponseWrapper(true,200 , "Review added",newReview);
            }
        }else{
            return reviewService.updateReview(newReviewDto, userId, prevReview.getReviewId());
        }
    }

}

package com.example.finalprojectgroup8;

public class RatingHelper {
    String reviewerid,description;
    int rating;

    public RatingHelper(){

    }

    public RatingHelper(String reviewerid, String description, int rating) {
        this.reviewerid = reviewerid;
        this.description = description;
        this.rating = rating;
    }

    public String getReviewerid() {
        return reviewerid;
    }

    public void setReviewerid(String reviewerid) {
        this.reviewerid = reviewerid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

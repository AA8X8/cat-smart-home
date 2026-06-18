package edu.rutmiit.demo.demorest.graphql.types;

public record CreateReviewInput(String bookId, String text, Integer rating) {
}
package edu.rutmiit.demo.demorest.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import edu.rutmiit.demo.booksapicontract.dto.BookResponse;
import edu.rutmiit.demo.booksapicontract.dto.Review;
import edu.rutmiit.demo.demorest.storage.InMemoryStorage;
import graphql.schema.DataFetchingEnvironment;

import java.util.Collections;
import java.util.List;

@DgsComponent
public class BookReviewsDataFetcher {

    private final InMemoryStorage storage;

    public BookReviewsDataFetcher(InMemoryStorage storage) {
        this.storage = storage;
    }

    @DgsData(parentType = "Book", field = "reviews")
    public List<Review> reviews(DataFetchingEnvironment dfe) {
        BookResponse book = dfe.getSource();
        List<Review> reviews = storage.bookReviews.get(book.getId());
        return reviews != null ? reviews : Collections.emptyList();
    }
}
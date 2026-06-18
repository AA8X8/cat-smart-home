package edu.rutmiit.demo.demorest.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import edu.rutmiit.demo.booksapicontract.dto.Review;
import edu.rutmiit.demo.demorest.graphql.types.CreateReviewInput;
import edu.rutmiit.demo.demorest.storage.InMemoryStorage;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@DgsComponent
public class ReviewDataFetcher {

    private final InMemoryStorage storage;
    private final AtomicLong reviewSequence = new AtomicLong(0);

    public ReviewDataFetcher(InMemoryStorage storage) {
        this.storage = storage;
    }

    @DgsMutation
    public Review createReview(@InputArgument CreateReviewInput input) {
        Long bookId = Long.parseLong(input.bookId());

        // Проверяем, существует ли книга
        if (!storage.books.containsKey(bookId)) {
            throw new RuntimeException("Book with id=" + bookId + " not found");
        }

        long id = reviewSequence.incrementAndGet();
        Review review = Review.builder()
                .id(id)
                .text(input.text())
                .rating(input.rating())
                .createdAt(OffsetDateTime.now())
                .build();

        // Сохраняем отзыв в хранилище (если ключа нет – создаём новый список)
        storage.bookReviews.computeIfAbsent(bookId, k -> new ArrayList<>()).add(review);

        return review;  // обязательно вернуть объект, не null
    }
}
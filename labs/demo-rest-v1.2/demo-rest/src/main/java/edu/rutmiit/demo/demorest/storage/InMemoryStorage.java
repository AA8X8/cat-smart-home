package edu.rutmiit.demo.demorest.storage;

import edu.rutmiit.demo.booksapicontract.dto.AuthorResponse;
import edu.rutmiit.demo.booksapicontract.dto.BookResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryStorage {
    public final Map<Long, AuthorResponse> authors = new ConcurrentHashMap<>();
    public final Map<Long, BookResponse> books = new ConcurrentHashMap<>();

    public final AtomicLong authorSequence = new AtomicLong(0);
    public final AtomicLong bookSequence = new AtomicLong(0);

    @PostConstruct
    public void init() {
        // Автор 1
        AuthorResponse author1 = new AuthorResponse(
                authorSequence.incrementAndGet(),
                "Лев",
                "Толстой",
                "Лев Толстой",
                null,
                "Русский писатель, один из наиболее известных авторов в мировой литературе.",
                LocalDate.of(1828, 9, 9),
                "Русский",
                2,
                null
        );

        // Автор 2
        AuthorResponse author2 = new AuthorResponse(
                authorSequence.incrementAndGet(),
                "Фёдор",
                "Достоевский",
                "Фёдор Достоевский",
                null,
                "Русский писатель, мыслитель, философ и публицист.",
                LocalDate.of(1821, 11, 11),
                "Русский",
                1,
                null
        );

        authors.put(author1.getId(), author1);
        authors.put(author2.getId(), author2);

        // Книга 1
        long bookId1 = bookSequence.incrementAndGet();
        BookResponse book1 = new BookResponse(
                bookId1,
                "Война и мир",
                "9785389062598",
                author1,
                "Эпический роман-хроника, описывающий русское общество эпохи войн против Наполеона.",
                "Исторический роман",
                1869,
                "ru",
                LocalDateTime.now(),
                null
        );
        books.put(bookId1, book1);

        // Книга 2
        long bookId2 = bookSequence.incrementAndGet();
        BookResponse book2 = new BookResponse(
                bookId2,
                "Преступление и наказание",
                "9785389062599",
                author2,
                "Роман о моральной дилемме студента Раскольникова.",
                "Психологический роман",
                1866,
                "ru",
                LocalDateTime.now(),
                null
        );
        books.put(bookId2, book2);

        // Книга 3
        long bookId3 = bookSequence.incrementAndGet();
        BookResponse book3 = new BookResponse(
                bookId3,
                "Анна Каренина",
                "9785389062597",
                author1,
                "Роман о трагической судьбе замужней дамы высшего общества.",
                "Роман",
                1877,
                "ru",
                LocalDateTime.now(),
                null
        );
        books.put(bookId3, book3);
    }
}
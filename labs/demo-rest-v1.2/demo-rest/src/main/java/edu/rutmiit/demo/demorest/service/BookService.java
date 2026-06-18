package edu.rutmiit.demo.demorest.service;

import edu.rutmiit.demo.booksapicontract.dto.*;
import edu.rutmiit.demo.booksapicontract.exception.IsbnAlreadyExistsException;
import edu.rutmiit.demo.booksapicontract.exception.ResourceNotFoundException;
import edu.rutmiit.demo.demorest.storage.InMemoryStorage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class BookService {

    private final InMemoryStorage storage;
    private final AuthorService authorService;

    public BookService(InMemoryStorage storage, @Lazy AuthorService authorService) {
        this.storage = storage;
        this.authorService = authorService;
    }

    public BookResponse findBookById(Long id) {
        return Optional.ofNullable(storage.books.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Book", id));
    }

    public PagedResponse<BookResponse> findAllBooks(Long authorId, String genre, Integer publishedYear,
                                                    String titleSearch, int page, int size) {
        Stream<BookResponse> stream = storage.books.values().stream()
                .sorted((b1, b2) -> b1.getId().compareTo(b2.getId()));

        if (authorId != null) {
            stream = stream.filter(b -> b.getAuthor() != null && b.getAuthor().getId().equals(authorId));
        }
        if (genre != null && !genre.isBlank()) {
            stream = stream.filter(b -> genre.equalsIgnoreCase(b.getGenre()));
        }
        if (publishedYear != null) {
            stream = stream.filter(b -> publishedYear.equals(b.getPublishedYear()));
        }
        if (titleSearch != null && !titleSearch.isBlank()) {
            String q = titleSearch.toLowerCase();
            stream = stream.filter(b -> b.getTitle() != null && b.getTitle().toLowerCase().contains(q));
        }
        List<BookResponse> allBooks = stream.toList();

        List<BookResponse> bookWithFreshAuthor = freshAuthor(allBooks);
        int totalElements = bookWithFreshAuthor.size();
        int totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 1;
        int from = page * size;
        int to = Math.min(from + size, totalElements);
        List<BookResponse> content = (from >= totalElements) ? List.of() : bookWithFreshAuthor.subList(from, to);
        return new PagedResponse<>(content, page, size, totalElements, totalPages, page >= totalPages - 1);
    }

    public List<BookResponse> freshAuthor(List<BookResponse> allBooks) {
        List<BookResponse> freshAuthor = new ArrayList<>();
        for (BookResponse book : allBooks) {
            AuthorResponse authorResponse = authorService.findById(book.getAuthor().getId());
            BookResponse bookResponse = new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getIsbn(),
                    authorResponse,
                    book.getDescription(),
                    book.getGenre(),
                    book.getPublishedYear(),
                    book.getLanguage(),
                    book.getCreatedAt(),
                    book.getUpdatedAt()
            );
            freshAuthor.add(bookResponse);
        }
        return freshAuthor;
    }

    public BookResponse createBook(BookRequest request) {
        validateIsbn(request.isbn(), null);
        AuthorResponse author = authorService.findById(request.authorId());

        long id = storage.bookSequence.incrementAndGet();
        BookResponse book = new BookResponse(
                id,
                request.title(),
                request.isbn(),
                author,
                request.description(),
                request.genre(),
                request.publishedYear(),
                request.language(),
                LocalDateTime.now(),
                null
        );

        storage.books.put(id, book);
        authorService.recalculateBooksCount(request.authorId());

        // Обновляем книгу с актуальным автором (после пересчёта количества книг)
        AuthorResponse updatedAuthor = authorService.findById(request.authorId());
        book = new BookResponse(
                id,
                request.title(),
                request.isbn(),
                updatedAuthor,
                request.description(),
                request.genre(),
                request.publishedYear(),
                request.language(),
                LocalDateTime.now(),
                null
        );
        storage.books.put(id, book);

        return book;
    }

    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        BookResponse existing = findBookById(id);
        validateIsbn(request.isbn(), id);

        BookResponse updated = new BookResponse(
                id,
                request.title(),
                request.isbn(),
                existing.getAuthor(),
                request.description(),
                request.genre(),
                request.publishedYear(),
                request.language(),
                existing.getCreatedAt(),
                LocalDateTime.now()
        );
        storage.books.put(id, updated);
        return updated;
    }

    public BookResponse patchBook(Long id, PatchBookRequest request) {
        BookResponse existing = findBookById(id);

        if (request.isbn() != null && !request.isbn().equalsIgnoreCase(existing.getIsbn())) {
            validateIsbn(request.isbn(), id);
        }

        BookResponse updated = new BookResponse(
                id,
                request.title() != null ? request.title() : existing.getTitle(),
                request.isbn() != null ? request.isbn() : existing.getIsbn(),
                existing.getAuthor(),
                request.description() != null ? request.description() : existing.getDescription(),
                request.genre() != null ? request.genre() : existing.getGenre(),
                request.publishedYear() != null ? request.publishedYear() : existing.getPublishedYear(),
                request.language() != null ? request.language() : existing.getLanguage(),
                existing.getCreatedAt(),
                LocalDateTime.now()
        );
        storage.books.put(id, updated);
        return updated;
    }

    public void deleteBook(Long id) {
        BookResponse book = findBookById(id);
        AuthorResponse author = book.getAuthor();
        storage.books.remove(id);
        authorService.recalculateBooksCount(author.getId());
    }

    public void deleteBooksByAuthorId(Long authorId) {
        List<Long> toDelete = storage.books.values().stream()
                .filter(b -> b.getAuthor() != null && b.getAuthor().getId().equals(authorId))
                .map(BookResponse::getId)
                .toList();
        toDelete.forEach(storage.books::remove);
        authorService.recalculateBooksCount(authorId);
    }

    private void validateIsbn(String isbn, Long currentBookId) {
        storage.books.values().stream()
                .filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
                .filter(b -> !b.getId().equals(currentBookId))
                .findAny()
                .ifPresent(b -> {
                    throw new IsbnAlreadyExistsException(isbn);
                });
    }

    public List<BookSummaryResponse> getAllBookSummary() {
        List<BookSummaryResponse> bookSummaryResponses = new ArrayList<>();
        List<BookResponse> bookResponses = storage.books.values().stream().toList();
        for (BookResponse book : bookResponses) {
            BookSummaryResponse summary = new BookSummaryResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getIsbn()
            );
            bookSummaryResponses.add(summary);
        }
        return bookSummaryResponses;
    }
}



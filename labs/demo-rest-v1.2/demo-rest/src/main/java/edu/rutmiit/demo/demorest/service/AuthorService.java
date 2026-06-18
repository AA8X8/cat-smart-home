package edu.rutmiit.demo.demorest.service;

import edu.rutmiit.demo.booksapicontract.dto.*;
import edu.rutmiit.demo.booksapicontract.exception.ResourceNotFoundException;
import edu.rutmiit.demo.demorest.storage.InMemoryStorage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final InMemoryStorage storage;
    private final BookService bookService;

    public AuthorService(InMemoryStorage storage, @Lazy BookService bookService) {
        this.storage = storage;
        this.bookService = bookService;
    }

    public PagedResponse<AuthorResponse> findAll(int page, int size) {
        List<AuthorResponse> all = storage.authors.values().stream()
                .sorted(Comparator.comparingLong(AuthorResponse::getId))
                .toList();
        int totalElements = all.size();
        int totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 1;
        int from = page * size;
        int to = Math.min(from + size, totalElements);
        List<AuthorResponse> content = (from >= totalElements) ? List.of() : all.subList(from, to);
        return new PagedResponse<>(content, page, size, totalElements, totalPages, page >= totalPages - 1);
    }

    public AuthorResponse findById(Long id) {
        return Optional.ofNullable(storage.authors.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Author", id));
    }

    public AuthorResponse create(AuthorRequest request) {
        long id = storage.authorSequence.incrementAndGet();
        String fullName = request.firstName() + " " + request.lastName();
        AuthorResponse author = new AuthorResponse(
                id,
                request.firstName(),
                request.lastName(),
                fullName,
                request.email(),
                request.bio(),
                request.birthDate(),
                request.nationality(),
                0,
                request.numberPhone()
        );
        storage.authors.put(id, author);
        return author;
    }

    public AuthorResponse update(Long id, AuthorRequest request) {
        AuthorResponse existing = findById(id);
        String fullName = request.firstName() + " " + request.lastName();
        AuthorResponse updatedAuthor = new AuthorResponse(
                id,
                request.firstName(),
                request.lastName(),
                fullName,
                request.email(),
                request.bio(),
                request.birthDate(),
                request.nationality(),
                existing.getBooksCount(),
                request.numberPhone()
        );
        storage.authors.put(id, updatedAuthor);
        return updatedAuthor;
    }

    public AuthorResponse patchAuthor(Long id, PatchAuthorRequest request) {
        AuthorResponse existing = findById(id);
        String newFirstName = request.firstName() != null ? request.firstName() : existing.getFirstName();
        String newLastName = request.lastName() != null ? request.lastName() : existing.getLastName();
        AuthorResponse updated = new AuthorResponse(
                id,
                newFirstName,
                newLastName,
                newFirstName + " " + newLastName,
                request.email() != null ? request.email() : existing.getEmail(),
                request.bio() != null ? request.bio() : existing.getBio(),
                request.birthDate() != null ? request.birthDate() : existing.getBirthDate(),
                request.nationality() != null ? request.nationality() : existing.getNationality(),
                existing.getBooksCount(),
                request.numberPhone() != null ? request.numberPhone() : existing.getNumberPhone()
        );
        storage.authors.put(id, updated);
        return updated;
    }

    public void delete(Long id) {
        findById(id);
        bookService.deleteBooksByAuthorId(id);
        storage.authors.remove(id);
    }

    public PagedResponse<AuthorResponse> searchByName(String query, int page, int size) {
        List<AuthorResponse> all = storage.authors.values().stream().toList();
        List<AuthorResponse> byName = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            String fullName = all.get(i).getFullName();
            String firstName = all.get(i).getFirstName();
            String lastName = all.get(i).getLastName();
            if (fullName.equalsIgnoreCase(query) || firstName.equalsIgnoreCase(query) || lastName.equalsIgnoreCase(query)) {
                byName.add(all.get(i));
            }
        }

        int totalElements = byName.size();
        int totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 1;
        int from = page * size;
        int to = Math.min(from + size, totalElements);
        List<AuthorResponse> content = (from >= totalElements) ? List.of() : byName.subList(from, to);
        return new PagedResponse<>(content, page, size, totalElements, totalPages, page >= totalPages - 1);
    }

    public void recalculateBooksCount(Long authorId) {
        AuthorResponse author = this.findById(authorId);
        List<BookResponse> booksInStorage = storage.books.values().stream().toList();
        int count = 0;
        for (int i = 0; i < booksInStorage.size(); i++) {
            BookResponse book = booksInStorage.get(i);
            if (book.getAuthor() != null && book.getAuthor().getId().equals(authorId)) count++;
        }
        AuthorResponse updateAuthor = new AuthorResponse(
                authorId,
                author.getFirstName(),
                author.getLastName(),
                author.getFullName(),
                author.getEmail(),
                author.getBio(),
                author.getBirthDate(),
                author.getNationality(),
                count,
                author.getNumberPhone()
        );
        storage.authors.put(authorId, updateAuthor);
    }
}
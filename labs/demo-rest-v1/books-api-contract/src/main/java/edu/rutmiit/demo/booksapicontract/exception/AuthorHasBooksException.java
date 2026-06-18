package edu.rutmiit.demo.booksapicontract.exception;

/**
 * Исключение, выбрасываемое при попытке удалить автора,
 * у которого есть хотя бы одна книга (без каскадного удаления).
 */
public class AuthorHasBooksException extends RuntimeException {

    private final Long authorId;
    private final int booksCount;

    public AuthorHasBooksException(Long authorId, int booksCount) {
        super(String.format("Автор с id=%d имеет такое количество %d книг. Нельзя удалить автора пока у него есть книги.", authorId, booksCount));
        this.authorId = authorId;
        this.booksCount = booksCount;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public int getBooksCount() {
        return booksCount;
    }
}
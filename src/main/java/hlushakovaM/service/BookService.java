package hlushakovaM.service;

import com.google.inject.persist.Transactional;
import hlushakovaM.model.Author;
import hlushakovaM.model.Book;
import hlushakovaM.model.BookDetails;
import hlushakovaM.model.Review;
import hlushakovaM.repository.BookRepository;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BookService {
    private final BookRepository repository;

    @Inject
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findBooksPublishedAfterYear(int year) {
        return repository.findBooksPublishedAfterYear(year);
    }
    public List<Book> findBookByAntonChekhov(String authorName) {
        return repository.findBookByAntonChekhov(authorName);
    }
    public List<Book> findBooksWithReviewRating4(int rating) {
        return repository.findBooksWithReviewRating4(rating);
    }
    public List<Book> findBooksWithTitleContaining(String titleFragment) {
        return repository.findBooksWithTitleContaining(titleFragment);
    }
    public List<Author> findAuthorsByBookId(Long bookId) {
        return repository.findAuthorsByBookId(bookId);
    }
    public Long countBooksPublishedBefore1850() {
        return repository.countBooksPublishedBefore1850();
    }
    public List<Book> findBooksWithoutAuthors() {
        return repository.findBooksWithoutAuthors();
    }
    public List<Book> findFirst3BooksWithReviews() {
        List<Book> booksWithReviews = repository.findFirst3BooksWithReviews();
        return booksWithReviews.stream().limit(3).toList();
    }
    public List<Author> findAuthorsContainingTolstoy() {
        return repository.findAuthorsContainingTolstoy();
    }
    public Long countReviewsWithRatingAbove4() {
        return repository.countReviewsWithRatingAbove4();
    }
}

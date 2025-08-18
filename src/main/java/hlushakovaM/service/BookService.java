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

    @Transactional
    public Book saveWithDetails(Book book, BookDetails bookDetails, List<Review> reviews, Set<Author> authors) {
        return repository.saveWithDetails(book, bookDetails, reviews, authors);
    }

    public Optional<Book> findBookWithDetails(Long bookId) {
        return repository.findBookWithDetails(bookId);
    }

    @Transactional
    public void updateBookDetails(Long id, String newIsbn) {
        repository.updateBookDetails(id, newIsbn);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

/*    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }*/

   /* @Transactional
    public Book save(Book book) {
        return repository.save(book);
    }*/


    /*public Book autoUpdateBook(Long id, String newTitle, String author) {
        return repository.autoUpdateBook(id, newTitle, author);
    }*/

    @Transactional
    public void deleteBookById(Long id) {
        repository.deleteBookById(id);
    }

    public List<Book> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    public List<Book> findByAuthor(String author) {
        return repository.findByAuthor(author);
    }

    //1.
    public List<Book> findBooksByLeoTolstoy() {
        return repository.findBooksByLeoTolstoy();
    }
    //2.
    public List<Book> findBooksPublishedIn1869() {
        return repository.findBooksPublishedIn1869();
    }

    //3.
    public List<Book> findBooksWithIsbnContaining(String isbnSubstring) {
        return repository.findBooksWithIsbnContaining(isbnSubstring);
    }

    //4.
    public List<Review> findReviewsForBook(Long bookId) {
        return repository.findReviewsForBook(bookId);
    }
    //5.
    public List<Object[]> findReviewsWithRating5() {
        return repository.findReviewsWithRating5();
    }
    // 6.
    public List<Book> findBooksWithTitleContainingWar() {
        return repository.findBooksWithTitleContainingWar();
    }
    // 7.
    public List<Author> findAuthorsForBook(Long bookId) {
        return repository.findAuthorsForBook(bookId);
    }
    // 8.
    public Long countReviewsForBook(Long bookId) {
        return repository.countReviewsForBook(bookId);
    }
    // 9.
    public List<Book> findBooksWithoutReviews() {
        return repository.findBooksWithoutReviews();
    }
   /* @Transactional
    public Optional<Book> update(Long id, String newTitle) {
        Optional<Book> bookOptional = repository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setTitle(newTitle);
            Book saveBook = repository.save(book);
            return Optional.of(saveBook);
        }
        return Optional.empty();
    }*/
}

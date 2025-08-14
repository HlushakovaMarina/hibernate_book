package hlushakovaM.repository;

import hlushakovaM.model.Author;
import hlushakovaM.model.Book;
import hlushakovaM.model.BookDetails;
import hlushakovaM.model.Review;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BookRepository {
    private static final Logger logger = LogManager.getLogger(BookRepository.class);
    private final EntityManager em;

    @Inject
    public BookRepository(EntityManager em) {
        this.em = em;
    }

    public List<Book> findBookWithDetailByIsbn(String isbn) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.bookDetails.isbn = :isbn" +
                "ORDER BY b.bookDetails.publicationYear DESC", Book.class);//сджойнили таблицы
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }

    public long countBooksAfterYear(int year) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Book b WHERE b.bookDetails.publicationYear > :year", Long.class);
        query.setParameter("year", year);
        return query.getSingleResult();
    }

    public List<Review> findReviewByBookId(Long bookId) {
        TypedQuery<Review> query = em.createQuery("SELECT r FROM Review r WHERE r.book.id = :bookId", Review.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    public List<Author>findAuthorsByBookId(Long bookId){
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a JOIN a.books b WHERE b.id = :bookId", Author.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }


    public Book saveWithDetails(Book book, BookDetails bookDetails,
                                List<Review> reviews, Set<Author> authors) {
        book.setBookDetails(bookDetails);
        book.setReviews(reviews);
        book.setAuthors(authors);
        authors.forEach(a -> a.getBooks().add(book));
        reviews.forEach(r -> r.setBook(book));

        //bookDetails.setBook(book);
        return em.merge(book);
    }

    public Optional<Book> findBookWithDetails(Long bookId) {
        Book book = em.find(Book.class, bookId);
        return Optional.ofNullable(book);
    }

    public void updateBookDetails(Long id, String newIsbn) {
        Book book = em.find(Book.class, id);
        if (book != null && book.getBookDetails() != null) {
            book.getBookDetails().setIsbn(newIsbn);
            em.merge(book);
        }
    }

    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    /*public Optional<Book> findById(Long id) {
        Book book = em.find(Book.class, id);
        return Optional.ofNullable(book);
    }*/

    /*public Book save(Book book) {
        logger.info("Create book: {}", book);
        em.persist(book);
        return book;
    }*/

  /*  public Book autoUpdateBook(Long id, String newTitle,  ) {
        logger.info("Updating book with ID: {}", id);
        Book book = em.find(Book.class, id);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthors(author);
            logger.info("Modified book in context: {}", book);
        }
        return book;
    }*/

    public List<Book> findByTitle(String title) {
        return em.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title", Book.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }

    public List<Book> findByAuthor(String author) {
        return em.createNamedQuery("Book.findByAuthor", Book.class)
                .setParameter("author", author)
                .getResultList();
    }

    public void deleteBookById(Long id) {
        logger.info("Deleting book with ID: {}", id);
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);

        }
    }
}

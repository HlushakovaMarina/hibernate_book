package hlushakovaM.repository;

import hlushakovaM.model.Author;
import hlushakovaM.model.Book;
import hlushakovaM.model.BookDetails;
import hlushakovaM.model.Review;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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

    public List<Book> findBooksByAuthorName(String authorName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();//интерфейс для зпросов
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, Author> author = book.join("authors");
        query.select(book)
                .where(cb.equal(author.get("name"), authorName))
                .orderBy(cb.asc(book.get("title")));
        return em.createQuery(query).getResultList();
    }

    public List<Book> findBooksByYear(int year) {
        CriteriaBuilder cb = em.getCriteriaBuilder();//интерфейс для зпросов
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, BookDetails> bookDetails = book.join("bookDetails");
        query.select(book)
                .where(cb.equal(bookDetails.get("publicationYear"), year));
        return em.createQuery(query).getResultList();
    }

    public List<Review> findReviewsByBookId(Long bookId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> review = query.from(Review.class);
        query.select(review)
                .where(cb.equal(review.get("book").get("id"), bookId));
        return em.createQuery(query).getResultList();
    }

    public Long countReviewsByBookId(Long bookId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Review> review = query.from(Review.class);
        query.select(cb.count(review))
                .where(cb.equal(review.get("book").get("id"), bookId));
        return em.createQuery(query).getSingleResult();
    }

    public List<Book> findBooksByIsbn(String isbn) {
        CriteriaBuilder cb = em.getCriteriaBuilder();//интерфейс для зпросов
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, BookDetails> bookDetails = book.join("bookDetails");
        query.select(book)
                .where(cb.like(cb.lower(bookDetails.get("isbn")), "%" + isbn.toLowerCase() + "%"));
        return em.createQuery(query).getResultList();
    }

    public List<Book> findBooksWithoutReviews() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, Review> reviews = book.join("reviews", JoinType.LEFT);
        query.select(book)
                .where(reviews.isNull())
                .orderBy(cb.asc(book.get("title")));
        return em.createQuery(query).getResultList();
    }
}

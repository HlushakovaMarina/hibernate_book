package hlushakovaM.repository;

import hlushakovaM.model.Author;
import hlushakovaM.model.Book;
import hlushakovaM.model.BookDetails;
import hlushakovaM.model.Review;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
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

    //4.
    public List<Book> findBooksByIsbn(String isbn) {
        CriteriaBuilder cb = em.getCriteriaBuilder();//интерфейс для зпросов
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, BookDetails> bookDetails = book.join("bookDetails");
        query.select(book)
                .where(cb.like(cb.lower(bookDetails.get("isbn")), "%" + isbn.toLowerCase() + "%"));
        return em.createQuery(query).getResultList();
    }

    //9.
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

    //1. Найти книги, изданные после 1900 года
    public List<Book> findBooksPublishedAfterYear(int year) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, BookDetails> bookDetails = book.join("bookDetails");
        query.select(book)
                .where(cb.equal(bookDetails.get("publicationYear"), year))
                .orderBy(cb.desc(bookDetails.get("publicationYear")));
        return em.createQuery(query).getResultList();
    }

    //2. Найти книги Антона Чехова
    public List<Book> findBookByAntonChekhov(String authorName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, Author> author = book.join("author");
        query.select(book)
                .where(cb.equal(author.get("name"), authorName))
                .orderBy(cb.asc(book.get("title")));
        return em.createQuery(query).getResultList();
    }

    //3. Найти книги с рейтингом отзывов 4
    public List<Book> findBooksWithReviewRating4(int rating) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, Review> reviews = book.join("reviews");
        query.select(book)
                .where(cb.equal(reviews.get("rating"), rating));
        return em.createQuery(query).getResultList();
    }

    // Задача 4: Найти книги с названием, содержащим "двенадцать"
    public List<Book> findBooksWithTitleContaining(String titleFragment) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        query.select(book)
                .where(cb.like(cb.lower(book.get("title")), "%" + titleFragment.toLowerCase() + "%"))
                .orderBy(cb.asc(book.get("title")));
        return em.createQuery(query).getResultList();
    }

    // Задача 5: Найти авторов книги "Двенадцать стульев"
    public List<Author> findAuthorsByBookId(Long bookId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> query = cb.createQuery(Author.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, Author> author = book.join("authors");
        query.select(author).where(cb.equal(book.get("id"), bookId));
        return em.createQuery(query).getResultList();
    }

    // Задача 6: Подсчёт книг, изданных до 1850 года
    public Long countBooksPublishedBefore1850() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, BookDetails> bookDetails = book.join("bookDetails");
        query.select(cb.count(book)).where(cb.lessThan(bookDetails.get("publicationYear"), 1850));
        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    // Задача 7: Найти книги без авторов
    public List<Book> findBooksWithoutAuthors() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        query.select(book).where(cb.isEmpty(book.get("authors"))).orderBy(cb.asc(book.get("title")));
        return em.createQuery(query).getResultList();
    }

    // Задача 8: Найти первые 3 книги с отзывами
    public List<Book> findFirst3BooksWithReviews() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, Review> review = book.join("reviews", JoinType.INNER);
        query.select(book).orderBy(cb.asc(book.get("title")));

        return em.createQuery(query).setMaxResults(3).getResultList();
    }

    // Задача 9: Найти авторов с именем, содержащим "Толстой"
    public List<Author> findAuthorsContainingTolstoy() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> query = cb.createQuery(Author.class);
        Root<Author> author = query.from(Author.class);
        query.select(author).where(cb.like(cb.lower(author.get("name")), "%Толстой%"));
        return em.createQuery(query).getResultList();
    }

    // Задача 10: Подсчёт отзывов с рейтингом выше 4
    public Long countReviewsWithRatingAbove4() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Review> review = query.from(Review.class);
        query.select(cb.count(review)).where(cb.greaterThan(review.get("rating"), 4));
        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    // Задача 5: Найти авторов книги "Двенадцать стульев"
    List<Author> findAuthorsByBookId(Long bookId) {
        TypedQuery<Author> query = em.createQuery("SELECT b.authors FROM " +
                "Book b WHERE b.id = :bookId");
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    // Задача 6: Подсчёт книг, изданных до 1850 года
    public long countBooksPublishedBefore1850(int year) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Book " +
                "b WHERE b.bookDetails.publicationYear < 1850");
        query.setParameter("year", year);
        return query.getSingleResult();
    }

    // Задача 7: Найти книги без авторов
    public List<Book> findBooksWithoutAuthors(Long bookId) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE " +
                "b.authors IS EMPTY ORDER BY b.title");
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    // Задача 8: Найти первые 3 книги с отзывами
    public List<Book> findFirst3BooksWithReviews() {
        TypedQuery<Book> query = em.createQuery("SELECT DISTINCT b FROM Book b JOIN FETCH b.reviews WHERE b.reviews IS NOT EMPTY ORDER BY b.title ASC");
        query.setParameter();
        return query.getResultList();
    }

    // Задача 9: Найти авторов с именем, содержащим "Толстой"
    public List<Author> findAuthorsByNameContaining(String namePart) {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(concat('%', :namePart, '%'))")
        query.setParameter("", namePart);
        return query.getResultList();
    }

    // Задача 10: Подсчёт отзывов с рейтингом выше 4
    public Long countReviewsWithRatingAbove4() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(r) FROM Review r WHERE r.rating > 4");
        query.setParameter();
        return query.getResultList();
    }
}

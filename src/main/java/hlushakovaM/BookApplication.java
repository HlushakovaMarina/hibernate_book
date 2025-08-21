package hlushakovaM;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import hlushakovaM.config.AppModule;
import hlushakovaM.model.Author;
import hlushakovaM.model.Book;
import hlushakovaM.model.BookDetails;

import hlushakovaM.model.Review;
import hlushakovaM.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BookApplication {
    private static final Logger logger = LogManager.getLogger(AppModule.class);


    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        PersistService persistService = injector.getInstance(PersistService.class);
        persistService.start(); //только для Google Guies

        BookService service = injector.getInstance(BookService.class);
        try {
            //1. Пример использования findBooksPublishedAfterYear
            List<Book> booksAfter1900 = service.findBooksPublishedAfterYear(1900);
            logger.info("Книги, изданные после 1900 года:", booksAfter1900);
            booksAfter1900.forEach(book -> System.out.println(book.getTitle() + " - " + book.getBookDetails().getPublicationYear()));
            //2.
            List<Book> antonChekhov = service.findBookByAntonChekhov("Антон Чехов");
            logger.info("Книги Антона Чехова:", antonChekhov);
            antonChekhov.forEach(book -> System.out.println(book.getTitle()));
            //3.
            List<Book> booksWithReviewRating4 = service.findBooksWithReviewRating4();
            logger.info("", booksWithReviewRating4);
            //4. Пример использования findBooksWithTitleContaining
            List<Book> booksWithTwelve = service.findBooksWithTitleContaining("двенадцать");
            System.out.println("\nКниги, содержащие 'двенадцать' в названии:");
            booksWithTwelve.forEach(book -> System.out.println(book.getTitle()));

            //5.
            List<Author> authorsOfTwelveChairs = service.findAuthorsByBookId(31L);
            logger.info("Authors of Twelve Chairs: {}", authorsOfTwelveChairs);
            //6.
            Long booksBefore1850 = service.countBooksPublishedBefore1850();
            logger.info("Books published before 1850: {}", booksBefore1850);
            //7.
            List<Book> booksWithoutAuthors = service.findBooksWithoutAuthors();
            logger.info("Книги без автора: {}", booksWithoutAuthors);
            //8.
            List<Book> first3WithReviews = service.findFirst3BooksWithReviews();
            logger.info("FПервые 3 книги с отзывами: {}", first3WithReviews);
            //9.
            List<Author> authorsContainingTolstoy = service.findAuthorsContainingTolstoy();
            logger.info("Авторы, содержащие Толстого: {}", authorsContainingTolstoy);
            //10.
            Long reviewsAbove4 = service.countReviewsWithRatingAbove4();
            logger.info("Отзывы с рейтингом выше 4: {}", reviewsAbove4);
        } catch (Exception e) {
            logger.error("Ошибка в работе с БД", e);
        } finally {
            persistService.stop();
        }
    }
}

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

            Book book1 = new Book("Война и мир3");
            BookDetails bookDetails = new BookDetails("978-5-17-123456-72", 1869);
            Set<Author> authors = Set.of(new Author("Лев Толстой"));
            /*       service.save(book1);*/
            List<Review> reviews = Arrays.asList(new Review("Отличная книга!", 5),
                    new Review("Классика", 4));
            book1.setReviews(reviews);
            service.saveWithDetails(book1, bookDetails, reviews, authors);

            logger.info("Create book with ID: {}", book1.getId());
            /*   service.save(book2);*//*
            logger.info("Create book with ID: {}", book2.getId());
            logger.info("Книги сохранены");*/

            Optional<Book> bookWithDetails = service.findBookWithDetails(43L);
            List<Book> books = service.findAll();
            books.forEach(logger::info);

            /*service.autoUpdateBook(book1.getId(),"Новое название книги","Новый автор книги");
            books.forEach(logger::info);

            List<Book> byTitle = service.findByTitle("Гарри Поттер и кубок огня");
            byTitle.forEach(logger::info);
            List<Book> byAuthor = service.findByAuthor("Джоан Роулинг");
            byAuthor.forEach(logger::info);*/

        } catch (Exception e) {
            logger.error("Ошибка в работе с БД", e);
        } finally {
            persistService.stop();
        }
    }
}

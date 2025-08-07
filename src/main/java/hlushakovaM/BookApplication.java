package hlushakovaM;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import hlushakovaM.config.AppModule;
import hlushakovaM.model.Book;
import hlushakovaM.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookApplication {
    private static final Logger logger = LogManager.getLogger(AppModule.class);


    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        PersistService persistService = injector.getInstance(PersistService.class);
        persistService.start(); //только для  гугл джус

        BookService service = injector.getInstance(BookService.class);
        try {
            Book book1 = new Book("Война и мир", "Лев Толстой");
            Book book2 = new Book("Преступление и наказание", "Фёдор Достоевский");
            service.save(book1);
            logger.info("Create book with ID: {}", book1.getId());
            service.save(book2);
            logger.info("Create book with ID: {}", book2.getId());
            logger.info("Книги сохранены");

            List<Book> books = service.findAll();
            books.forEach(logger::info);

            service.autoUpdateBook(book1.getId(),"Новое название книги","Новый автор книги");
            books.forEach(logger::info);

            List<Book> byTitle = service.findByTitle("Гарри Поттер и кубок огня");
            byTitle.forEach(logger::info);
            List<Book> byAuthor = service.findByAuthor("Джоан Роулинг");
            byAuthor.forEach(logger::info);

        } catch (Exception e) {
            logger.error("Ошибка в работе с БД", e);
        } finally {
            persistService.stop();
        }
    }
}

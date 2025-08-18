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

        } catch (Exception e) {
            logger.error("Ошибка в работе с БД", e);
        } finally {
            persistService.stop();
        }
    }
}

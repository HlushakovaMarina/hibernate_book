package hlushakovaM.config;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import hlushakovaM.repository.BookRepository;
import hlushakovaM.service.BookService;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new JpaPersistModule("book-pu"));
        bind(BookRepository.class);
        bind(BookService.class);
    }
}
package hlushakovaM.config;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.persist.jpa.JpaPersistOptions;
import hlushakovaM.repository.BookRepository;
import hlushakovaM.service.BookService;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        JpaPersistOptions options = JpaPersistOptions.builder()
                        .setAutoBeginWorkOnEntityManagerCreation(true)
                                .build();
        install(new JpaPersistModule("book-pu", options));
        bind(BookRepository.class);
        bind(BookService.class);
    }
}
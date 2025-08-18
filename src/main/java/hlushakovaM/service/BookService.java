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


}

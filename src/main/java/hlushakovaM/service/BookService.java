package hlushakovaM.service;

import hlushakovaM.model.Book;
import hlushakovaM.repository.BookRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookRepository repository;

    @Inject
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Book save(Book book) {
        return repository.save(book);
    }

    @Transactional
    public Optional<Book> update(Long id,String newTitle){
        Optional<Book> bookOptional = repository.findById(id);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            book.setTitle(newTitle);
            Book saveBook = repository.save(book);
            return Optional.of(saveBook);
        }
        return Optional.empty();
    }
}

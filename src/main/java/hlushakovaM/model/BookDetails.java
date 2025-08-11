package hlushakovaM.model;

import jakarta.persistence.*;

@Entity
@Table(name = "book_details")
public class BookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String isbn;
    @Column
    private Integer publicationYear;
    @OneToOne(mappedBy = "bookDetails")
    private Book book;

    public BookDetails() {
    }

    public BookDetails(String isbn, Integer publicationYear) {
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public BookDetails(String isbn, Integer publicationYear, Book book) {
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookDetails{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", book=" + book +
                '}';
    }
}

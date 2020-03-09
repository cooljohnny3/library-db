package com.john.librarydb.service;

import com.john.librarydb.dao.AuthorDAO;
import com.john.librarydb.dao.BookDAO;
import com.john.librarydb.dao.GenreDAO;
import com.john.librarydb.dao.LibraryDAO;
import com.john.librarydb.model.Author;
import com.john.librarydb.model.Book;
import com.john.librarydb.model.Genre;
import com.john.librarydb.model.Library;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final GenreDAO genreDAO;
    private final LibraryDAO libraryDAO;

    public BookService(@Qualifier("mySQL") BookDAO bookDAO,
                       @Qualifier("mySQL") AuthorDAO authorDAO,
                       @Qualifier("mySQL") GenreDAO genreDAO,
                       @Qualifier("mySQL") LibraryDAO libraryDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.genreDAO = genreDAO;
        this.libraryDAO = libraryDAO;
    }

    // TODO: Possibly change to be like checkLibrary
    private void checkAuthorAndGenre(Book book) {
        Optional<Author> optionalAuthor = authorDAO.getAuthorByName(
                book.getAuthor().getFirstName(),
                book.getAuthor().getLastName()
        );
        if(optionalAuthor.isEmpty()) {
            book.getAuthor().setId(UUID.randomUUID());
            authorDAO.addAuthor(book.getAuthor());
        } else {
            book.getAuthor().setId(optionalAuthor.get().getId());
        }

        Optional<Genre> optionalGenre = genreDAO.getGenreByName(book.getGenre().getName());
        if(optionalGenre.isEmpty()) {
            book.getGenre().setId(UUID.randomUUID());
            genreDAO.addGenre(book.getGenre());
        } else {
            book.getGenre().setId(optionalGenre.get().getId());
        }
    }

    private boolean checkLibrary(UUID libraryId) {
        return libraryDAO.getLibraries().stream().filter(l -> l.getId().equals(libraryId)).count() > 0;
    }

    public int addBook(Book book) {
        if (!checkLibrary(book.getLibraryId())) {
            return 1;
        }
        checkAuthorAndGenre(book);
        if (bookDAO.addBook(book) == 1 || libraryDAO.addBookToLibrary(book) == 1)
            return 1;
        return 0;
    }

    public int deleteBookById(UUID id) {
        return bookDAO.deleteBookById(id);
    }

    public int updateBookById(UUID id, Book book) {
        if (!checkLibrary(book.getLibraryId())) {
            return 1;
        }
        checkAuthorAndGenre(book);
        if (bookDAO.addBook(book) == 1 || libraryDAO.addBookToLibrary(book) == 1)
            return 1;
        return 0;
    }

    public List<Book> getBooks() {
        return bookDAO.getBooks();
    }

    public Optional<Book> selectBookById(UUID id) {
        return bookDAO.selectBookById(id);
    }

    public void returnBook(Library library, Book book) {
        libraryDAO.addBookToLibrary(book);
    }

    public void checkOutBook(Library library, Book book) {
        libraryDAO.removeBookFromLibrary(book);
    }

    public void transfer(Library library1, Library library2, Book book) {
        libraryDAO.transfer(library1, library2, book);
    }
}

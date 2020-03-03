package com.john.librarydb.service;

import com.john.librarydb.dao.AuthorDAO;
import com.john.librarydb.dao.BookDAO;
import com.john.librarydb.dao.GenreDAO;
import com.john.librarydb.model.Author;
import com.john.librarydb.model.Book;
import com.john.librarydb.model.Genre;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final GenreDAO genreDAO;

    public BookService(@Qualifier("mySQL") BookDAO bookDAO,
                       @Qualifier("mySQL") AuthorDAO authorDAO,
                       @Qualifier("mySQL") GenreDAO genreDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.genreDAO = genreDAO;
    }

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

    public int addBook(Book book) {
        checkAuthorAndGenre(book);
        return bookDAO.addBook(book);
    }

    public int deleteBookById(UUID id) {
        return bookDAO.deleteBookById(id);
    }

    public int updateBookById(UUID id, Book book) {
        checkAuthorAndGenre(book);
        return bookDAO.updateBookById(id, book);
    }

    public List<Book> getBooks() {
        return bookDAO.getBooks();
    }

    public Optional<Book> selectBookById(UUID id) {
        return bookDAO.selectBookById(id);
    }
}

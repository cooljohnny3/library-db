package com.john.librarydb.dao;

import com.john.librarydb.model.Book;
import com.john.librarydb.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookDAO {
    int addBook(UUID id, Book book);
    default int addBook(Book book) {
        UUID id = UUID.randomUUID();
        return addBook(id, book);
    }
    int deleteBookById(UUID id);
    int updateBookById(UUID id, Book book);
    List<Book> getBooks();
    Optional<Book> selectBookById(UUID id);
}

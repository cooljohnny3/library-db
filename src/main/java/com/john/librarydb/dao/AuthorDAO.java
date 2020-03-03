package com.john.librarydb.dao;

import com.john.librarydb.model.Author;

import java.util.Optional;
import java.util.UUID;

public interface AuthorDAO {
    int addAuthor(UUID id, Author author);
    default int addAuthor(Author author) {
        UUID id = UUID.randomUUID();
        return addAuthor(id, author);
    }
    Optional<Author> getAuthorById(UUID id);
    Optional<Author> getAuthorByName(String firstName, String lastName);
    int removeAuthorById(UUID id);
}

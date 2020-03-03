package com.john.librarydb.dao;

import com.john.librarydb.model.Book;
import com.john.librarydb.model.Library;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LibraryDAO {
    int addLibrary(UUID id, Library library);
    default int addLibrary(Library library) {
        UUID id = UUID.randomUUID();
        return addLibrary(id, library);
    }
    int deleteLibraryById(UUID id);
    int updateLibraryById(UUID id, Library library);
    List<Library> getLibraries();
    Optional<Library> selectLibraryById(UUID id);
    int addBookToLibrary(Library library, Book book);
    List<Book> getBooksAtLibrary(Library library);
}

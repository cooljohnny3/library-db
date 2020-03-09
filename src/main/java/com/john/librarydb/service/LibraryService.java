package com.john.librarydb.service;

import com.john.librarydb.dao.LibraryDAO;
import com.john.librarydb.model.Book;
import com.john.librarydb.model.Library;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibraryService {
    private final LibraryDAO libraryDAO;

    public LibraryService(@Qualifier("mySQL") LibraryDAO libraryDAO) {
        this.libraryDAO = libraryDAO;
    }

    public int addLibrary(Library library) {
        return libraryDAO.addLibrary(library);
    }

    public int deleteLibraryById(UUID id) {
        if(!checkBooks(id)) {
            return 1;
        }
        return libraryDAO.deleteLibraryById(id);
    }

    private boolean checkBooks(UUID id) {
        return getBooksAtLibrary(selectLibraryById(id).get()).stream().count() > 0;
    }

    public int updateLibraryById(UUID id, Library library) {
        return libraryDAO.updateLibraryById(id, library);
    }

    public List<Library> getLibraries() {
        return libraryDAO.getLibraries();
    }

    public Optional<Library> selectLibraryById(UUID id) {
        return libraryDAO.selectLibraryById(id);
    }

    public List<Book> getBooksAtLibrary(Library library) {
        return libraryDAO.getBooksAtLibrary(library);
    }
}

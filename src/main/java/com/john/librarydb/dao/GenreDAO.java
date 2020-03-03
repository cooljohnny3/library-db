package com.john.librarydb.dao;

import com.john.librarydb.model.Author;
import com.john.librarydb.model.Genre;

import java.util.Optional;
import java.util.UUID;

public interface GenreDAO {
    int addGenre(UUID id, Genre genre);
    default int addGenre(Genre genre) {
        UUID id = UUID.randomUUID();
        return addGenre(id, genre);
    }
    Optional<Genre> getGenreById(UUID id);
    Optional<Genre> getGenreByName(String name);
    int removeGenreById(UUID id);
}

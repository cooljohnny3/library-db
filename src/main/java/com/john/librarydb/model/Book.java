package com.john.librarydb.model;

import java.util.UUID;

public class Book {
    private UUID id;
    private String title;
    private Author author;
    private Genre genre;
    private UUID libraryId;

    public Book(UUID id, String title, Author author, Genre genre, UUID libraryId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.libraryId = libraryId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public UUID getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(UUID libraryId) {
        this.libraryId = libraryId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}

package com.john.librarydb.model;

public class Transaction {
    public enum Request {
        RETURN,
        CHECKOUT,
        TRANSFER
    }

    private final Book book;
    private final Library library1;
    private Library library2;
    private final Request request;

    public Transaction(Book book, Library library1, Request request) {
        this.book = book;
        this.library1 = library1;
        this.request = request;
    }

    public Transaction(Book book, Library library1, Library library2, Request request) {
        this.book = book;
        this.library1 = library1;
        this.library2 = library2;
        this.request = request;
    }

    public Book getBook() {
        return book;
    }

    public Library getLibrary1() {
        return library1;
    }

    public Library getLibrary2() {
        return library2;
    }

    public Request getRequest() {
        return request;
    }
}

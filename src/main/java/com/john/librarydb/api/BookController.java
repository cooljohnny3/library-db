package com.john.librarydb.api;

import com.john.librarydb.model.Book;
import com.john.librarydb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/book")
@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public void addBook(@Valid @NotNull @RequestBody Book book) {
        bookService.addBook(book);
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") UUID id) {
        return bookService.selectBookById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable("id") UUID id, @Valid @NotNull @RequestBody Book book) {
        bookService.updateBookById(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id")UUID id) {
        bookService.deleteBookById(id);
    }
}

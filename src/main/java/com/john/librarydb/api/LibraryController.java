package com.john.librarydb.api;

import com.john.librarydb.model.Library;
import com.john.librarydb.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/library")
@Controller
public class LibraryController {
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/add")
    public void addLibrary(@Valid @NotNull @RequestBody Library library) {
        libraryService.addLibrary(library);
    }

    @GetMapping
    public List<Library> getLibraries() {
        return libraryService.getLibraries();
    }

    @GetMapping("/{id}")
    public Library getLibraryById(@PathVariable("id") UUID id) {
        return libraryService.selectLibraryById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public void updateLibrary(@PathVariable("id") UUID id, @Valid @NotNull @RequestBody Library library) {
        libraryService.updateLibraryById(id, library);
    }

    @DeleteMapping("/{id}")
    public void deleteLibrary(@PathVariable("id")UUID id) {
        libraryService.deleteLibraryById(id);
    }
}

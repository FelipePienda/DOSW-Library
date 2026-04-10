package edu.eci.tdd.controller;

import edu.eci.tdd.model.Book;
import edu.eci.tdd.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book, @RequestParam int quantity) {
        bookService.addBook(book, quantity);
        return ResponseEntity.ok("Libro agregado al inventario con " + quantity + " ejemplares.");
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Integer> getStock(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getAvailableUnits(id));
    }
}

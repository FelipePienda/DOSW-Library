package edu.eci.tdd.service;

import edu.eci.tdd.model.Book;
import edu.eci.tdd.exception.BookNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(); // Tu servicio que maneja el Mapa
    }

    @Test
    void testAddBookSuccess() {
        Book book = new Book("1", "Clean Code");
        bookService.addBook(book, 5);
        assertEquals(5, bookService.getAvailableUnits("1")); // Escenario Exitoso [cite: 22]
    }

    @Test
    void testLoanReducesStock() throws BookNotAvailableException {
        Book book = new Book("1", "Clean Code");
        bookService.addBook(book, 2);
        bookService.loanBook("1", "user123");
        assertEquals(1, bookService.getAvailableUnits("1")); // Verifica lógica de negocio [cite: 22]
    }

    @Test
    void testLoanFailsWhenNoStock() throws BookNotAvailableException {
        Book book = new Book("2", "Refactoring");
        bookService.addBook(book, 1);
        bookService.loanBook("2", "user123"); // Ahora stock = 0
        // Intentar prestar de nuevo debe fallar
        assertThrows(BookNotAvailableException.class, () -> {
            bookService.loanBook("2", "user456");
        });
    }

    @Test
void testReturnBookIncreasesStockCorrectly() throws BookNotAvailableException {
    Book book = new Book("4", "Java 8 in Action");
    bookService.addBook(book, 1);
    bookService.loanBook("4", "user123"); // Baja a 0
    bookService.returnBook("4", "user123"); // Sube a 1
    assertEquals(1, bookService.getAvailableUnits("4"));
}

@Test
void testReturnFailsForAlreadyReturnedLoan() throws BookNotAvailableException {
    Book book = new Book("5", "Spring Boot");
    bookService.addBook(book, 1);
    bookService.loanBook("5", "user123");
    bookService.returnBook("5", "user123"); // Primera devolución
    
    // Regla: No se puede devolver sobre un préstamo ya devuelto 
    assertThrows(IllegalStateException.class, () -> {
        bookService.returnBook("5", "user123");
    });
}

@Test
void testAddBookWithInvalidStockThrowsException() {
    Book book = new Book("6", "Clean Architecture");
    // Regla: No se puede crear con ejemplares totales <= 0 
    assertThrows(IllegalArgumentException.class, () -> {
        bookService.addBook(book, 0);
    });
}
}
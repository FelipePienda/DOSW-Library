package edu.eci.tdd.service;

import edu.eci.tdd.model.Book;
import edu.eci.tdd.exception.BookNotAvailableException;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookService {
    private final Map<String, Integer> inventory = new HashMap<>();
    // NUEVO: Mapa para rastrear el estado de los préstamos (bookId + userId -> estado)
    // "Active" o "Returned"
    private final Map<String, String> loans = new HashMap<>();

    public void addBook(Book book, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        inventory.put(book.getId(), quantity);
    }

    public int getAvailableUnits(String bookId) {
        return inventory.getOrDefault(bookId, 0);
    }

    public void updateStock(String bookId, int delta) {
        int currentStock = getAvailableUnits(bookId);
        inventory.put(bookId, currentStock + delta);
    }

    public void loanBook(String bookId, String userId) throws BookNotAvailableException {
        if (!inventory.containsKey(bookId)) {
             throw new BookNotAvailableException("Book does not exist");
        }
        int available = getAvailableUnits(bookId);
        if (available <= 0) {
            throw new BookNotAvailableException("Book not available");
        }
        
        updateStock(bookId, -1);
        // Registramos el préstamo como ACTIVO
        loans.put(bookId + userId, "ACTIVE");
    }

    public void returnBook(String bookId, String userId) {
        String loanKey = bookId + userId;
        
        // REGLA: Validar si el préstamo existe y si ya fue devuelto
        if (!loans.containsKey(loanKey)) {
            throw new IllegalStateException("No active loan found for this user and book");
        }
        
        if ("RETURNED".equals(loans.get(loanKey))) {
            // Esto es lo que el test estaba esperando que hiciéramos
            throw new IllegalStateException("Loan already returned");
        }
        
        updateStock(bookId, 1);
        // Marcamos como DEVUELTO para que la segunda vez falle
        loans.put(loanKey, "RETURNED");
    }
}
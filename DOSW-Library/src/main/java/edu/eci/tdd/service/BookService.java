package edu.eci.tdd.service;

import edu.eci.dosw.tdd.model.Book;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookService {
    // Mapa de Libros y la cantidad de ejemplares
    private final Map<String, Integer> inventory = new HashMap<>();

    public void addBook(Book book, int quantity) {
        inventory.put(book.getId(), quantity);
    }

    public int getAvailableUnits(String bookId) {
        return inventory.getOrDefault(bookId, 0);
    }

    public void updateStock(String bookId, int delta) {
        int currentStock = getAvailableUnits(bookId);
        inventory.put(bookId, currentStock + delta);
    }
}
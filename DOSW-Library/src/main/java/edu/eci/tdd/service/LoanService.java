package edu.eci.tdd.service;

import edu.eci.dosw.tdd.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.model.Loan;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {
    private final List<Loan> loans = new ArrayList<>(); // Listado de préstamos
    private final BookService bookService;

    public LoanService(BookService bookService) {
        this.bookService = bookService;
    }

    public Loan createLoan(String userId, String bookId) throws BookNotAvailableException {
        // Validar si hay stock disponible [cite: 21]
        if (bookService.getAvailableUnits(bookId) <= 0) {
            throw new BookNotAvailableException("No hay ejemplares disponibles para el libro: " + bookId);
        }

        // Reducir stock y registrar préstamo
        bookService.updateStock(bookId, -1);
        Loan newLoan = new Loan(userId, bookId);
        loans.add(newLoan);

        return newLoan;
    }

    public List<Loan> getAllLoans() {
        return loans;
    }
}

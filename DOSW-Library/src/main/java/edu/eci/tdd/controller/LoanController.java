package edu.eci.tdd.controller;

import edu.eci.tdd.exception.BookNotAvailableException;
import edu.eci.tdd.model.Loan;
import edu.eci.tdd.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestParam String userId, @RequestParam String bookId) {
        try {
            Loan loan = loanService.createLoan(userId, bookId);
            return ResponseEntity.ok(loan);
        } catch (BookNotAvailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
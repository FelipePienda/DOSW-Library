package edu.eci.tdd.model;

import java.time.LocalDate;

public class Loan {
    private String userId;
    private String bookId;
    private LocalDate loanDate;

    public Loan(String userId, String bookId) {
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = LocalDate.now();
    }

    public String getUserId() { return userId; }
    public String getBookId() { return bookId; }
    public LocalDate getLoanDate() { return loanDate; }
}

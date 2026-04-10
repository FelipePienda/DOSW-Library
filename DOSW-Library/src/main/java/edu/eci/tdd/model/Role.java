package edu.eci.tdd.model;

public enum Role {
    USER,
    LIBRARIAN;

    @Override
    public String toString() {
        return name();
    }
}
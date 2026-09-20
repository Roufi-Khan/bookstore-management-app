package model;

// Represents a single book in the bookstore
public class Book {

    // Book attributes
    private String name;
    private double price;

    // Constructor to initialize a book
    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getter for book name
    public String getName() {
        return name;
    }

    // Getter for book price
    public double getPrice() {
        return price;
    }
}
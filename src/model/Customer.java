package model;

// Represents a customer in the bookstore system
public class Customer {

    private String username;
    private String password;
    private int points;

    // Constructor: new customers start with 0 points
    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.points = 0;
    }

    // Getter methods
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getPoints() { return points; }

    // Add points after purchase
    // Rule: 1 CAD = 10 points
    public void addPoints(int p) {
        points += p;
    }

    // Deduct points when redeeming
    public void redeemPoints(int p) {
        points -= p;
    }

    // Determine customer status based on points
    // >= 1000 → Gold, otherwise → Silver
    public String getStatus() {
        return (points >= 1000) ? "Gold" : "Silver";
    }
}
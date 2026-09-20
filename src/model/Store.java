package model;

import java.util.ArrayList;
import java.io.*;

// Acts as a central data store for the application
// Holds all books and customers and handles file persistence
public class Store {

    // Lists storing current application data
    public static ArrayList<Book> books = new ArrayList<>();
    public static ArrayList<Customer> customers = new ArrayList<>();

    // ================= SAVE BOOKS =================
    // Writes all books to books.txt file
    public static void saveBooks() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"));

            // Write each book as: name,price
            for (Book b : books) {
                writer.write(b.getName() + "," + b.getPrice());
                writer.newLine();
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= LOAD BOOKS =================
    // Reads books from books.txt into memory
    public static void loadBooks() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("books.txt"));
            String line;

            // Read each line and reconstruct Book objects
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                books.add(new Book(parts[0], Double.parseDouble(parts[1])));
            }

            reader.close();
        } catch (Exception e) {
            // Ignore if file does not exist (first run case)
        }
    }

    // ================= SAVE CUSTOMERS =================
    // Writes all customers to customers.txt
    public static void saveCustomers() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt"));

            // Format: username,password,points
            for (Customer c : customers) {
                writer.write(c.getUsername() + "," +
                             c.getPassword() + "," +
                             c.getPoints());
                writer.newLine();
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= LOAD CUSTOMERS =================
    // Reads customers from file and restores objects
    public static void loadCustomers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Create customer and restore points
                Customer c = new Customer(parts[0], parts[1]);
                c.addPoints(Integer.parseInt(parts[2]));

                customers.add(c);
            }

            reader.close();
        } catch (Exception e) {
            // Ignore if file doesn't exist
        }
    }
}
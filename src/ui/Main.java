package ui;

import model.Store;

public class Main {
    public static void main(String[] args) {

        // Load saved data from files when application starts
        // This ensures persistence between sessions
        Store.loadBooks();
        Store.loadCustomers();

        // Create main application window (context of the program)
        MainFrame frame = new MainFrame();

        // Make the GUI visible
        frame.setVisible(true);
    }
}
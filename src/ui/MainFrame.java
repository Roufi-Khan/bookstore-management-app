package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;

public class MainFrame extends JFrame {

    // This panel is reused for all screens
    // Instead of creating multiple windows, we swap this panel (State Pattern idea)
    private JPanel panel;

    public MainFrame() {
        setTitle("Bookstore App");
        setSize(420, 350);

        // Centers the window on screen
        setLocationRelativeTo(null);

        // Disable default close so we can save data first
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Custom behavior when user clicks "X"
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {

                // Save all current data before exiting
                // Ensures persistence between runs
                Store.saveBooks();
                Store.saveCustomers();

                System.exit(0);
            }
        });

        // Start app in login state
        showLogin();
    }

    // ================= LOGIN SCREEN =================
    public void showLogin() {

        // Create a new panel (new "state")
        panel = new JPanel();
        panel.setLayout(null); // absolute positioning

        // Username label + field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 25);

        JTextField userField = new JTextField();
        userField.setBounds(150, 50, 150, 25);

        // Password label + field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 25);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 100, 150, 25);

        // Login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(150, 150, 100, 25);

        // When login is clicked
        loginBtn.addActionListener(e -> {

            String user = userField.getText();
            String pass = new String(passField.getPassword());

            // Owner login check
            if (user.equals("admin") && pass.equals("admin")) {
                showOwnerMenu(); // transition to owner state
                return;
            }

            // Customer login check
            for (Customer c : Store.customers) {
                if (c.getUsername().equals(user) &&
                    c.getPassword().equals(pass)) {

                    // Transition to customer screen with that user
                    showCustomerScreen(c);
                    return;
                }
            }

            // If login fails
            JOptionPane.showMessageDialog(this, "Invalid login");
        });

        // Add components to panel
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginBtn);

        // Replace current screen with login screen
        setContentPane(panel);
        revalidate();
    }

    // ================= OWNER MENU =================
    public void showOwnerMenu() {

        panel = new JPanel();
        panel.setLayout(null);

        // Buttons represent actions owner can perform
        JButton booksBtn = new JButton("Books");
        booksBtn.setBounds(120, 50, 150, 30);

        JButton customersBtn = new JButton("Customers");
        customersBtn.setBounds(120, 100, 150, 30);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(120, 150, 150, 30);

        // Each button changes screen (state transition)
        booksBtn.addActionListener(e -> showBooksScreen());
        customersBtn.addActionListener(e -> showCustomersScreen());
        logoutBtn.addActionListener(e -> showLogin());

        panel.add(booksBtn);
        panel.add(customersBtn);
        panel.add(logoutBtn);

        setContentPane(panel);
        revalidate();
    }

    // ================= BOOK MANAGEMENT =================
    public void showBooksScreen() {

        panel = new JPanel();
        panel.setLayout(null);

        // Table structure
        String[] columns = {"Book Name", "Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 350, 120);

        // Load books into table
        for (Book b : Store.books) {
            model.addRow(new Object[]{b.getName(), b.getPrice()});
        }

        // Labels + input fields
        JLabel nameLabel = new JLabel("Book:");
        nameLabel.setBounds(50, 140, 120, 20);

        JTextField nameField = new JTextField();
        nameField.setBounds(50, 160, 120, 25);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(200, 140, 120, 20);

        JTextField priceField = new JTextField();
        priceField.setBounds(200, 160, 120, 25);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(140, 200, 100, 25);

        // Add book logic
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());

                // Add to backend list
                Store.books.add(new Book(name, price));

                // Add to table UI
                model.addRow(new Object[]{name, price});

                // Clear inputs
                nameField.setText("");
                priceField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        });

        // Delete selected book
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(50, 240, 100, 25);

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {

                // Remove from UI
                model.removeRow(row);

                // Remove from data
                Store.books.remove(row);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(200, 240, 100, 25);
        backBtn.addActionListener(e -> showOwnerMenu());

        panel.add(scrollPane);
        panel.add(nameLabel);
        panel.add(priceLabel);
        panel.add(nameField);
        panel.add(priceField);
        panel.add(addBtn);
        panel.add(deleteBtn);
        panel.add(backBtn);

        setContentPane(panel);
        revalidate();
    }

    // ================= CUSTOMER MANAGEMENT =================
    public void showCustomersScreen() {

        panel = new JPanel();
        panel.setLayout(null);

        String[] columns = {"Username", "Password", "Points"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 20, 350, 120);

        // Load customers
        for (Customer c : Store.customers) {
            model.addRow(new Object[]{
                c.getUsername(),
                c.getPassword(),
                c.getPoints()
            });
        }

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 140, 120, 20);

        JTextField userField = new JTextField();
        userField.setBounds(50, 160, 120, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(200, 140, 120, 20);

        JTextField passField = new JTextField();
        passField.setBounds(200, 160, 120, 25);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(140, 200, 100, 25);

        // Add customer
        addBtn.addActionListener(e -> {
            Customer c = new Customer(userField.getText(), passField.getText());

            Store.customers.add(c);

            model.addRow(new Object[]{
                c.getUsername(),
                c.getPassword(),
                c.getPoints()
            });

            userField.setText("");
            passField.setText("");
        });

        // Delete customer
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(50, 240, 100, 25);

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                model.removeRow(row);
                Store.customers.remove(row);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(200, 240, 100, 25);
        backBtn.addActionListener(e -> showOwnerMenu());

        panel.add(scroll);
        panel.add(userLabel);
        panel.add(passLabel);
        panel.add(userField);
        panel.add(passField);
        panel.add(addBtn);
        panel.add(deleteBtn);
        panel.add(backBtn);

        setContentPane(panel);
        revalidate();
    }

    // ================= CUSTOMER BUY SCREEN =================
    public void showCustomerScreen(Customer customer) {

        panel = new JPanel();
        panel.setLayout(null);

        // Display customer info
        JLabel welcome = new JLabel(
            "Welcome " + customer.getUsername() +
            " | Points: " + customer.getPoints() +
            " | Status: " + customer.getStatus()
        );
        welcome.setBounds(20, 10, 350, 25);

        // Table with checkbox selection
        String[] cols = {"Book Name", "Price", "Select"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public Class<?> getColumnClass(int col) {
                return col == 2 ? Boolean.class : String.class;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 40, 350, 120);

        // Load books
        for (Book b : Store.books) {
            model.addRow(new Object[]{b.getName(), b.getPrice(), false});
        }

        JButton buyBtn = new JButton("Buy");
        JButton redeemBtn = new JButton("Redeem + Buy");
        JButton logoutBtn = new JButton("Logout");

        buyBtn.setBounds(20, 180, 100, 25);
        redeemBtn.setBounds(140, 180, 150, 25);
        logoutBtn.setBounds(300, 180, 80, 25);

        logoutBtn.addActionListener(e -> showLogin());

        // BUY (no discount)
        buyBtn.addActionListener(e -> {
            double total = 0;

            // Loop backwards so removal doesn't break indexing
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                if (Boolean.TRUE.equals(model.getValueAt(i, 2))) {

                    double price = Double.parseDouble(model.getValueAt(i, 1).toString());
                    total += price;

                    // Remove purchased book from inventory
                    Store.books.remove(i);
                    model.removeRow(i);
                }
            }

            // Add earned points
            customer.addPoints((int)(total * 10));

            showCostScreen(customer, total);
        });

        // REDEEM + BUY
        redeemBtn.addActionListener(e -> {
            double total = 0;

            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                if (Boolean.TRUE.equals(model.getValueAt(i, 2))) {

                    double price = Double.parseDouble(model.getValueAt(i, 1).toString());
                    total += price;

                    Store.books.remove(i);
                    model.removeRow(i);
                }
            }

            // Convert points → discount
            int points = customer.getPoints();
            double discount = points / 100.0;

            double finalCost = Math.max(0, total - discount);

            // Update points
            int usedPoints = (int)Math.min(points, total * 100);
            customer.redeemPoints(usedPoints);
            customer.addPoints((int)(finalCost * 10));

            showCostScreen(customer, finalCost);
        });

        panel.add(welcome);
        panel.add(scroll);
        panel.add(buyBtn);
        panel.add(redeemBtn);
        panel.add(logoutBtn);

        setContentPane(panel);
        revalidate();
    }

    // ================= COST SCREEN =================
    public void showCostScreen(Customer customer, double totalCost) {

        panel = new JPanel();
        panel.setLayout(null);

        // Display final cost
        JLabel totalLabel = new JLabel("Total Cost: " + totalCost);
        totalLabel.setBounds(100, 50, 200, 25);

        // Display updated points + membership status
        JLabel pointsLabel = new JLabel(
            "Points: " + customer.getPoints() +
            " | Status: " + customer.getStatus()
        );
        pointsLabel.setBounds(80, 100, 250, 25);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(140, 150, 100, 25);

        logoutBtn.addActionListener(e -> showLogin());

        panel.add(totalLabel);
        panel.add(pointsLabel);
        panel.add(logoutBtn);

        setContentPane(panel);
        revalidate();
    }
}
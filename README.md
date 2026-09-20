# Bookstore Management App

A Java Swing desktop application developed by a three-person team for COE528 at Toronto Metropolitan University.

The application provides owner and customer interfaces for managing a bookstore, purchasing books, and earning or redeeming loyalty points.

## Features

### Owner
- Log in to the owner interface.
- Add and delete books.
- Create and delete customer accounts.

### Customer
- Log in with an account created by the owner.
- Select and purchase available books.
- Earn 10 loyalty points per dollar charged.
- Redeem 100 points for a $1 discount.
- View the final cost, updated points, and Silver/Gold status.

Membership status is based on the current points balance:
- Silver: fewer than 1,000 points
- Gold: 1,000 points or more

Purchases are simulated; the application does not process payments.

## Technology

- Java
- Java Swing
- ArrayList collections
- Local text-file persistence
- NetBeans / Apache Ant project configuration

## Code Structure

| File | Responsibility |
| --- | --- |
| `src/model/Book.java` | Book name and price |
| `src/model/Customer.java` | Customer credentials, points, and status |
| `src/model/Store.java` | In-memory collections and file persistence |
| `src/ui/Main.java` | Application entry point |
| `src/ui/MainFrame.java` | Screens, event handlers, and purchase workflow |

The model and UI are organized into separate packages. Screen changes are handled by replacing panels within the main window.

Membership status uses a points-based condition rather than separate State-pattern classes.

## Running the Application

The included NetBeans configuration targets Java 25.

### NetBeans

1. Install JDK 25 and use a compatible NetBeans installation.
2. Open the project folder.
3. Select JDK 25 as the project's Java platform.
4. Build and run `ui.Main`.

### Terminal

With JDK 25 installed, run these commands from the project root:

```bash
mkdir -p out
javac -d out src/model/*.java src/ui/*.java
java -cp out ui.Main

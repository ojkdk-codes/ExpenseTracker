/**
 * A Simple Expense Tracker Program
 *
 *  @author Manoj Khadka
 *  Last Modified: 2024-08-17
 */

import java.io.*;
import java.util.*;

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";
    private static Map<String, Double> expenses = new HashMap<>();

    /**
     * Add expenses. Takes category as type String and amount as type Double
     * @param scanner To read the user input
     */
    private static void addExpense(Scanner scanner) {
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        expenses.put(category, expenses.getOrDefault(category, 0.0) + amount);
        System.out.println("Expense added successfully!");
    }

    /**
     * Print each expenses
     */
    private static void viewExpenses() {
        System.out.println("\nExpenses:");
        for (Map.Entry<String, Double> entry : expenses.entrySet()) {
            System.out.println(entry.getKey() + ": $" + entry.getValue());
        }
    }

    /**
     * Read the expenses from the hash map
     * Write out to a file "expenses.txt"
     */
    private static void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Double> entry : expenses.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    /**
     * Read the expenses from the file "expenses.txt" (if exist)
     * Add those expenses back to hashmap
     */
    private static void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String category = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    expenses.put(category, amount);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous expense record found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        loadExpenses(); //load values(if exist) first
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nExpense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    saveExpenses();
                    System.out.println("Expenses saved. Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }
}

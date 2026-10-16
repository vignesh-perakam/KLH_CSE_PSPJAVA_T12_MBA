import java.util.InputMismatchException;
import java.util.Scanner;

// ============================================================================
// MODULE 6: EXCEPTION HANDLING (User-Defined Custom Exception)
// Handles domain-specific invalid expense inputs (e.g., non-positive amounts)
// ============================================================================
class InvalidExpenseException extends Exception {
    public InvalidExpenseException(String message) {
        super(message);
    }
}

// ============================================================================
// MODULE 4: INHERITANCE
// Base class for an expense item
// ============================================================================
class Expense {
    protected String title;
    protected double amount;

    // MODULE 6: Validates parameters and throws custom exception on invalid data
    public Expense(String title, double amount) throws InvalidExpenseException {
        if (amount <= 0) {
            throw new InvalidExpenseException("Expense amount must be greater than zero! Provided: RS" + amount);
        }
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidExpenseException("Expense title cannot be empty!");
        }
        this.title = title;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    // Base method to be overridden
    public String getDetails() {
        return String.format("%-15s : ₹%.2f", title, amount);
    }
}

// ============================================================================
// MODULE 4 (INHERITANCE) & MODULE 5 (CONSTRUCTOR OVERLOADING + METHOD OVERRIDING)
// Subclass extending Expense using inheritance
// ============================================================================
class RecurringExpense extends Expense {
    private String billingCycle;

    // MODULE 5: Constructor Overloading (Constructor 1 - Default cycle)
    // Propagates InvalidExpenseException from super constructor (MODULE 6)
    public RecurringExpense(String title, double amount) throws InvalidExpenseException {
        super(title, amount);
        this.billingCycle = "Monthly";
    }

    // MODULE 5: Constructor Overloading (Constructor 2 - Custom cycle)
    public RecurringExpense(String title, double amount, String billingCycle) throws InvalidExpenseException {
        super(title, amount);
        this.billingCycle = billingCycle;
    }

    // MODULE 5: Method Overriding (Overriding getDetails() from base class)
    @Override
    public String getDetails() {
        return String.format("%-15s : ₹%.2f [Recurring - %s]", 
                title, amount, billingCycle);
    }
}

// ============================================================================
// MAIN APPLICATION
// ============================================================================
public class Budget {

    // MODULE 3: 1D Array for homogeneous record storage
    private static final int MAX_EXPENSES = 50;
    private Expense[] expenseList = new Expense[MAX_EXPENSES];
    private int count = 0;

    // ========================================================================
    // MODULE 6: EXCEPTION HANDLING (Defensive try-catch block for array bounds)
    // ========================================================================
    public void addExpense(Expense exp) {
        try {
            if (count >= MAX_EXPENSES) {
                throw new ArrayIndexOutOfBoundsException("Storage capacity exceeded (Max: " + MAX_EXPENSES + ").");
            }
            expenseList[count] = exp;
            count++;
            System.out.println("[SUCCESS] Expense recorded successfully.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("[ERROR - MODULE 6] " + e.getMessage());
        }
    }

    // Calculation logic
    public double computeTotalExpenses() {
        double total = 0.0;
        // MODULE 3: Array traversal
        for (int i = 0; i < count; i++) {
            total += expenseList[i].getAmount();
        }
        return total;
    }

    public double computeBalance(double income, double expenses) {
        return income - expenses;
    }

    public double computeSavingsPercentage(double income, double balance) {
        // MODULE 2: Decision making / conditional statement
        if (income <= 0) {
            return 0.0;
        }
        return (balance / income) * 100.0;
    }

    public void displayAllExpenses() {
        // MODULE 2: Conditional control check
        if (count == 0) {
            System.out.println("[INFO] No transactions recorded.");
            return;
        }
        System.out.println("\n--- Recorded Transactions ---");
        for (int i = 0; i < count; i++) {
            System.out.println(expenseList[i].getDetails());
        }
    }

    // Displays formatted budget report
    public void printBudgetReport(double monthlyIncome) {
        double totalExpenses = computeTotalExpenses();
        double balance = computeBalance(monthlyIncome, totalExpenses);
        double savingsRate = computeSavingsPercentage(monthlyIncome, balance);

        System.out.println("\n---------------- BUDGET SUMMARY ----------------");
        System.out.printf("Total Monthly Income : RS%.2f\n", monthlyIncome);
        System.out.printf("Total Expenses       : RS%.2f\n", totalExpenses);
        System.out.printf("Net Savings          : RS%.2f (%.1f%% of income)\n", balance, savingsRate);
        System.out.println("------------------------------------------------");
    }

    // ========================================================================
    // MAIN METHOD (MODULE 1: SCANNER & DATA TYPES)
    // ========================================================================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double monthlySalary = 0.0;

        // MODULE 6: Input exception handling for initial salary prompt
        while (true) {
            try {
                System.out.print("Enter your monthly income (RS): ");
                monthlySalary = scanner.nextDouble();
                scanner.nextLine();
                if (monthlySalary < 0) {
                    throw new InvalidExpenseException("Income cannot be negative.");
                }
                break;
            } catch (InputMismatchException e) {
                System.err.println(" Invalid numeric format. Please re-enter a valid income.");
                scanner.nextLine(); // Clear buffer
            } catch (InvalidExpenseException e) {
                System.err.println(" " + e.getMessage());
            }
        }

        int choice = 0;
        Budget analyzer = new Budget();

        // ====================================================================
        // MODULE 2: CONTROL STATEMENTS (Looping & Decision Branching)
        // ====================================================================
        while (choice != 6) {
            System.out.println("\n=== Monthly Budget Analyzer ===");
            System.out.println("1. Add One-Time Expense");
            System.out.println("2. Add Recurring Expense");
            System.out.println("3. Display All Expenses");
            System.out.println("4. Generate Budget Summary");
            System.out.println("5. Update Monthly Income");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            // ================================================================
            // MODULE 6: Wrapping Menu Dispatch in try-catch to prevent crashes
            // ================================================================
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 

                // MODULE 2: Multi-way branching via switch-case
                switch (choice) {
                    case 1:
                        System.out.print("Enter expense title: ");
                        String title1 = scanner.nextLine();
                        System.out.print("Enter amount: ");
                        double amount1 = scanner.nextDouble();
                        scanner.nextLine(); 

                        // Throws and catches InvalidExpenseException (MODULE 6)
                        analyzer.addExpense(new Expense(title1, amount1));
                        break;

                    case 2:
                        System.out.print("Enter recurring expense title: ");
                        String title2 = scanner.nextLine();
                        System.out.print("Enter amount: ");
                        double amount2 = scanner.nextDouble();
                        scanner.nextLine(); 

                        System.out.print("Do you want to specify a billing cycle? (y/n): ");
                        String specify = scanner.nextLine().trim();

                        // MODULE 2: If-else branching
                        if (specify.equalsIgnoreCase("y")) {
                            System.out.print("Enter billing cycle (e.g., Weekly, Quarterly): ");
                            String cycle = scanner.nextLine();
                            // MODULE 5: Overloaded constructor with 3 parameters
                            analyzer.addExpense(new RecurringExpense(title2, amount2, cycle));
                        } else {
                            // MODULE 5: Overloaded constructor with 2 parameters
                            analyzer.addExpense(new RecurringExpense(title2, amount2));
                        }
                        break;

                    case 3:
                        // MODULE 5: Polymorphic getDetails() call
                        analyzer.displayAllExpenses();
                        break;

                    case 4:
                        analyzer.printBudgetReport(monthlySalary);
                        break;

                    case 5:
                        System.out.print("Enter new monthly income (RS): ");
                        double newSalary = scanner.nextDouble();
                        scanner.nextLine();
                        if (newSalary < 0) {
                            throw new InvalidExpenseException("Income cannot be negative.");
                        }
                        monthlySalary = newSalary;
                        System.out.println("[SUCCESS] Monthly income updated to RS" + monthlySalary);
                        break;

                    case 6:
                        System.out.println("Exiting application. Goodbye!");
                        break;

                    default:
                        System.out.println("[WARN] Invalid option. Enter a number between 1 and 6.");
                }
            } catch (InputMismatchException e) {
                // MODULE 6: Catches non-integer menu selections or bad double reads
                System.err.println(" Input format mismatch. Please enter valid numeric characters.");
                scanner.nextLine(); // Clear scanner buffer
            } catch (InvalidExpenseException e) {
                // MODULE 6: Catches logical business rule violations
                System.err.println(" " + e.getMessage());
            } catch (Exception e) {
                // MODULE 6: Generic fallback exception guard
                System.err.println(" Unexpected error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }
}

import java.util.Scanner;

// ============================================================================
// MODULE 4:  INHERITANCE
// Base class for an expense item
// ============================================================================
class Expense {
    protected String title;
    protected double amount;

    public Expense(String title, double amount) {
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
    public RecurringExpense(String title, double amount) {
        super(title, amount);
        this.billingCycle = "Monthly";
    }

    // MODULE 5: Constructor Overloading (Constructor 2 - Custom cycle)
    public RecurringExpense(String title, double amount, String billingCycle) {
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

    // Adds an expense to the array
    public void addExpense(Expense exp) {
        if (count < MAX_EXPENSES) {
            expenseList[count] = exp;
            count++;
            System.out.println("[SUCCESS] Expense recorded successfully.");
        } else {
            System.out.println("[ERROR] Array limit reached! Cannot store more expenses.");
        }
    }

    // ========================================================================
    // MODULE 2: USER-DEFINED METHODS
    // Dedicated modular methods for arithmetic operations
    // ========================================================================
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
        if (income <= 0) {
            return 0.0;
        }
        return (balance / income) * 100.0;
    }

    
    public void displayAllExpenses() {
        if (count == 0) {
            System.out.println("[INFO] No transactions recorded.");
            return;
        }
        System.out.println("\n--- Recorded Transactions ---");
        for (int i = 0; i < count; i++) {
            // Polymorphic call: executes base or overridden getDetails()
            System.out.println(expenseList[i].getDetails());
        }
    }

    // Displays summary using Module 2 methods
    public void printBudgetReport(double monthlyIncome) {
        double totalExpenses = computeTotalExpenses();
        double balance = computeBalance(monthlyIncome, totalExpenses);
        double savingsRate = computeSavingsPercentage(monthlyIncome, balance);

        System.out.println("\n---------------- BUDGET SUMMARY ----------------");
        System.out.printf("Total Monthly Income : ₹%.2f\n", monthlyIncome);
        System.out.printf("Total Expenses       : ₹%.2f\n", totalExpenses);
        System.out.printf("Net Savings          : ₹%.2f (%.1f%% of income)\n", balance, savingsRate);
        System.out.println("------------------------------------------------");
    }

    // ========================================================================
    // MAIN METHOD (MODULE 1: SCANNER & DATA TYPES)
    // ========================================================================
    public static void main(String[] args) {
        // MODULE 1: Scanner for console input
        Scanner scanner = new Scanner(System.in);

        
        System.out.print("Enter your monthly income (₹): ");
        double monthlySalary = scanner.nextDouble();
        scanner.nextLine(); 

        
        int choice = 0;

        Budget analyzer = new Budget();

        while (choice != 6) {
            System.out.println("\n=== Monthly Budget Analyzer ===");
            System.out.println("1. Add One-Time Expense");
            System.out.println("2. Add Recurring Expense ");
            System.out.println("3. Display All Expenses ");
            System.out.println("4. Generate Budget Summary ");
            System.out.println("5. Update Monthly Income");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter expense title: ");
                    String title1 = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount1 = scanner.nextDouble();
                    scanner.nextLine(); 

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

                    if (specify.equalsIgnoreCase("y")) {
                        System.out.print("Enter billing cycle (e.g., Weekly, Quarterly): ");
                        String cycle = scanner.nextLine();
                        // MODULE 5: Overloaded constructor with 3 parameters
                        analyzer.addExpense(new RecurringExpense(title2, amount2, cycle));
                    } else {
                        // MODULE 5: Overloaded constructor with 2 parameters (defaults to 'Monthly')
                        analyzer.addExpense(new RecurringExpense(title2, amount2));
                    }
                    break;

                case 3:
                    // MODULE 5: Displays output through overridden getDetails()
                    analyzer.displayAllExpenses();
                    break;

                case 4:
                    // MODULE 2: Calls modular calculation methods
                    analyzer.printBudgetReport(monthlySalary);
                    break;

                case 5:
                    // MODULE 1: Re-prompting and updating primitive double
                    System.out.print("Enter new monthly income (₹): ");
                    monthlySalary = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.println("[SUCCESS] Monthly income updated to ₹" + monthlySalary);
                    break;

                case 6:
                    System.out.println("Exiting application. Goodbye!");
                    break;

                default:
                    System.out.println("[WARN] Invalid option. Enter a number between 1 and 6.");
            }
        }

        scanner.close();
    }
}

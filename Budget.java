
import java.util.Scanner;

// [CONCEPTS: Class, Constructor]
class ExpenseManager {
    // [CONCEPTS: Arrays, String, Data Types (double, int)]
    private double[] expenses;
    private String[] categories;
    private double budget;
    private int expenseCount;

    // [CONCEPT: Constructor]
    
    public ExpenseManager(int maxExpenses, double budget) {
        this.expenses = new double[maxExpenses];
        this.categories = new String[maxExpenses];
        this.budget = budget;
        this.expenseCount = 0;
    }

    // [CONCEPT: Method Overloading] 
    
    public void addExpense(double amount) {
        // Defaults to "General" if no category is provided
        addExpense(amount, "General"); 
    }

    // [CONCEPT: Method Overloading] 

    public void addExpense(double amount, String category) {
        if (expenseCount < expenses.length) {
            expenses[expenseCount] = amount;
            categories[expenseCount] = category;
            expenseCount++;
            System.out.println("Added: RS" + amount + " for " + category);
        } else {
            System.out.println("Expense list is full!");
        }
    }

    // [CONCEPT: Methods]
    
    public double calculateTotalExpenses() {
        double total = 0;
        for (int i = 0; i < expenseCount; i++) {
            total += expenses[i];
        }
        return total;
    }

    // [CONCEPT: Methods]
    
    public void printReport() {
        double total = calculateTotalExpenses();
        double remaining = budget - total;

        System.out.println("\n=== Monthly Expense Report ===");
        System.out.println("Total Budget: RS" + budget);
        System.out.println("------------------------------");
        
        for (int i = 0; i < expenseCount; i++) {
            System.out.println(categories[i] + ": RS" + expenses[i]);
        }
        
        System.out.println("------------------------------");
        System.out.println("Total Spent: RS" + total);
        
        if (total > budget) {
            System.out.println("Warning: You are over budget by RS" + Math.abs(remaining));
        } else {
            System.out.println("Great job! You stayed under budget. Remaining: RS" + remaining);
        }
    }
}

public class Budget {
    public static void main(String[] args) {
        // [CONCEPT: Scanner] 
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Monthly Budget Analyzer!");
        System.out.print("Enter your total budget for the month: RS");
        
        // [CONCEPT: Data Types] 
        double monthlyBudget = sc.nextDouble();
        
        System.out.print("How many expenses do you want to record? ");
        int maxItems = sc.nextInt();
        sc.nextLine(); 

        
        ExpenseManager manager = new ExpenseManager(maxItems, monthlyBudget);

        for (int i = 0; i < maxItems; i++) {
            System.out.print("\nEnter expense amount (or 0 to stop): RS");
            double amount = sc.nextDouble();
            
            if (amount == 0) {
                break; 
            }
            
            sc .nextLine(); 
            
            System.out.print("Enter category (press Enter to leave blank): ");
            // [CONCEPT: String] 
            String category = sc.nextLine();

            
            if (category.trim().isEmpty()) {
                manager.addExpense(amount); 
            } else {
                manager.addExpense(amount, category); 
            }
        }

        
        manager.printReport();

    
        sc.close();
    }
}

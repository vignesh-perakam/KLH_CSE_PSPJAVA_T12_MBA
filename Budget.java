import java.util.Scanner;

public class Budget 
{

    public static void main(String[] args) 
   
    {    
         String userName;double monthlyIncome;    
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
         userName = sc.nextLine();     

        System.out.print("Enter your total monthly income -RS-> ");
         monthlyIncome = sc.nextDouble();

        String[] categories = {"Housing", "Food", "Transport", "Entertainment", "Utilities"};
        double[] expenses = new double[categories.length];  

        System.out.println("\n--- Enter your monthly expenses ---");
        for (int i = 0; i < categories.length; i++)      
        {
            System.out.print(categories[i] + "-RS->");
            expenses[i] = sc.nextDouble();
        }

        double totalExpenses = calculateTotal(expenses);
        double netSavings = monthlyIncome - totalExpenses;

        printReport(userName, monthlyIncome, totalExpenses, netSavings);
        analyzeBudget(netSavings, monthlyIncome);

        sc.close();
    }

    public static double calculateTotal(double[] expenseArray) 
    {
        double total = 0;
        for (double expense : expenseArray) 
        {
            total += expense;
        }
        return total;
    }

    public static void printReport(String name, double income, double totalExpenses, double savings) 
    {
        System.out.println("\n==================================");
        System.out.println("   BUDGET REPORT FOR: " + name.toUpperCase());
        System.out.println("==================================");
        System.out.printf("Total Monthly Income : $%.2f%n", income);
        System.out.printf("Total Expenses       : $%.2f%n", totalExpenses);
        System.out.printf("Net Monthly Savings  : $%.2f%n", savings);
        System.out.println("----------------------------------");
    }

    public static void analyzeBudget(double savings, double income) 
    {
        System.out.println("Financial Analysis:");
        
        double savingsRate = (savings / income) * 100;

        if (savings < 0)  
        {
            System.out.println(" WARNING: You are overspending! Your expenses exceed your income.");
            System.out.println("Action Plan: Look closely at your non-essential expenses and cut back immediately.");
        } else if (savingsRate >= 20.0) 
        {
            System.out.println(" EXCELLENT: You saved " + String.format("%.1f", savingsRate) + "% of your income!");
            System.out.println("Action Plan: You are hitting the ideal 20% savings target. Consider investing these surplus funds.");
        } else 
        {
            System.out.println(" GOOD START: You have a positive savings balance, but it is below the recommended 20% mark.");
            System.out.println("Action Plan: Try optimizing minor categories like 'Entertainment' or 'Food' to boost your savings rate.");
        }
        System.out.println("==================================");
    }
}

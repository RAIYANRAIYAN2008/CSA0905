import java.util.Scanner;

class InsufficientBalanceException extends Exception {
    InsufficientBalanceException(String message) { super(message); }
}

public class ATM {
    static double balance = 5000.0;

    static void withdraw(int accountNumber, double amount)
            throws InsufficientBalanceException {
        if (accountNumber <= 0) throw new IllegalArgumentException("Invalid account number");
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > balance) throw new InsufficientBalanceException("Insufficient balance");
        balance -= amount;
        System.out.println("Withdrawal successful: Rs." + amount);
        System.out.println("Remaining balance: Rs." + balance);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter account number: ");
            int account = sc.nextInt();
            System.out.print("Enter withdrawal amount: ");
            double amount = sc.nextDouble();
            withdraw(account, amount);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Please enter numbers only.");
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("ATM transaction completed.");
        }
    }
}

import java.util.Scanner;

class Bank {
    String name;
    int accno;
    double p;

    Bank(String name, int accno, double p) {
        this.name = name;
        this.accno = accno;
        this.p = p;
    }

    void display() {
        System.out.println("Customer Details:");
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accno);
        System.out.println("Principal Amount: " + p);
    }
}

class Account extends Bank {
    double amt;

    Account(String name, int accno, double p) {
        super(name, accno, p);
        // this.amt = amt;
    }

    void deposit() {
        p = p + amt;
        System.out.println("Deposit Successful!");
    }

    void withdraw() {
        if (amt > p) {
            System.out.println("INSUFFICIENT BALANCE");
        } else {
            p = p - amt;
            if (p < 500) {
                // Penalty imposed if the principal amount after withdrawal is less than 500
                p = p - ((500 - p) / 10);
            }
            System.out.println("Withdrawal Successful!");
        }
    }

    void displayBalance() {
        System.out.println("Current Balance: " + p);
    }

    @Override
    void display() {
        super.display();
        System.out.println("Transaction Amount: " + amt);
        System.out.println("Updated Principal Amount: " + p);
    }
}

public class AccountMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user input for customer details
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter account number: ");
        int accno = scanner.nextInt();

        System.out.print("Enter initial principal amount: ");
        double principal = scanner.nextDouble();

        // Create an instance of the Account class with user input
        Account account = new Account(name, accno, principal);

        int choice;

        do {
            // Display menu
            System.out.println("\nMenu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Display Balance and Details");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Deposit
                    System.out.print("Enter deposit amount: ");
                    account.amt = scanner.nextDouble();
                    account.deposit();
                    break;

                case 2:
                    // Withdraw
                    System.out.print("Enter withdrawal amount: ");
                    account.amt = scanner.nextDouble();
                    account.withdraw();
                    break;

                case 3:
                    // Display Balance and Details
                    account.display();
                    account.displayBalance();
                    break;

                case 4:
                    // Exit
                    System.out.println("Exiting the program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }

        } while (choice != 4);

        // Close the scanner
        scanner.close();
    }
}

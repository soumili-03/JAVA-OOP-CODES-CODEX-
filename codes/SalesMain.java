class Product {
    String name;
    int code;
    double amount;

    Product(String n, int c, double p) {
        name = n;
        code = c;
        amount = p;
    }

    void show() {
        System.out.println("Product Details:");
        System.out.println("Name: " + name);
        System.out.println("Product Code: " + code);
        System.out.println("Amount: " + amount);
    }
}

class Sales extends Product {
    int day;
    double tax;
    double totamt;

    Sales(String n, int c, double p, int d) {
        super(n, c, p);
        day = d;
        tax = 0.0;
        totamt = 0.0;
    }

    void compute() {
        // Calculate service tax @ 12.4% of the actual sale amount
        tax = 0.124 * amount;

        // Calculate fine @ 2.5% of the actual sale amount
        double fine = 0.025 * amount;

        // Calculate total amount paid by the retailer
        if (day > 30) {
            totamt = amount + tax + fine;
        } else {
            totamt = amount + tax;
        }
    }

    @Override
    void show() {
        super.show();
        System.out.println("Days taken to pay: " + day);
        System.out.println("Service Tax: " + tax);
        System.out.println("Total Amount Paid: " + totamt);
    }
}

public class SalesMain {
    public static void main(String[] args) {
        // Example usage
        Sales sales = new Sales("ProductABC", 123, 5000.0, 35);
        sales.compute();
        sales.show();
    }
}

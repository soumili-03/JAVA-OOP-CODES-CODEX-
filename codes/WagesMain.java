class Worker {
    String name;
    double basic;

    Worker(String n, double b) {
        name = n;
        basic = b;
    }

    void display() {
        System.out.println("Worker Details:");
        System.out.println("Name: " + name);
        System.out.println("Basic Pay: " + basic);
    }
}

class Wages extends Worker {
    double hrs;
    double rate;
    double wage;

    Wages(String n, double b, double h, double r) {
        super(n, b);
        hrs = h;
        rate = r;
        wage = 0.0;
    }

    double overtime() {
        // Calculate and return the overtime amount
        return hrs * rate;
    }

    @Override
    void display() {
        super.display();
        // Calculate wage using the formula: wage = overtime amount + basic pay
        wage = overtime() + basic;
        System.out.println("Hours Worked: " + hrs);
        System.out.println("Rate per Hour: " + rate);
        System.out.println("Overtime Amount: " + overtime());
        System.out.println("Monthly Wage: " + wage);
    }
}

public class WagesMain {
    public static void main(String[] args) {
        // Example usage
        Wages wages = new Wages("John", 2000.0, 45.0, 15.0);
        wages.display();
    }
}

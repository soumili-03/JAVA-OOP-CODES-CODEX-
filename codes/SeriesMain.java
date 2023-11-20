class Number {
    int n;

    Number(int nn) {
        n = nn;
    }

    int factorial(int a) {
        if (a == 0 || a == 1) {
            return 1;
        } else {
            return a * factorial(a - 1);
        }
    }

    void display() {
        System.out.println("Number: " + n);
    }
}

class Series extends Number {
    int sum;

    Series(int nn) {
        super(nn);
        sum = 0;
    }

    void calsum() {
        for (int i = 1; i <= n; i++) {
            sum += factorial(i);
        }
    }

    @Override
    void display() {
        super.display();
        System.out.println("Sum of the series: " + sum);
    }
}

public class SeriesMain {
    public static void main(String[] args) {
        // Example usage
        Series series = new Series(5);
        series.calsum();
        series.display();
    }
}


class Stock {
    String item;
    int qt;
    double rate;
    double amt;

    Stock(String itemName, int quantity, double unitRate) {
        item = itemName;
        qt = quantity;
        rate = unitRate;
        amt = qt * rate;
    }

    void display() {
        System.out.println("Stock Details:");
        System.out.println("Item: " + item);
        System.out.println("Quantity: " + qt);
        System.out.println("Unit Rate: " + rate);
        System.out.println("Net Value: " + amt);
    }
}

class Purchase extends Stock {
    int pqt;
    double prate;

    Purchase(String itemName, int quantity, double unitRate, int purchasedQuantity, double purchasedRate) {
        super(itemName, quantity, unitRate);
        pqt = purchasedQuantity;
        prate = purchasedRate;
    }

    void update() {
        // Update stock by adding previous quantity and purchased quantity
        qt += pqt;

        // Replace the rate of the item if there is a difference in the purchase rate
        if (rate != prate) {
            rate = prate;
        }

        // Update current stock value
        amt = qt * rate;
    }

    @Override
    void display() {
        super.display();
        System.out.println("Purchased Quantity: " + pqt);
        System.out.println("Purchased Rate: " + prate);
        System.out.println("\nStock Details After Updation:");
        super.display();
    }
}
public class StockPurchaseMain {
    public static void main(String[] args) {
        // Create an instance of Stock
        Stock stockItem = new Stock("Widget", 100, 10.5);

        // Display initial stock details
        System.out.println("Initial Stock Details:");
        stockItem.display();

        // Create an instance of Purchase
        Purchase purchaseItem = new Purchase("Widget", 100, 10.5, 50, 12.0);

        // Display purchase details
        System.out.println("\nPurchase Details:");
        purchaseItem.display();

        // Update stock based on the purchase
        purchaseItem.update();

        // Display stock details after updation
        System.out.println("\nStock Details After Purchase Updation:");
        purchaseItem.display();
    }
}

package task;
import task.customer.*;

public interface SalesLogger {
    void logSale(Customer customer, Cart cart, double totalCost);
}

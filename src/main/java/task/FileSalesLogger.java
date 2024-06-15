package task;
import lombok.*;
import task.customer.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
public class FileSalesLogger implements SalesLogger{
    private final String fileName;

    @Override
    public void logSale(Customer customer, Cart cart, double totalCost){
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println("Customer: " + customer);
            writer.println("Cart: " + cart);
            writer.println("Total Cost: " + totalCost);
            writer.println("----------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

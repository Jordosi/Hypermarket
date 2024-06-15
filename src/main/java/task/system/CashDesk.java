package task.system;

import java.util.concurrent.BlockingQueue;

import lombok.*;
import task.customer.*;
import task.good.*;
import task.SalesLogger;

@Data
public class CashDesk implements Runnable{

    private final BlockingQueue<Customer> queue;
    private final Warehouse warehouse;
    private final SalesLogger salesLogger;

    @Override
    public void run(){
        while(true){
            try{
                Customer customer = queue.take();
                processCustomer(customer);
            }
            catch(InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void processCustomer(Customer customer){
        Cart cart = customer.getCart();
        double totalCost = 0;

        for (Product product : cart.getProducts()) {
            int quantity = product.getQuantity();

            if (warehouse.updateQuantity(product.getCategoryID(), product.getId(), -quantity)) {
                totalCost += product.getPrice() * quantity;
            } else {
                customer.getCart().removeProduct(product);
            }
        }

        salesLogger.logSale(customer, cart, totalCost);

        try {
            Thread.sleep(customer.getCheckoutTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

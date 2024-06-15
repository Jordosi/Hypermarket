package task.system;
import task.SalesLogger;
import task.customer.Customer;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class CashManager {
    private final BlockingQueue<Customer> queue;
    private final Warehouse whouse;
    private final SalesLogger salesLogger;
    private final ExecutorService executorService;
    private final int numCashiers;

    public CashManager(int numCashiers, Warehouse whouse, SalesLogger salesLogger) {
        this.numCashiers = numCashiers;
        this.queue = new LinkedBlockingQueue<>();
        this.whouse = whouse;
        this.salesLogger = salesLogger;
        this.executorService = Executors.newFixedThreadPool(numCashiers);
    }

    public void start() {
        // Запускаем указанное количество кассиров
        for (int i = 0; i < numCashiers; i++) {
            CashDesk cash = new CashDesk(queue, whouse, salesLogger);
            executorService.submit(cash);
        }
    }

    public void addCustomer(Customer customer) {
        try {
            queue.put(customer);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        executorService.shutdown();
    }
}

package main;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import java.util.Random;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import task.good.*;
import task.system.*;
import task.customer.*;
import task.*;

public class Main {

    public static void main(String[] args) throws IOException{
        int cashes = 4;
        String logFile = "salesLogFile.log";

        int numCashiers = cashes;
        Warehouse whouse = createWarehouse();
        FileSalesLogger salesLogger = new FileSalesLogger(logFile);
        CashManager cashManager = new CashManager(numCashiers, whouse, salesLogger);

        cashManager.start();

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Cart cart = createCart(whouse);
            Customer customer = new Customer(cart);
            cashManager.addCustomer(customer);

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        cashManager.stop();
    }

    private static Warehouse createWarehouse() {
        List<Category> categories = createCategories();
        Map<Integer, Map<Integer, Product>> products = createProducts(categories);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("warehouse.json"), products);
        } catch (IOException e) {
            System.err.println("Ошибка сериализации в JSON: " + e.getMessage());
        }

        return new Warehouse(products);
    }

    private static Cart createCart(Warehouse whouse) {
        Cart cart = new Cart(new ArrayList<Product>());
        Random random = new Random();

        List<Product> allProducts = whouse.getProducts().values().stream()
                .flatMap(map -> map.values().stream())
                .collect(Collectors.toList());

        int numProducts = random.nextInt(5) + 1;
        for (int i = 0; i < numProducts; i++) {
            Product product = allProducts.get(random.nextInt(allProducts.size()));

            int quantity = random.nextInt(3) + 1;
            cart.addProduct(product, quantity);
        }

        return cart;
    }

    private static List<Category> createCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Fruit"));
        categories.add(new Category(2, "Vegetables"));
        categories.add(new Category(3, "Diary"));
        return categories;
    }

    private static Map<Integer, Map<Integer, Product>> createProducts(List<Category> categories) {
        Map<Integer, Map<Integer, Product>> products = new HashMap<>();
        Random random = new Random();

        for (Category category : categories) {
            Map<Integer, Product> categoryProducts = new HashMap<>();
            for (int i = 0; i < 5; i++) {
                int productId = category.getId() * 100 + i + 1;
                String productName = category.getName() + " " + (i + 1);
                int price = random.nextInt(100) + 10;
                int quantity = random.nextInt(20) + 5;
                categoryProducts.put(productId, new Product(productId, productName, price,
                        category.getId(), quantity));
            }
            products.put(category.getId(), categoryProducts);
        }
        return products;
    }
}

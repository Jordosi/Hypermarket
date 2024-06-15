package task.system;

import lombok.*;
import task.good.*;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

@Data
public class Warehouse {
    private final Map<Integer, Map<Integer, Product>> products;

    public Product getProduct(int categoryID, int productID){
        return products.getOrDefault(categoryID, Collections.emptyMap()).get(productID);
    }

    public synchronized boolean updateQuantity(int categoryId, int productId, int quantityChange) {
        Map<Integer, Product> categoryProducts = products.get(categoryId);
        if (categoryProducts == null) {
            return false;
        }
        Product product = categoryProducts.get(productId);
        if (product == null) {
            return false;
        }
        int newQuantity = product.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            return false;
        }
        product.setQuantity(newQuantity);
        return true;
    }

    public void addProduct(int categoryId, Product product) {
        Map<Integer, Product> categoryProducts = products.computeIfAbsent(categoryId, k -> new HashMap<>());
        categoryProducts.put(product.getId(), product);
    }

    public void removeProduct(int categoryId, int productId) {
        Map<Integer, Product> categoryProducts = products.get(categoryId);
        if (categoryProducts != null) {
            categoryProducts.remove(productId);
        }
    }
}

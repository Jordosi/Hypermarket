package task.customer;

import task.good.Product;
import lombok.*;

import java.util.ArrayList;


@Data
@AllArgsConstructor
public class Cart {
    private ArrayList<Product> cart;

    public ArrayList<Product> getProducts() {
        return cart;
    }

    public void addProduct (Product product, int quantity){
        product.setQuantity(quantity);
        cart.add(product);
    }

    public void removeProduct(Product product){
        cart.remove(product);
    }
}
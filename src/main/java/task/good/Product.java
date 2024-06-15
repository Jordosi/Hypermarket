package task.good;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Product implements Serializable {

    private int id;
    private String name;
    private double price;
    private int categoryID;
    private int quantity;


    //TODO: remove this constructor???
    public Product(int id, String name, double price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}


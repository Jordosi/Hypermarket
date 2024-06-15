package task.customer;

import lombok.*;
import task.good.*;

import java.util.ArrayList;
import java.util.Random;

@Data
public class Customer {
    private Cart cart;
    private int checkoutTime;

    public Customer(Cart cart){
        this.cart = cart;
        this.checkoutTime = setRandomCheckOutTime();
    }

    private int setRandomCheckOutTime(){
        Random random = new Random();
        return random.nextInt(5000) + 1000;
    }
}

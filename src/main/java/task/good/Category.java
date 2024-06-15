package task.good;

import lombok.*;

@Data
public class Category {
    private String name;
    private int id;

    public Category(int id, String name){
        this.id = id;
        this.name = name;
    }
}
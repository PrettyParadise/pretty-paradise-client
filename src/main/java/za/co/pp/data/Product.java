package za.co.pp.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Product {
    private long id;
    private String name;
    private double price;
    private String image;

    public Product(long id, String name, Double price, String image){
        this.id=id;
        this.name=name;
        this.price=price;
        this.image=image;
    }
}

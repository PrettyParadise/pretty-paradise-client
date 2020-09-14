package za.co.pp.data.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDomainObject {
    private long id;
    private String name;
    private double price;
    private byte[] image;

}

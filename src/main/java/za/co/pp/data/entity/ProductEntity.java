package za.co.pp.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@Entity
public class ProductEntity {
    @Id
    private Long id;

    private String name;
    private Double price;
    @Transient
    private Resource image;
}

package za.co.pp.service;

import org.springframework.stereotype.Service;
import za.co.pp.data.Product;

@Service
public interface ProductService {
    public Product getProduct(long id);
}

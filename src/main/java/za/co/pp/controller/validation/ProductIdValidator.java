package za.co.pp.controller.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import za.co.pp.data.repository.ProductRepository;
import za.co.pp.exception.PrettyParadiseClientException;

@Component
public class ProductIdValidator {
    private ProductRepository productRepository;

    @Autowired
    public ProductIdValidator(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public void validateProductId(final long productId) {
        this.productRepository.findById(productId).orElseThrow(() -> new PrettyParadiseClientException("A product with the specified ID does not exist.", HttpStatus.NOT_FOUND));
    }
}

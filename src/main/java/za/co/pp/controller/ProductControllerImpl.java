package za.co.pp.controller;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import za.co.pp.controller.validation.ProductIdValidator;
import za.co.pp.data.domain.ProductDomainObject;
import za.co.pp.data.dto.Product;
import za.co.pp.data.mapper.ProductMapper;
import za.co.pp.service.ProductService;

@RestController
@Slf4j
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    private ProductMapper productMapper;
    private ProductIdValidator productIdValidator;

    @Autowired
    public ProductControllerImpl(ProductService productService, ProductMapper productMapper, ProductIdValidator productIdValidator) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.productIdValidator = productIdValidator;
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProductDomainObjects().stream()
                .map(productDomainObject -> productMapper.domainObjectToDto(productDomainObject))
                .collect(Collectors.toList());
        return new ResponseEntity(products, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> getProduct(final long productId) {
        this.productIdValidator.validateProductId(productId);
        ProductDomainObject productDomainObject = productService.getProductDomainObject(productId);
        Product product = this.productMapper.domainObjectToDto(productDomainObject);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}

package za.co.pp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.pp.data.dto.Product;
import za.co.pp.data.mapper.ProductMapper;
import za.co.pp.service.ProductService;

@RestController
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    public ProductControllerImpl(ProductService productService){
        this.productService = productService;
    }

    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts().stream()
                .map(productDomainObject -> productMapper.domainObjectToDto(productDomainObject))
                .collect(Collectors.toList());
        return new ResponseEntity(products, HttpStatus.OK);
    }

}

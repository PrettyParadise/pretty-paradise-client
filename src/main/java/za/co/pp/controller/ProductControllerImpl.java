package za.co.pp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.pp.data.dto.Product;
import za.co.pp.service.ProductService;

@RestController
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @Autowired
    public ProductControllerImpl(ProductService productService){
        this.productService = productService;
    }

    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity(productService.getAllProducts(), HttpStatus.OK);
    }

}

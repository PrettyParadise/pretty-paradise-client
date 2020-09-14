package za.co.pp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import za.co.pp.data.dto.Product;

public interface ProductController {

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    default ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}

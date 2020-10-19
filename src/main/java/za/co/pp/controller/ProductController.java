package za.co.pp.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import za.co.pp.data.dto.Product;

public interface ProductController {

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    default ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
    default ResponseEntity<Product> getProduct(@PathVariable long productId){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}

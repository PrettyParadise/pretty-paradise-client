package za.co.pp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.pp.client.api.ProductsApi;
import za.co.pp.client.api.model.Product;
import za.co.pp.service.ProductService;

import java.util.ArrayList;
import java.util.List;

//@RestController
//public class ProductsController implements ProductsApi {
//
////    @Autowired
////    private ProductService productService;
////
////    @Override
////    public ResponseEntity<List<Product>> getAllProducts() {
////        List<Product> products = productService.getAllProducts();
////        return new ResponseEntity<>(products, HttpStatus.OK);
////    }
//}

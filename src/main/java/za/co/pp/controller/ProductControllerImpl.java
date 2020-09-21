package za.co.pp.controller;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import za.co.pp.data.dto.Product;
import za.co.pp.data.mapper.ProductMapper;
import za.co.pp.service.ProductService;

@RestController
@Slf4j
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    public ProductControllerImpl(ProductService productService){
        this.productService = productService;
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts().stream()
                .map(productDomainObject -> productMapper.domainObjectToDto(productDomainObject))
                .collect(Collectors.toList());
        return new ResponseEntity(products, HttpStatus.OK);
    }
}

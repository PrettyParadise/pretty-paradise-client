package za.co.pp.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import za.co.pp.controller.validation.ProductIdValidator;

@SessionAttributes("cart")
@RestController
@Slf4j
public class CartControllerImpl implements CartController {

    private final ProductIdValidator productIdValidator;

    @Autowired
    public CartControllerImpl(ProductIdValidator productIdValidator){
        this.productIdValidator = productIdValidator;
    }

    @Override
    public ResponseEntity<Void> addProductToCart(List<Long> cart, final long productId) {
        this.productIdValidator.validateProductId(productId);
        cart.add(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ModelAttribute("cart")
    public List<Long> cart() {
        return new ArrayList<>();
    }

}

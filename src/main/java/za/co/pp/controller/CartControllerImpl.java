package za.co.pp.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import za.co.pp.controller.validation.ProductIdValidator;
import za.co.pp.controller.validation.ProductValidator;
import za.co.pp.service.ProductService;

@SessionAttributes("cart")
@RestController
@Slf4j
public class CartControllerImpl implements CartController {

    private final ProductIdValidator productIdValidator;
    private final ProductService productService;

    @Autowired
    public CartControllerImpl(final ProductIdValidator productIdValidator,
                              final ProductService productService){
        this.productIdValidator = productIdValidator;
        this.productService = productService;
    }

    @Override
    public ResponseEntity<Void> addProductToCart(List<Long> cart, final long productId) {
        this.productIdValidator.validateProductId(productId);
        cart.add(productId);
        log.info("Cart content: " + cart.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> requestProducts(final List<Long> cart, String emailAddress) {
        ProductValidator.validateEmailAddress(emailAddress);
        this.productService.sendEmailToRequestProductsInCart(cart, emailAddress);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Long>> getCartItems(List<Long> cart) {
        return new ResponseEntity(cart, HttpStatus.OK);
    }

    @ModelAttribute("cart")
    public List<Long> cart() {
        return new ArrayList<>();
    }

}

package za.co.pp.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface CartController {
    @RequestMapping(value = "cart/{productId}", method = RequestMethod.POST)
    default ResponseEntity<Void> addProductToCart(@ModelAttribute("cart") List<Long> cart, @PathVariable(value = "productId") long productId){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}

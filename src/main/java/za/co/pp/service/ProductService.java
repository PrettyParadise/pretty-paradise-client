package za.co.pp.service;

import java.util.List;

import za.co.pp.data.domain.ProductDomainObject;
import za.co.pp.data.dto.Product;

public interface ProductService {

    List<ProductDomainObject> getAllProducts();

    void sendEmailToRequestProductsInCart(List<Long> cartProductIdItems, String emailAddress);

}

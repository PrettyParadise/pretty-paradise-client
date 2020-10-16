package za.co.pp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import za.co.pp.data.domain.ProductDomainObject;
import za.co.pp.data.entity.ProductEntity;
import za.co.pp.data.mapper.ProductMapper;
import za.co.pp.data.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private final JavaMailSender emailSender;

    @Autowired
    public ProductServiceImpl(final JavaMailSender emailSender,
                              final ProductRepository productRepository,
                              final ProductMapper productMapper) {
        this.emailSender = emailSender;
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDomainObject> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<ProductDomainObject> productDomainObjects = productEntities.stream().map(
                productEntity -> { return productMapper.entityToDomain(productEntity);}
        ).collect(Collectors.toList());
        return productDomainObjects;
    }

    @Override
    public void sendEmailToRequestProductsInCart(final List<Long> cartProductIdItems, String emailAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreplyprettyparadise@gmail.com");
        message.setTo("krugerquintin5@gmail.com");
        message.setSubject("New Order");
        message.setText(prepareEmailBody(cartProductIdItems, emailAddress));
        emailSender.send(message);

    }

    private String prepareEmailBody(List<Long> cartProductIdItems, String emailAddress) {
        List<String> productDescriptions = createProductDescriptionsList(cartProductIdItems);
        String message = "";
        for(String productDescription: productDescriptions){
            message += productDescription + "\n";
        }
        message += "\n" + emailAddress + " placed the order.";
        return message;
    }

    private List<String> createProductDescriptionsList(final List<Long> cartProductIdItems) {
        List<String> productIdNameAndPrices = new ArrayList<>();
        for (Long productId: cartProductIdItems) {
            ProductEntity product = this.productRepository.findById(productId).get();
            String message = product.getId() + " - " + product.getName() + " - R" + product.getPrice();
            productIdNameAndPrices.add(message);
        }
        return productIdNameAndPrices;
    }
}

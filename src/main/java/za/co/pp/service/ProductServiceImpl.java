package za.co.pp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.pp.data.domain.ProductDomainObject;
import za.co.pp.data.dto.Product;
import za.co.pp.data.entity.ProductEntity;
import za.co.pp.data.mapper.ProductMapper;
import za.co.pp.data.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDomainObject> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<ProductDomainObject> productDomainObjects = productEntities.stream().map(
                productEntity -> { return productMapper.entityToDomain(productEntity);}
        ).collect(Collectors.toList());
        return productDomainObjects;
    }
}

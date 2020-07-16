package za.co.pp.service;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private ProductMapper productMapper;
////        Mappers.getMapper(ProductMapper.class);
//
//    public List<Product> getAllProducts(){
//        List<ProductEntity> productEntities = productRepository.findAll();
//        List<ProductDomainObject> productDomainObjects = (List<ProductDomainObject>) productEntities.stream().map(
//                (product)->{ return productMapper.ProductEntityToProductDomainObject(product); });
//        List<Product> productObjects = (List<Product>) productDomainObjects.stream().map(
//                (product)->{ return productMapper.ProductDomainObjectToProduct(product); });
//        return productObjects;
//    }
}

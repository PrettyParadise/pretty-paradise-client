package za.co.pp.data.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.pp.data.domain.ProductDomainObject;
import za.co.pp.data.entity.ProductEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static za.co.pp.utils.ProductUtils.getTestProductImageByteArray;

@SpringBootTest
public class ProductMapperUnitTest {
    @Autowired
    ProductMapper productMapper;

    @Test
    void canMapEntityToDomain() throws Exception {
        ProductEntity productEntity = new ProductEntity(1L, "Pink and Pretty", 20.00, getTestProductImageByteArray());

        ProductDomainObject productDomainObject = productMapper.entityToDomain(productEntity);

        assertThat(productEntity.getId()).isEqualTo(productDomainObject.getId());
        assertThat(productEntity.getName()).isEqualTo(productDomainObject.getName());
        assertThat(productEntity.getPrice()).isEqualTo(productDomainObject.getPrice());
        assertThat(productDomainObject.getImage()).isEqualTo(getTestProductImageByteArray());
    }

}

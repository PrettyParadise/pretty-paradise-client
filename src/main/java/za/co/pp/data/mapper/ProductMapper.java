package za.co.pp.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import za.co.pp.data.domain.ProductDomainObject;
import za.co.pp.data.dto.Product;
import za.co.pp.data.entity.ProductEntity;

@Mapper(componentModel = "spring", uses = ByteArrayMapper.class)
@Component
public interface ProductMapper {
    ProductDomainObject entityToDomain(ProductEntity productEntity);

    @Mapping(source = "image", target = "encodedImage", qualifiedByName = "byteArrayToEncodedString")
    Product domainObjectToDto(ProductDomainObject productDomainObject);
}

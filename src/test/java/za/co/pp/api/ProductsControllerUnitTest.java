package za.co.pp.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import za.co.pp.data.Product;
import za.co.pp.service.ProductService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@ContextConfiguration(classes = PrettyParadiseClientApplication.class)
@WebMvcTest(ProductsController.class)
//@SpringBootTest
public class ProductsControllerUnitTest {
    private static final Product PRODUCT=new Product(1, "Doring", 44.50, "/here/there/and_there");

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void canGetAProduct() throws Exception {
//        when(productService.getProduct(1L)).thenReturn(PRODUCT);
        mockMvc.perform(get("/products/{id}", 1L))
            .andExpect(status().isFound());
//            .andExpect(jsonPath("$.id", is(1)))
//            .andExpect(jsonPath("$.name", is("Doring")));
//            .andExpect(jsonPath("$.price", is(44.50)))
//            .andExpect(jsonPath("$.image", is("/here/there/and_there"))).andReturn();
    }
}

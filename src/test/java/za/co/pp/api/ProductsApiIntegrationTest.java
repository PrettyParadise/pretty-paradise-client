package za.co.pp.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import za.co.pp.PrettyParadiseClientApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = PrettyParadiseClientApplication.class)
//@WebMvcTest(ProductsController.class)
@SpringBootTest
public class ProductsApiIntegrationTest {
//    @Autowired
//    private DataSource dataSource;

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void canGetAllProducts() throws Exception {
//        mockMvc.perform(get("/products")).andExpect(status().isOk());
//    }

    @Test
    void canLoadContext(){}

}

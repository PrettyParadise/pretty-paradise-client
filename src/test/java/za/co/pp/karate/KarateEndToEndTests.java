package za.co.pp.karate;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class KarateEndToEndTests {

    @Karate.Test
    Karate canViewAndRequestProducts(){
        return Karate.run("classpath:karate/view-and-request-products.feature").relativeTo(this.getClass());
    }

}

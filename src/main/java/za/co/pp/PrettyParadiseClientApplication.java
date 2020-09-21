package za.co.pp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
//import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;

@SpringBootApplication
@EnableEurekaClient
//@EnableJdbcHttpSession
public class PrettyParadiseClientApplication {

    public static void main(String... args) {
        SpringApplication.run(PrettyParadiseClientApplication.class, args);
    }

}

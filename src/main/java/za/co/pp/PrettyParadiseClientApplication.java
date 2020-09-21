package za.co.pp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PrettyParadiseClientApplication {

    public static void main(String... args) {
        SpringApplication.run(PrettyParadiseClientApplication.class, args);
    }

}

package microfood.tickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"microfood.orders.client"})
@ComponentScan(basePackages = "microfood")
public class RestaurantTicketsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantTicketsApplication.class, args);
    }

}

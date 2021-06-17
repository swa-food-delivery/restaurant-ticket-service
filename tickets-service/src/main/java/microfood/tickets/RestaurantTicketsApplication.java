package microfood.tickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"microfood.orders.client"})
public class RestaurantTicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantTicketsApplication.class, args);
	}

}

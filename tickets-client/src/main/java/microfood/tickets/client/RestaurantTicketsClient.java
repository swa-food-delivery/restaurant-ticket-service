package microfood.tickets.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import microfood.tickets.dtos.TicketBaseDTO;
import microfood.tickets.dtos.TicketDTO;

@FeignClient(name = "restaurant-tickets-service")
public interface RestaurantTicketsClient {
    @RequestMapping(method = RequestMethod.POST, value = "/tickets/{restaurantId}", consumes = "application/json")
    TicketDTO createTicket(@PathVariable UUID restaurantId, TicketBaseDTO ticketBaseDTO);
}

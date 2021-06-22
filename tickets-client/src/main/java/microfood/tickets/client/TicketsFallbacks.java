package microfood.tickets.client;

import java.util.UUID;


import org.springframework.stereotype.Component;

import microfood.tickets.client.exceptions.ServiceUnavailableException;
import microfood.tickets.dtos.TicketBaseDTO;
import microfood.tickets.dtos.TicketDTO;

@Component("TicketsFallbacks")
public class TicketsFallbacks implements RestaurantTicketsClient {

    @Override
    public TicketDTO createTicket(UUID restaurantId, TicketBaseDTO ticketBaseDTO) {
        throw new ServiceUnavailableException("restaurant-tickets-service is currently unavailable");
    }
}

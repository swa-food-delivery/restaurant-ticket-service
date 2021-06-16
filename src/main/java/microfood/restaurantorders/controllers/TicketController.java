package microfood.restaurantorders.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import microfood.restaurantorders.dtos.StatusDTO;
import microfood.restaurantorders.dtos.TicketBaseDTO;
import microfood.restaurantorders.dtos.TicketDTO;
import microfood.restaurantorders.dtos.TicketStatusEnum;
import microfood.restaurantorders.exceptions.BadRequestException;
import microfood.restaurantorders.exceptions.NotFoundException;
import microfood.restaurantorders.services.RestaurantTicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final RestaurantTicketService ticketService;

    @Autowired
    public TicketController(RestaurantTicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/{restaurantId}")
    public TicketDTO createTicket(@PathVariable UUID restaurantId, @RequestBody TicketBaseDTO ticketBaseDTO) {
        return ticketService.createTicket(restaurantId, ticketBaseDTO);
    }

    @GetMapping("/{restaurantId}")
    public List<TicketDTO> getTickets(@PathVariable UUID restaurantId,
                                      @RequestParam Optional<TicketStatusEnum> ticketStatus) {
        return ticketService.getTickets(restaurantId, ticketStatus);
    }

    @PutMapping("/status/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTicketStatus(@PathVariable UUID ticketId, @RequestBody StatusDTO statusDTO)
            throws NotFoundException, BadRequestException {
        ticketService.updateTicketStatus(ticketId, statusDTO.getStatus());
    }
}

package microfood.restaurantorders.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import microfood.restaurantorders.TicketRepository;
import microfood.restaurantorders.dtos.TicketBaseDTO;
import microfood.restaurantorders.dtos.TicketDTO;
import microfood.restaurantorders.dtos.TicketStatusEnum;
import microfood.restaurantorders.entities.Ticket;
import microfood.restaurantorders.exceptions.BadRequestException;
import microfood.restaurantorders.exceptions.NotFoundException;

@Service
@Transactional
public class RestaurantTicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public RestaurantTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketDTO createTicket(UUID restaurantId, TicketBaseDTO ticketBaseDTO) {
        Ticket ticket = new Ticket();
        ticket.setRestaurantId(restaurantId);
        ticket.setOrderId(ticketBaseDTO.getOrderId());
        ticket.setStatus(TicketStatusEnum.PENDING);
        ticket = ticketRepository.save(ticket);
        return ticket.toDto();
    }

    public List<TicketDTO> getTickets(UUID restaurantId, Optional<TicketStatusEnum> status) {
        List<Ticket> tickets;
        if (status.isPresent()) {
            tickets = ticketRepository.findByRestaurantIdAndStatus(restaurantId, status.get());
        } else {
            tickets = ticketRepository.findByRestaurantId(restaurantId);
        }
        return tickets.stream().map(Ticket::toDto).collect(Collectors.toList());
    }

    public void updateTicketStatus(UUID ticketId, TicketStatusEnum status) throws NotFoundException, BadRequestException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException(String.format("Ticket with id %s not found",
                        ticketId.toString())));
        if (ticket.getStatus().getNextStates().contains(status)) {
            ticket.setStatus(status);
            ticketRepository.save(ticket);
        } else {
            throw new BadRequestException(String.format("Cannot transition from state %s to %s",
                    ticket.getStatus(), status));
        }

        // TODO: notify order service
    }
}

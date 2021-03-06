package microfood.tickets.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import microfood.orders.client.OrdersClient;
import microfood.orders.dtos.OrderStatusDTO;
import microfood.orders.enums.OrderStatusEnum;
import microfood.tickets.TicketRepository;
import microfood.tickets.dtos.TicketBaseDTO;
import microfood.tickets.dtos.TicketDTO;
import microfood.tickets.enums.TicketStatusEnum;
import microfood.tickets.entities.Ticket;
import microfood.tickets.exceptions.BadRequestException;
import microfood.tickets.exceptions.NotFoundException;

@Service
@Transactional
@Slf4j
public class RestaurantTicketService {
    private final TicketRepository ticketRepository;
    private final OrdersClient ordersClient;

    @Autowired
    public RestaurantTicketService(TicketRepository ticketRepository, OrdersClient ordersClient) {
        this.ticketRepository = ticketRepository;
        this.ordersClient = ordersClient;
    }

    public TicketDTO createTicket(UUID restaurantId, TicketBaseDTO ticketBaseDTO) {
        log.info("Creating new ticket for restaurant with id {}", restaurantId);
        Ticket ticket = new Ticket();
        ticket.setRestaurantId(restaurantId);
        ticket.setOrderId(ticketBaseDTO.getOrderId());
        ticket.setStatus(TicketStatusEnum.PENDING);
        ticket = ticketRepository.save(ticket);
        log.info("Ticket created with id: {}", ticket.getTicketId());
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
            log.info("Updating ticket status. ticketId: {}, previous status: {}, new status {}",
                    ticketId, ticket.getStatus(), status);
            ticket.setStatus(status);
            ticketRepository.save(ticket);
        } else {
            log.error("Status update was unsuccessful.");
            throw new BadRequestException(String.format("Cannot transition from state %s to %s",
                    ticket.getStatus(), status));
        }

        OrderStatusDTO statusDTO = new OrderStatusDTO();
        switch (status) {
            case ACCEPTED:
                statusDTO.setStatus(OrderStatusEnum.ACCEPTED);
                break;
            case DECLINED:
                statusDTO.setStatus(OrderStatusEnum.DECLINED);
                break;
            case PREPARED:
                statusDTO.setStatus(OrderStatusEnum.PREPARED);
                break;
            default:
                throw new BadRequestException(String.format("Cannot set order status for ticket status %s", status));
        }
        log.info("Notifying orders service about order status chnage");
        ordersClient.setOrderStatus(ticket.getOrderId(), statusDTO);
    }
}

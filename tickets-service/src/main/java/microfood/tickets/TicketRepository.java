package microfood.tickets;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import microfood.tickets.enums.TicketStatusEnum;
import microfood.tickets.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    public List<Ticket> findByRestaurantId(UUID restaurantId);

    public List<Ticket> findByRestaurantIdAndStatus(UUID restaurantId, TicketStatusEnum status);

}

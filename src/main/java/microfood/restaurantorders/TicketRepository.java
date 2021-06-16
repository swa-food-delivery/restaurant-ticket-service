package microfood.restaurantorders;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import microfood.restaurantorders.dtos.TicketStatusEnum;
import microfood.restaurantorders.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    public List<Ticket> findByRestaurantId(UUID restaurantId);

    public List<Ticket> findByRestaurantIdAndStatus(UUID restaurantId, TicketStatusEnum status);

}

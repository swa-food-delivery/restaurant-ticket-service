package microfood.restaurantorders.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import microfood.restaurantorders.dtos.TicketDTO;
import microfood.restaurantorders.dtos.TicketStatusEnum;

@Entity
@Table(name = "TICKETS")
@Data
public class Ticket {
    @Id
    @GeneratedValue
    @Column(name = "TICKET_ID")
    private UUID ticketId;

    @Column(name = "RESTAURANT_ID")
    private UUID restaurantId;

    @Column(name = "ORDER_ID")
    private UUID orderId;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TicketStatusEnum status;

    public TicketDTO toDto() {
        return new TicketDTO(this.ticketId, this.orderId, this.restaurantId, this.status);
    }
}

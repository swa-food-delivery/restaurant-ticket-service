package microfood.tickets.dtos;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import microfood.tickets.enums.TicketStatusEnum;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TicketDTO extends TicketBaseDTO {
    private UUID restaurantId;
    private TicketStatusEnum status;

    public TicketDTO(UUID ticketId, UUID orderId, UUID restaurantId, TicketStatusEnum status) {
        super(ticketId,orderId);
        this.restaurantId = restaurantId;
        this.status = status;
    }
}

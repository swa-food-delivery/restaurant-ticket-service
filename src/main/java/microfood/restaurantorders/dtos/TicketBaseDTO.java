package microfood.restaurantorders.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketBaseDTO {
    private UUID ticketId;
    private UUID orderId;
}

package microfood.tickets.dtos;

import lombok.Data;
import microfood.tickets.enums.TicketStatusEnum;

@Data
public class StatusDTO {
    private TicketStatusEnum status;
}

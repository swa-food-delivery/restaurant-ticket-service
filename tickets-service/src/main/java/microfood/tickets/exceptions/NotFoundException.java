package microfood.tickets.exceptions;

public class NotFoundException extends TicketServiceException {
    public NotFoundException(String message) {
        super(message);
    }
}

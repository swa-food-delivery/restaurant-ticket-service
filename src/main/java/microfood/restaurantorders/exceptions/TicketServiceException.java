package microfood.restaurantorders.exceptions;

public class TicketServiceException extends Exception {
    public TicketServiceException() {
        super();
    }

    public TicketServiceException(String message) {
        super(message);
    }

    public TicketServiceException(String message, final Throwable cause) {
        super(message, cause);
    }
}

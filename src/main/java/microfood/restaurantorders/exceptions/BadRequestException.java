package microfood.restaurantorders.exceptions;

public class BadRequestException extends TicketServiceException {
    public BadRequestException(String message) {
        super(message);
    }
}

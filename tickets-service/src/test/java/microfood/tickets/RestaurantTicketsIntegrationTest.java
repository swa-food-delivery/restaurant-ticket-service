package microfood.tickets;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import microfood.tickets.dtos.StatusDTO;
import microfood.tickets.dtos.TicketBaseDTO;
import microfood.tickets.dtos.TicketDTO;
import microfood.tickets.enums.TicketStatusEnum;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationtest")
@Transactional
@Slf4j
class RestaurantTicketsIntegrationTest {
    private ClientAndServer mockServer;

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    public void startServer() {
        mockServer = startClientAndServer(1080);

    }

    @AfterAll
    public void stopServer() {
        mockServer.stop();
    }

    @Test
    void testTicketCreateUpdate() {
        UUID restaurantId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        TicketBaseDTO ticketBaseDTO = new TicketBaseDTO(null, orderId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TicketBaseDTO> entity = new HttpEntity<>(ticketBaseDTO, headers);
        TicketDTO ticketDTO = restTemplate.exchange(String.format("/tickets/%s", restaurantId),
                HttpMethod.POST, entity, TicketDTO.class).getBody();

        List<TicketDTO> tickets = restTemplate.exchange(String.format("/tickets/%s", restaurantId),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketDTO>>() {
                }).getBody();

        List<TicketDTO> matchingTickets = tickets.stream()
                .filter(t -> t.getTicketId().equals(ticketDTO.getTicketId()))
                .collect(Collectors.toList());
        Assertions.assertFalse(matchingTickets.isEmpty());

        String ordersPath = "/orders/" + ticketDTO.getOrderId()+ "/status";
        new MockServerClient("localhost", 1080)
                .when(request()
                        .withPath(ordersPath)
                        .withMethod("PUT")
                ).respond(HttpResponse.response().withStatusCode(200));

        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setStatus(TicketStatusEnum.DECLINED);
        HttpEntity<StatusDTO> statusEntity = new HttpEntity<>(statusDTO, headers);
        ResponseEntity<Void> statusResponse = restTemplate.exchange("/tickets/status/" + ticketDTO.getTicketId(),
                HttpMethod.PUT, statusEntity, Void.class);

        new MockServerClient("localhost", 1080)
                .verify(request()
                                .withPath(ordersPath)
                                .withMethod("PUT"),
                        VerificationTimes.exactly(1)
                );
        Assertions.assertEquals(statusResponse.getStatusCode(), HttpStatus.OK);
    }

}

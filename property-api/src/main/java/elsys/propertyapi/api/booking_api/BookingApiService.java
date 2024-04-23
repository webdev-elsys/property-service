package elsys.propertyapi.api.booking_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingApiService {
    private final WebClient webClient;

    @Autowired
    public BookingApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public List<String> getBookedRooms(String propertyUuid, LocalDate checkInDate, LocalDate checkOutDate) {
        String endpoint = BookingApiEndpoints.getBookedRooms(propertyUuid, checkInDate, checkOutDate);

        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(List.class)
                .map(o -> (List<String>) o)
                .block();
    }
}

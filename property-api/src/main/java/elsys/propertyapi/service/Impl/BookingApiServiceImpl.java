package elsys.propertyapi.service.Impl;

import elsys.propertyapi.api.booking_api.BookingApiEndpoints;
import elsys.propertyapi.service.BookingApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingApiServiceImpl implements BookingApiService {
    private final WebClient webClient;

    @Autowired
    public BookingApiServiceImpl(WebClient.Builder webClientBuilder) {
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

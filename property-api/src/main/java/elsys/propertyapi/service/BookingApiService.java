package elsys.propertyapi.service;

import java.time.LocalDate;
import java.util.List;

public interface BookingApiService {
    public List<String> getBookedRooms(String propertyUuid, LocalDate checkInDate, LocalDate checkOutDate);
}

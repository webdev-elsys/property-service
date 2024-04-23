package elsys.propertyapi.service;

import elsys.propertyapi.dto.AddPropertyRequest;
import elsys.propertyapi.entity.Property;
import elsys.propertyapi.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PropertyService {
    Property addProperty(AddPropertyRequest propertyData, MultipartFile[] images) throws IOException, ExecutionException, InterruptedException;
    List<Room> getAvailableRooms(String propertyUuid, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests);
    Float getRoomPrice(String propertyUuid, String roomUuid);
}

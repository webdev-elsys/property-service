package elsys.propertyapi.service.Impl;

import elsys.propertyapi.dto.AddPropertyRequest;
import elsys.propertyapi.dto.AddRoomRequest;
import elsys.propertyapi.entity.Location;
import elsys.propertyapi.entity.Property;
import elsys.propertyapi.entity.Room;
import elsys.propertyapi.repository.PropertyRepository;
import elsys.propertyapi.repository.RoomRepository;
import elsys.propertyapi.service.AzureBlobService;
import elsys.propertyapi.service.BookingApiService;
import elsys.propertyapi.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static elsys.propertyapi.mapper.PropertyMapper.propertyMapper;
import static elsys.propertyapi.mapper.RoomMapper.roomMapper;
import static elsys.propertyapi.mapper.LocationMapper.locationMapper;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final AzureBlobService azureBlobService;
    private final BookingApiService bookingApiService;

    // TODO: this should be checked if indeed is async
    @Override
    public Property addProperty(AddPropertyRequest propertyData, MultipartFile[] images) throws IOException, ExecutionException, InterruptedException {
        Property property = propertyMapper.fromAddPropertyRequest(propertyData);
        property.setImages(new ArrayList<>());

        List<CompletableFuture<String>> imageUploadFutures = new ArrayList<>();
        for (MultipartFile image : images) {
            imageUploadFutures.add(azureBlobService.uploadImage(image));
        }

        CompletableFuture.allOf(imageUploadFutures.toArray(new CompletableFuture[0])).thenAccept(voidResult -> {
            for (CompletableFuture<String> imageUrlFuture : imageUploadFutures) {
                property.addImage(imageUrlFuture.join());
            }
        });

        List<Room> rooms = roomMapper.fromAddRoomRequestList(propertyData.rooms());
        rooms.forEach(room -> room.setProperty(property));
        property.setRooms(rooms);

        Location location = locationMapper.fromLocationDto(propertyData.location());
        location.setProperty(property);
        property.setLocation(location);

        return propertyRepository.save(property);
    }

    @Override
    public List<Room> getAvailableRooms(String propertyUuid, LocalDate checkInDate, LocalDate checkOutDate, int guests) {
        List<Room> rooms = roomRepository.getAllByPropertyUuidAndGuests(propertyUuid, guests);

        if (rooms.isEmpty()) {
            return rooms;
        }

        List<String> nonAvailableRooms = bookingApiService.getBookedRooms(propertyUuid, checkInDate, checkOutDate);
        rooms.removeIf(room -> nonAvailableRooms.contains(room.getUuid()));

        return rooms;
    }

    @Override
    public Float getRoomPrice(String propertyUuid, String roomUuid) {
        return roomRepository.findByPropertyUuidAndUuid(propertyUuid, roomUuid).getPricePerNight();
    }

    @Override
    public List<Property> getOwnerProperties(String ownerUuid) {
        return propertyRepository.getAllByOwnerUuid(ownerUuid);
    }

    @Override
    public Page<Property> getPropertiesByCityAndCountry(String city, String country, Pageable pageable) {
        if (city == null) {
            return propertyRepository.getAllByLocationCountry(country, pageable);
        } else {
            return propertyRepository.getAllByLocationCityAndLocationCountry(city, country, pageable);
        }
    }

    @Override
    public String getOwnerUuidByPropertyUuid(String propertyUuid) {
        return propertyRepository.getByUuid(propertyUuid).getOwnerUuid();
    }

    @Override
    public Property addRoomToProperty(String propertyUuid, AddRoomRequest addRoomRequest) {
        Property property = propertyRepository.findById(propertyUuid).orElseThrow();

        Room room = roomMapper.fromAddRoomRequest(addRoomRequest);
        room.setProperty(property);
        property.getRooms().add(room);

        return propertyRepository.save(property);
    }

    @Override
    public Room updatePropertyRoom(String propertyUuid, String roomUuid, AddRoomRequest addRoomRequest) {
        Property property = propertyRepository.findById(propertyUuid).orElseThrow();
        Room room = roomMapper.fromAddRoomRequest(addRoomRequest);

        property.getRooms().removeIf(r -> r.getUuid().equals(roomUuid));
        property.getRooms().add(room);

        propertyRepository.save(property);

        return room;
    }

    @Override
    public Property updateProperty(String propertyUuid, AddPropertyRequest addPropertyRequest) {
        Property property = propertyRepository.findById(propertyUuid).orElseThrow();

        property.setTitle(addPropertyRequest.title());
        property.setDescription(addPropertyRequest.description());
        property.setLocation(locationMapper.fromLocationDto(addPropertyRequest.location()));
        property.setFacilities(addPropertyRequest.facilities());
        property.setRooms(roomMapper.fromAddRoomRequestList(addPropertyRequest.rooms()));

        return propertyRepository.save(property);
    }

    @Override
    public void deleteProperty(String propertyUuid) {
        Property property = propertyRepository.findById(propertyUuid).orElseThrow();
        property.setDeleted(true);
        propertyRepository.save(property);
    }

    @Override
    public void deleteRoom(String propertyUuid, String roomUuid) {
        Property property = propertyRepository.findById(propertyUuid).orElseThrow();
        property.getRooms()
                .stream()
                .filter(
                        room -> room.getUuid().equals(roomUuid)
                ).findFirst().ifPresent(room -> room.setDeleted(true));

        propertyRepository.save(property);
    }
}

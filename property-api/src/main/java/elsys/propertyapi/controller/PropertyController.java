package elsys.propertyapi.controller;

import com.azure.core.annotation.Patch;
import elsys.propertyapi.dto.AddPropertyRequest;
import elsys.propertyapi.dto.AddRoomRequest;
import elsys.propertyapi.entity.Property;
import elsys.propertyapi.entity.Room;
import elsys.propertyapi.service.PropertyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping("/")
    public ResponseEntity<Property> addProperty(
        @RequestPart("data") @Valid AddPropertyRequest propertyData,
        @RequestPart("images") MultipartFile[] images
    ) throws IOException, ExecutionException, InterruptedException {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.addProperty(propertyData, images));
    }

    @GetMapping("/{propertyUuid}/rooms/available")
    public ResponseEntity<List<Room>> getAvailableRooms(
        @RequestParam("checkInDate") @Future LocalDate checkInDate,
        @RequestParam("checkOutDate") @Future LocalDate checkOutDate,
        @RequestParam("numberOfGuests") @Positive int numberOfGuests,
        @PathVariable String propertyUuid
    ) {
        return ResponseEntity.ok(propertyService.getAvailableRooms(propertyUuid, checkInDate, checkOutDate, numberOfGuests));
    }

    @GetMapping("/{propertyUuid}/rooms/{roomUuid}/pricePerNight")
    public ResponseEntity<Float> getRoomPrice(
        @PathVariable @UUID String propertyUuid,
        @PathVariable @UUID String roomUuid
    ) {
        return ResponseEntity.ok(propertyService.getRoomPrice(propertyUuid, roomUuid));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<Property>> getOwnerProperties(@RequestParam("ownerUuid") @UUID String ownerUuid) {
        return ResponseEntity.ok(propertyService.getOwnerProperties(ownerUuid));
    }

    @GetMapping("/location")
    public ResponseEntity<Page<Property>> getPropertiesByCityAndCountry(
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "country") String country,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Property> properties = propertyService.getPropertiesByCityAndCountry(city, country, pageable);
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{propertyUuid}/ownerUuid")
    public ResponseEntity<String> getOwnerUuidByPropertyUuid(@PathVariable @UUID String propertyUuid) {
        return ResponseEntity.ok(propertyService.getOwnerUuidByPropertyUuid(propertyUuid));
    }

    // 1. Add room to property
    @PatchMapping("/{propertyUuid}/rooms")
    public ResponseEntity<Property> addRoomToProperty(
        @PathVariable @UUID String propertyUuid,
        @RequestBody @Valid AddRoomRequest addRoomRequest
    ) {
        return ResponseEntity.ok(propertyService.addRoomToProperty(propertyUuid, addRoomRequest));
    }


    // 2. Update room
    @PatchMapping("/{propertyUuid}/rooms/{roomUuid}")
    public ResponseEntity<Room> updatePropertyRoom(
        @PathVariable @UUID String propertyUuid,
        @PathVariable @UUID String roomUuid,
        @RequestBody @Valid AddRoomRequest addRoomRequest
    ) {
        return ResponseEntity.ok(propertyService.updatePropertyRoom(propertyUuid, roomUuid, addRoomRequest));
    }

    // 3. Update property
    @PatchMapping("/{propertyUuid}")
    public ResponseEntity<Property> updateProperty(
        @PathVariable @UUID String propertyUuid,
        @RequestBody @Valid AddPropertyRequest addPropertyRequest
    ) {
        return ResponseEntity.ok(propertyService.updateProperty(propertyUuid, addPropertyRequest));
    }

    // 4. Delete property - soft delete (a.k.a. isDeleted = true; do not remove from DB, just add a flag)
    @PatchMapping("delete/{propertyUuid}")
    public ResponseEntity<Void> deleteProperty(@PathVariable @UUID String propertyUuid) {
        propertyService.deleteProperty(propertyUuid);
        return ResponseEntity.noContent().build();
    }

    // 5. Delete room - soft delete (a.k.a. isDeleted = true; do not remove from DB, just add a flag)
    @PatchMapping("delete/{propertyUuid}/rooms/{roomUuid}")
    public ResponseEntity<Void> deleteRoom(
        @PathVariable @UUID String propertyUuid,
        @PathVariable @UUID String roomUuid
    ) {
        propertyService.deleteRoom(propertyUuid, roomUuid);
        return ResponseEntity.noContent().build();
    }
}

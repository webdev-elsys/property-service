package elsys.propertyapi.controller;

import elsys.propertyapi.dto.AddPropertyRequest;
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

    @PostMapping()
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

    @GetMapping("/owner/{ownerUuid}")
    public ResponseEntity<List<Property>> getOwnerProperties(@PathVariable @UUID String ownerUuid) {
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
    public ResponseEntity<String> getOwnerUuid(@PathVariable @UUID String propertyUuid) {
        return ResponseEntity.ok(propertyService.getOwnerUuid(propertyUuid));
    }

    // TODO: Implement the following methods:
    // 1. Add room to property
    // 2. Update room
    // 3. Update property
    // 4. Delete property - soft delete (a.k.a. isDeleted = true; do not remove from DB, just add a flag)
    // 5. Delete room - soft delete (a.k.a. isDeleted = true; do not remove from DB, just add a flag)

}

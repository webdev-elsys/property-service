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

    @GetMapping()
    public ResponseEntity<List<Property>> getOwnerProperties(@RequestParam("ownerUuid") @UUID String ownerUuid) {
        return ResponseEntity.ok(propertyService.getOwnerProperties(ownerUuid));
    }

    // UPDATE: added the following method

    // DELETE: removed the following method

    // ADD ROOM: added the following method

    // DELETE ROOM: added the following method

    // UPDATE ROOM: added the following method
}

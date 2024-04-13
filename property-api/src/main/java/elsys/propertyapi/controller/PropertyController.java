package elsys.propertyapi.controller;

import elsys.propertyapi.dto.AddPropertyRequest;
import elsys.propertyapi.entity.Property;
import elsys.propertyapi.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    // UPDATE: added the following method

    // DELETE: removed the following method

    // ADD ROOM: added the following method

    // DELETE ROOM: added the following method

    // UPDATE ROOM: added the following method
}

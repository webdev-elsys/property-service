package elsys.propertyapi.dto;

import elsys.propertyapi.entity.Location;
import elsys.propertyapi.entity.PropertyFacility;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

public record AddPropertyRequest(
    @NotNull @UUID String ownerUuid,
    @NotNull @NotEmpty String title,
    @NotNull @NotEmpty String description,
    @NotNull LocationDto location,
    List<PropertyFacility> facilities,
    List<AddRoomRequest> rooms
) { }

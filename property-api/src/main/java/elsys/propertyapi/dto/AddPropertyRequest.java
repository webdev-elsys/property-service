package elsys.propertyapi.dto;

import elsys.propertyapi.entity.PropertyFacility;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

public record AddPropertyRequest(
    @NotNull @UUID String ownerUuid,
    @NotNull @NotEmpty String title,
    @NotNull @NotEmpty String description,
    List<PropertyFacility> facilities,
    List<AddRoomRequest> rooms
) { }

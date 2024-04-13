package elsys.propertyapi.dto;

import elsys.propertyapi.entity.PropertyFacility;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@Data
public class AddPropertyRequest {
    @NotNull
    @UUID
    private final String ownerUuid;

    @NotNull
    @NotEmpty
    private final String title;

    @NotNull
    @NotEmpty
    private final String description;

    private final List<PropertyFacility> facilities;
    private final List<AddRoomRequest> rooms;
}

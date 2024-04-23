package elsys.propertyapi.dto;

import elsys.propertyapi.entity.RoomFacility;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class AddRoomRequest {
    @NotNull
    private final String type;

    @NotNull
    @Positive
    private final int guests;

    @NotNull
    @PositiveOrZero
    private final float pricePerNight;

    @NotNull
    @Positive
    private final int area;

    @NotNull
    private final List<RoomFacility> facilities;
}

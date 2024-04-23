package elsys.propertyapi.dto;

import elsys.propertyapi.entity.RoomFacility;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

public record AddRoomRequest(
    @NotNull String type,
    @NotNull @Positive int guests,
    @NotNull @PositiveOrZero float pricePerNight,
    @NotNull @Positive int area,
    @NotNull List<RoomFacility> facilities
) { }

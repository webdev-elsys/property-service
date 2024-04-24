package elsys.propertyapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LocationDto (
    @NotNull @NotEmpty String googlePlaceId,
    @NotNull @NotEmpty String country,
    @NotNull @NotEmpty String city,
    @NotNull @NotEmpty String address,
    @NotNull @Positive int postalCode,
    @NotNull double latitude,
    @NotNull double longitude
) {}

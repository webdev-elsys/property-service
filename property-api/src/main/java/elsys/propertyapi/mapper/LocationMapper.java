package elsys.propertyapi.mapper;

import elsys.propertyapi.dto.LocationDto;
import elsys.propertyapi.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
    LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    Location fromLocationDto(LocationDto locationDto);
}

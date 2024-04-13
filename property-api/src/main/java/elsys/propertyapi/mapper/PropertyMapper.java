package elsys.propertyapi.mapper;

import elsys.propertyapi.dto.AddPropertyRequest;
import elsys.propertyapi.entity.Property;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PropertyMapper {
    PropertyMapper propertyMapper = Mappers.getMapper(PropertyMapper.class);

    Property fromAddPropertyRequest(AddPropertyRequest addPropertyRequest);
}

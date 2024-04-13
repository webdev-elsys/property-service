package elsys.propertyapi.service.Impl;

import elsys.propertyapi.dto.AddPropertyRequest;
import elsys.propertyapi.entity.Property;
import elsys.propertyapi.repository.PropertyRepository;
import elsys.propertyapi.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static elsys.propertyapi.mapper.PropertyMapper.propertyMapper;
import static elsys.propertyapi.mapper.RoomMapper.roomMapper;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    @Override
    public Property addProperty(AddPropertyRequest propertyData, MultipartFile[] images) {
        Property property = propertyMapper.fromAddPropertyRequest(propertyData);

        // TODO: Implement image upload

        property.setRooms(roomMapper.fromAddRoomRequestList(propertyData.getRooms()));

        return propertyRepository.save(property);
    }
}

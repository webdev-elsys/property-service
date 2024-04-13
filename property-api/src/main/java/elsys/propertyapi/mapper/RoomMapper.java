package elsys.propertyapi.mapper;

import elsys.propertyapi.dto.AddRoomRequest;
import elsys.propertyapi.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoomMapper {
    RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    Room fromAddRoomRequest(AddRoomRequest addRoomRequest);
    List<Room> fromAddRoomRequestList(List<AddRoomRequest> addRoomRequests);
}

package elsys.propertyapi.repository;

import elsys.propertyapi.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> getAllByPropertyUuidAndGuests(String propertyUuid, int guests);
    Room findByPropertyUuidAndUuid(String propertyUuid, String roomUuid);
}

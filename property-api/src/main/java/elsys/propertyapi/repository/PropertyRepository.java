package elsys.propertyapi.repository;

import elsys.propertyapi.entity.Property;
import elsys.propertyapi.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {
    List<Property> getAllByOwnerUuid(String ownerUuid);
    Page<Property> getAllByLocationCityAndLocationCountry(String city, String country, Pageable pageable);
    Page<Property> getAllByLocationCountry(String country, Pageable pageable);
    Property getByUuid(String propertyUuid);
}

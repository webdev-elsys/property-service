package elsys.propertyapi.service;


import elsys.propertyapi.dto.AddPropertyRequest;
import elsys.propertyapi.entity.Property;
import org.springframework.web.multipart.MultipartFile;

public interface PropertyService {
    Property addProperty(AddPropertyRequest propertyData, MultipartFile[] images);
}

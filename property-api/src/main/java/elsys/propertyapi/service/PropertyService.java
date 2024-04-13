package elsys.propertyapi.service;

import elsys.propertyapi.dto.AddPropertyRequest;
import elsys.propertyapi.entity.Property;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface PropertyService {
    Property addProperty(AddPropertyRequest propertyData, MultipartFile[] images) throws IOException, ExecutionException, InterruptedException;
}

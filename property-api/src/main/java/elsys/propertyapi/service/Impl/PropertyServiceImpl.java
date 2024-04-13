package elsys.propertyapi.service.Impl;

import elsys.propertyapi.dto.AddPropertyRequest;
import elsys.propertyapi.entity.Property;
import elsys.propertyapi.repository.PropertyRepository;
import elsys.propertyapi.service.AzureBlobService;
import elsys.propertyapi.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static elsys.propertyapi.mapper.PropertyMapper.propertyMapper;
import static elsys.propertyapi.mapper.RoomMapper.roomMapper;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final AzureBlobService azureBlobService;

    // TODO: this should be checked if indeed is async
    @Override
    public Property addProperty(AddPropertyRequest propertyData, MultipartFile[] images) throws IOException, ExecutionException, InterruptedException {
        Property property = propertyMapper.fromAddPropertyRequest(propertyData);

        List<CompletableFuture<String>> imageUploadFutures = new ArrayList<>();
        for (MultipartFile image : images) {
            imageUploadFutures.add(azureBlobService.uploadImage(image));
        }

        CompletableFuture.allOf(imageUploadFutures.toArray(new CompletableFuture[0])).thenAccept(voidResult -> {
            for (CompletableFuture<String> imageUrlFuture : imageUploadFutures) {
                property.addImage(imageUrlFuture.join());
            }
        });

        property.setRooms(roomMapper.fromAddRoomRequestList(propertyData.getRooms()));

        return propertyRepository.save(property);
    }
}

package elsys.propertyapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AzureBlobService {
    CompletableFuture<String> uploadImage(MultipartFile file) throws IOException;
    CompletableFuture<Void> deleteImage(String imageUrl);
}

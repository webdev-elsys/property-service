package elsys.propertyapi.service.Impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import elsys.propertyapi.service.AzureBlobService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AzureBlobServiceImpl implements AzureBlobService {
    @Value("${azure.storage.connection-string}")
    private String azureStorageConnectionString;

    @Value("${azure.storage.container-name}")
    private String azureStorageContainerName;

    @Override
    @Async
    public CompletableFuture<String> uploadImage(MultipartFile file) throws IOException {
        BlobContainerClient blobContainerClient = getBlobContainerClient();

        String fileName = generateUniqueFileName(file);
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return CompletableFuture.completedFuture(blobClient.getBlobUrl());
    }

    @Override
    @Async
    public CompletableFuture<Void> deleteImage(String imageUrl) {
        BlobContainerClient blobContainerClient = getBlobContainerClient();

        String blobName = getBlobNameFromUrl(imageUrl);
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
        blobClient.delete();

        return CompletableFuture.completedFuture(null);
    }

    private BlobContainerClient getBlobContainerClient() {
        return new BlobServiceClientBuilder()
                .connectionString(azureStorageConnectionString)
                .buildClient()
                .getBlobContainerClient(azureStorageContainerName);
    }

    private String generateUniqueFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        return UUID.randomUUID() + extension;
    }

    private String getBlobNameFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
    }
}

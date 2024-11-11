package B1G4.bookmark.service.S3Service;

import org.springframework.web.multipart.MultipartFile;

public interface S3ImageService {
    String upload(MultipartFile image);
    void deleteImageFromS3(String imageAddress);
}

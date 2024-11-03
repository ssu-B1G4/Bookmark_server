package B1G4.bookmark.service.ReviewService;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewImgService {
    public void uploadImage(Long reviewId, List<MultipartFile> images);
}
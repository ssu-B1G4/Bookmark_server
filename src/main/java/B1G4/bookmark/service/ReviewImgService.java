package B1G4.bookmark.service;

import B1G4.bookmark.domain.Review;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewImgService {
    public void uploadImage(Review review, List<MultipartFile> images);
}
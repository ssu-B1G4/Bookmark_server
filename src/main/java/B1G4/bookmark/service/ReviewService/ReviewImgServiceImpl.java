package B1G4.bookmark.service.ReviewService;

import B1G4.bookmark.domain.Review;
import B1G4.bookmark.domain.ReviewImg;
import B1G4.bookmark.repository.ReviewImgRepository;
import B1G4.bookmark.repository.ReviewRepository;
import B1G4.bookmark.service.S3Service.S3ImageService;
import B1G4.bookmark.service.S3Service.S3ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewImgServiceImpl implements ReviewImgService {

    private final ReviewImgRepository reviewImgRepository;
    private final ReviewRepository reviewRepository;
    private final S3ImageService s3ImageService;

    // 이미지 저장
    public void uploadImage(Long reviewId, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) return;

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 리뷰 없음"));

        images.forEach(image -> {
            if (!image.isEmpty()) {
                try {
                    // S3에 이미지 업로드 후 URL 반환
                    String s3Url = s3ImageService.upload(image);

                    // ReviewImg 엔티티 생성 및 DB 저장
                    ReviewImg reviewImg = ReviewImg.builder()
                            .review(review)
                            .url(s3Url)
                            .build();

                    reviewImgRepository.save(reviewImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

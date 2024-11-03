package B1G4.bookmark.service.ReviewService;

import B1G4.bookmark.domain.Review;
import B1G4.bookmark.domain.ReviewImg;
import B1G4.bookmark.repository.ReviewImgRepository;
import B1G4.bookmark.repository.ReviewRepository;
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

    // 이미지 저장
    public void uploadImage(Long reviewId, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) return;

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 리뷰 없음"));

        // 이미지 파일 저장하는 경로 -> 나중에 s3로 바꿈
        String uploadsDir = "src/main/resources/static/uploads/reviewImgs/";

        images.forEach(image -> {
            if (!image.isEmpty()) {
                try {
                    // 이미지 파일 저장 후 경로 반환
                    String dbFilePath = saveImage(image, uploadsDir);

                    // ReviewImg 엔티티 생성 및 DB 저장
                    ReviewImg reviewImg = ReviewImg.builder()
                            .review(review)
                            .url(dbFilePath)
                            .build();

                    reviewImgRepository.save(reviewImg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 이미지 파일을 저장하는 메서드
    private String saveImage(MultipartFile image, String uploadsDir) throws IOException {
        // 고유한 파일 이름 생성
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        // 경로를 절대 경로로 변환
        Path uploadsPath = Paths.get(uploadsDir).toAbsolutePath();

        // 디렉토리가 존재하지 않으면 생성
        if (!Files.exists(uploadsPath)) {
            Files.createDirectories(uploadsPath);
        }

        // 파일 전체 경로 생성
        Path filePath = uploadsPath.resolve(fileName);
        String dbFilePath = "/uploads/reviewImgs/" + fileName;

        // 파일 저장
        File file = filePath.toFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(image.getBytes());
        }

        return dbFilePath;
    }
}

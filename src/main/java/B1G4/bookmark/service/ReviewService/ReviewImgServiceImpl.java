package B1G4.bookmark.service.ReviewService;

import B1G4.bookmark.domain.Review;
import B1G4.bookmark.domain.ReviewImg;
import B1G4.bookmark.repository.ReviewImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    // 이미지 저장
    public void uploadImage(Review review, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) return;
        try {
            // 이미지 파일 저장하는 경로 -> 나중에 s3로 바꿈
            String uploadsDir = "src/main/resources/static/uploads/reviewImgs/";

            // 각 이미지 파일에 대해 업로드 및 DB 저장
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    // 이미지 파일 경로 저장
                    String dbFilePath = saveImage(image, uploadsDir);

                    // ReviewImg 엔티티 생성 및 저장
                    ReviewImg reviewImg = new ReviewImg(review, dbFilePath);
                    reviewImgRepository.save(reviewImg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 이미지 파일을 저장하는 메서드
    private String saveImage(MultipartFile image, String uploadsDir) throws IOException {
        // 파일 이름 생성
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        // 실제 파일이 저장될 경로
        String filePath = uploadsDir + fileName;
        // DB에 저장될 경로 문자열
        String dbFilePath = "/uploads/reviewImgs/" + fileName;

        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        return dbFilePath;
    }
}

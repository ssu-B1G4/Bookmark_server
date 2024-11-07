package B1G4.bookmark.service.ReportPlaceService;

import B1G4.bookmark.converter.ReportPlaceConverter;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.ReportPlace;
import B1G4.bookmark.repository.ReportPlaceRepository;
import B1G4.bookmark.web.dto.ReportPlaceDTO.ReportPlaceRequestDTO;
import B1G4.bookmark.web.dto.ReportPlaceDTO.ReportPlaceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportPlaceServiceImpl implements ReportPlaceService {

    private final ReportPlaceRepository reportPlaceRepository;

    public ReportPlaceResponseDTO createReportPlace(ReportPlaceRequestDTO requestDTO, List<MultipartFile> images, Member member) {
        List<String> imageUrls = uploadImages(images);

        ReportPlace reportPlace = ReportPlaceConverter.toEntity(requestDTO, imageUrls, member);

        reportPlaceRepository.save(reportPlace);

        return new ReportPlaceResponseDTO(reportPlace.getId());
    }

    public List<String> uploadImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return List.of(); // 이미지가 없으면 빈 리스트 반환
        }

        // 이미지 파일 저장 경로 (나중에 s3로 변경)
        String uploadsDir = "src/main/resources/static/uploads/reportImgs/";

        List<String> imageUrls = new ArrayList<>();

        images.forEach(image -> {
            if (!image.isEmpty()) {
                try {
                    String dbFilePath = saveImage(image, uploadsDir);
                    imageUrls.add(dbFilePath); // URL 추가
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return imageUrls;
    }

    private String saveImage(MultipartFile image, String uploadsDir) throws IOException {
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        Path uploadsPath = Paths.get(uploadsDir).toAbsolutePath();

        if (!Files.exists(uploadsPath)) {
            Files.createDirectories(uploadsPath);
        }

        Path filePath = uploadsPath.resolve(fileName);
        String dbFilePath = "/uploads/reportImgs/" + fileName;

        File file = filePath.toFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(image.getBytes());
        }

        return dbFilePath;
    }
}
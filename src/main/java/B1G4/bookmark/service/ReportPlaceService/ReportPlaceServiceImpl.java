package B1G4.bookmark.service.ReportPlaceService;

import B1G4.bookmark.converter.ReportPlaceConverter;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.ReportPlace;
import B1G4.bookmark.repository.ReportPlaceRepository;
import B1G4.bookmark.service.S3Service.S3ImageServiceImpl;
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
    private final S3ImageServiceImpl s3ImageService;

    public ReportPlaceResponseDTO createReportPlace(ReportPlaceRequestDTO requestDTO, List<MultipartFile> images, Member member) {
        List<String> imageUrls = uploadImages(images);

        ReportPlace reportPlace = ReportPlaceConverter.toEntity(requestDTO, imageUrls, member);

        reportPlaceRepository.save(reportPlace);

        return new ReportPlaceResponseDTO(reportPlace.getId());
    }

    public List<String> uploadImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return List.of();
        }

        List<String> imageUrls = new ArrayList<>();

        images.forEach(image -> {
            if (!image.isEmpty()) {
                try {
                    String s3Url = s3ImageService.upload(image);
                    imageUrls.add(s3Url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return imageUrls;
    }
}
package cyou.noteit.image_server.domain.image.service;

import cyou.noteit.image_server.domain.image.dto.request.ImageUploadRequestDTO;
import cyou.noteit.image_server.domain.image.dto.response.ImageUploadResponseDTO;
import cyou.noteit.image_server.global.base.exception.CustomException;
import cyou.noteit.image_server.global.base.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService{

    private static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/image";

    @Value("${IMAGE_API}")
    private String IMAGE_API;

    @Value("${DOMAIN}")
    private String DOMAIN;

    @Override
    public ResponseEntity<ImageUploadResponseDTO> upload(ImageUploadRequestDTO imageUploadRequestDTO, MultipartFile file) throws IOException {
        if(!imageUploadRequestDTO.getApi().equals(IMAGE_API))
            throw new CustomException(ErrorCode.INVALID_API_KEY);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        String fileName = LocalDateTime.now().format(formatter) + UUID.randomUUID() + "."+ extension;

        // 업로드 디렉토리 생성
        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 저장
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation);

        return ResponseEntity.ok(
                ImageUploadResponseDTO.builder()
                        .url(DOMAIN + "/image/display/" + fileName)
                        .build()
        );
    }

    @Override
    public ResponseEntity<Resource> getImage(String imagePath) throws MalformedURLException {
        Path filePath = Paths.get(uploadDirectory).resolve(imagePath).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return ResponseEntity.ok(resource);
        } else {
            throw new CustomException(ErrorCode.IMAGE_NOT_FOUND);
        }
    }
}

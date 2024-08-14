package cyou.noteit.image_server.domain.image.service;

import cyou.noteit.image_server.domain.image.dto.request.ImageUploadRequestDTO;
import cyou.noteit.image_server.domain.image.dto.response.ImageUploadResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface ImageService {

    ResponseEntity<ImageUploadResponseDTO> upload(ImageUploadRequestDTO imageUploadRequestDTO, MultipartFile file) throws IOException;

    ResponseEntity<Resource> getImage(String imagePath) throws MalformedURLException;
}

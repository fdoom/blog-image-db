package cyou.noteit.image_server.domain.image.controller;

import cyou.noteit.image_server.domain.image.dto.request.ImageUploadRequestDTO;
import cyou.noteit.image_server.domain.image.dto.response.ImageUploadResponseDTO;
import cyou.noteit.image_server.domain.image.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageUploadResponseDTO> upload(
            @RequestPart @Valid ImageUploadRequestDTO imageUploadRequestDTO,
            @RequestPart MultipartFile file
    ) throws IOException {
        return imageService.upload(imageUploadRequestDTO, file);

    }

    @GetMapping(
            value = {"/display/{imagePath}"},
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE}
    )
    public ResponseEntity<Resource> getImage(@PathVariable String imagePath) throws MalformedURLException {
        return imageService.getImage(imagePath);
    }
}

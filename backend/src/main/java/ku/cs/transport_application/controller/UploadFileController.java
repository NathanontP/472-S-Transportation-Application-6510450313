package ku.cs.transport_application.controller;

import ku.cs.transport_application.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
public class UploadFileController {

    @Autowired
    private FileService fileService;

    private static final String BASE_IMAGE_PATH = "src/main/resources/static/images";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestPart("orderId") UUID orderId, @RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(Map.of("error", "File is empty"), HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;

            if (!fileName.endsWith(".pdf")) {
                return new ResponseEntity<>(Map.of("error", "Only PDF files are allowed"), HttpStatus.BAD_REQUEST);
            }

            fileService.uploadFile(orderId, file);

            return new ResponseEntity<>(Map.of("message", "File uploaded successfully", "fileName", fileName), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(Map.of("error", "Failed to upload file"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images/users/{filename:.+}")
    public ResponseEntity<Resource> getUserImage(@PathVariable String filename) {
        return serveImage("users", filename);
    }

    @GetMapping("/images/workers/{filename:.+}")
    public ResponseEntity<Resource> getWorkerImage(@PathVariable String filename) {
        return serveImage("workers", filename);
    }

    @GetMapping("/images/uploads/{filename:.+}")
    public ResponseEntity<Resource> getUploadImage(@PathVariable String filename) {
        return serveImage("uploads", filename);
    }

    private ResponseEntity<Resource> serveImage(String folder, String filename) {
        try {
            if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
                throw new IllegalArgumentException("Invalid filename");
            }

            String filePath = Paths.get(BASE_IMAGE_PATH, folder, filename).toString();
            File file = new File(filePath);

            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Resource resource = new FileSystemResource(file);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

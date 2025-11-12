package com.example.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.example.entity.s3.S3ZipDownloadRequest;
import com.example.service.S3ZipDownloadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/s3")
@Validated
public class S3ZipDownloadController {

    private static final String DEFAULT_ARCHIVE_NAME = "archive.zip";

    private final S3ZipDownloadService s3ZipDownloadService;

    public S3ZipDownloadController(S3ZipDownloadService s3ZipDownloadService) {
        this.s3ZipDownloadService = s3ZipDownloadService;
    }

    @PostMapping("/zip")
    public ResponseEntity<StreamingResponseBody> downloadAsZip(@Valid @RequestBody S3ZipDownloadRequest request) {
        List<String> keys = request.getObjectKeys();

        // StreamingResponseBody を利用してレスポンスを逐次書き込み、大量ファイルでもメモリフットプリントを抑える。
        StreamingResponseBody responseBody = outputStream -> s3ZipDownloadService.streamObjectsAsZip(keys, outputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/zip"));
        headers.setContentDisposition(createContentDisposition(request.getArchiveFileName()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseBody);
    }

    private ContentDisposition createContentDisposition(String requestedName) {
        String fileName = StringUtils.hasText(requestedName) ? requestedName : DEFAULT_ARCHIVE_NAME;
        return ContentDisposition.attachment()
                .filename(fileName, StandardCharsets.UTF_8)
                .build();
    }
}

package com.example.service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.config.S3Properties;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Service
public class S3ZipDownloadService {

    private static final int DEFAULT_BUFFER_SIZE = 16 * 1024;

    private final S3Client s3Client;
    private final S3Properties properties;

    public S3ZipDownloadService(S3Client s3Client, S3Properties properties) {
        this.s3Client = s3Client;
        this.properties = properties;
    }

    public void streamObjectsAsZip(List<String> objectKeys, OutputStream outputStream) {
        if (objectKeys == null || objectKeys.isEmpty()) {
            throw new IllegalArgumentException("objectKeys must not be null or empty");
        }

        if (!StringUtils.hasText(properties.getBucket())) {
            throw new IllegalStateException("S3 bucket name is not configured");
        }

        // ここでレスポンスの OutputStream を ZIP 用にラップし、逐次的に書き込むことで
        // 大容量ファイルでもメモリ消費を抑えたストリーミング処理にしている。
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream))) {
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

            for (String key : objectKeys) {
                if (!StringUtils.hasText(key)) {
                    throw new IllegalArgumentException("object key must not be blank");
                }

                writeObjectToZip(zipOutputStream, buffer, key.trim());
            }

            // finish を呼び出して ZIP のフッタを書き込み、クライアント側で解凍できるようにする。
            zipOutputStream.finish();
            zipOutputStream.flush();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to stream zip archive", e);
        }
    }

    private void writeObjectToZip(ZipOutputStream zipOutputStream, byte[] buffer, String key) {
        // ZIP 内の 1 エントリを生成しつつ、S3 からのレスポンスを逐次読み取って書き込む。
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build();

        boolean entryStarted = false;

        try (ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request)) {
            ZipEntry zipEntry = new ZipEntry(key);
            zipOutputStream.putNextEntry(zipEntry);
            entryStarted = true;

            int read;
            while ((read = response.read(buffer)) != -1) {
                // S3 から読み込んだチャンクを即座に ZIP に書き出し、ダウンロードをストリーム処理する。
                zipOutputStream.write(buffer, 0, read);
            }
        } catch (NoSuchKeyException e) {
            throw new IllegalArgumentException("Object not found in S3: " + key, e);
        } catch (SdkClientException e) {
            throw new IllegalStateException("Failed to download object from S3: " + key, e);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write object to zip: " + key, e);
        } finally {
            if (entryStarted) {
                try {
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    throw new UncheckedIOException("Failed to close zip entry for object: " + key, e);
                }
            }
        }
    }
}

package com.example.entity.s3;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public class S3ZipDownloadRequest {

    // まとめて取得する S3 オブジェクトキーのリスト。空リクエストはバリデーションで弾く。
    @NotEmpty
    private List<String> objectKeys;

    // ダウンロード時の ZIP ファイル名（任意指定）。
    private String archiveFileName;

    public List<String> getObjectKeys() {
        return objectKeys;
    }

    public void setObjectKeys(List<String> objectKeys) {
        this.objectKeys = objectKeys;
    }

    public String getArchiveFileName() {
        return archiveFileName;
    }

    public void setArchiveFileName(String archiveFileName) {
        this.archiveFileName = archiveFileName;
    }
}

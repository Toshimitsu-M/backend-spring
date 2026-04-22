package com.example.domain;

import lombok.Data;

/**
 * Favorite
 * ユーザーのキャラクターお気に入り情報ドメインオブジェクト
 */
@Data
public class Favorite {
    private String userId;
    private String characterId;
    private String characterName;
    private String characterImage;
    private String createdAt;
}

package com.example.entity.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import lombok.*;

/**
 * DynamoFavorite
 * ユーザーのキャラクターお気に入り情報を管理するDynamoDBエンティティ
 */
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DynamoFavorite {

    private String userId;
    private String characterId;
    private String characterName;
    private String characterImage;
    private String createdAt;

    // ========= テーブルの主キー =========
    @DynamoDbPartitionKey
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDbSortKey
    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    // ========= 属性 =========
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

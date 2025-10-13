package com.example.entity.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import lombok.*;


@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DynamoCharacterComment {

    private Long commentId;
    private String updatedAt;
    private String characterId;
    private String userId;
    private String commentText;
    private String gsiPk;

    // ========= テーブルの主キー =========
    @DynamoDbPartitionKey
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @DynamoDbSortKey
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ========= 属性（キャラ・ユーザーIDなど） =========
    @DynamoDbSecondaryPartitionKey(indexNames = {"GSI3_Character"})
    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {"GSI2_User"})
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    // ========= GSI: characterId 単体検索用 =========
    
    public String getCharacterIdForGsi() {
        return characterId;
    }

    public void setCharacterIdForGsi(String characterId) {
        this.characterId = characterId;
    }

    // ========= GSI: userId 単体検索用 =========
    
    public String getUserIdForGsi() {
        return userId;
    }

    public void setUserIdForGsi(String userId) {
        this.userId = userId;
    }

    // ========= GSI: characterId#userId 検索用 =========
    @DynamoDbSecondaryPartitionKey(indexNames = {"GSI_CharacterUser"})
    public String getGsiPk() {
        return gsiPk;
    }

    public void setGsiPk(String gsiPk) {
        this.gsiPk = gsiPk;
    }
}


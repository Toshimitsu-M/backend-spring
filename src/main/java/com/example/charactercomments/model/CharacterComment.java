package com.example.charactercomments.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import lombok.*;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterComment {

    private String commentId;
    private String updatedAt;
    private String characterId;
    private String userId;
    private String commentText;
    private String gsiPk;

    @DynamoDbPartitionKey
    public String getCommentId() { return commentId; }

    @DynamoDbSortKey
    public String getUpdatedAt() { return updatedAt; }

    @DynamoDbSecondaryPartitionKey(indexNames = "GSI1")
    public String getGsiPk() { return gsiPk; }
}


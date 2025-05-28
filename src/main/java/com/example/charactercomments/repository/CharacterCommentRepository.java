package com.example.charactercomments.repository;

import com.example.charactercomments.model.CharacterComment;

import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CharacterCommentRepository {

    private final DynamoDbTable<CharacterComment> table;

    // コンストラクタ
    public CharacterCommentRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("characterComments", TableSchema.fromBean(CharacterComment.class));
    }

    // 保存・更新
    public CharacterComment save(CharacterComment comment) {
        table.putItem(comment);
        return comment;
    }

    // キャラクターIDで取得
    public List<CharacterComment> findByCharacterId(String characterId) {
        DynamoDbIndex<CharacterComment> index = table.index("GSI3_Character");
        QueryConditional condition = QueryConditional.keyEqualTo(Key.builder().partitionValue(characterId).build());
        SdkIterable<Page<CharacterComment>> results = index.query(r -> r.queryConditional(condition));
        List<CharacterComment> comments = new java.util.ArrayList<>();
        for (Page<CharacterComment> page : results) {
            comments.addAll(page.items());
        }
        return comments;

    }

    // ユーザーIDで取得
    public List<CharacterComment> findByUserId(String userId) {
        DynamoDbIndex<CharacterComment> index = table.index("GSI2_User");
        QueryConditional condition = QueryConditional.keyEqualTo(Key.builder().partitionValue(userId).build());
        SdkIterable<Page<CharacterComment>> results = index.query(r -> r.queryConditional(condition));
        List<CharacterComment> comments = new java.util.ArrayList<>();
        for (Page<CharacterComment> page : results) {
            comments.addAll(page.items());
        }
        return comments;
    }

    // キャラクターIDとユーザーIDで取得
    public List<CharacterComment> findByCharacterIdAndUserId(String characterId, String userId) {
        String gsiPk = characterId + "#" + userId;
        DynamoDbIndex<CharacterComment> index = table.index("GSI1_CharacterUser");
        QueryConditional condition = QueryConditional.keyEqualTo(Key.builder().partitionValue(gsiPk).build());
        SdkIterable<Page<CharacterComment>> results = index.query(r -> r.queryConditional(condition));
        List<CharacterComment> comments = new java.util.ArrayList<>();
        for (Page<CharacterComment> page : results) {
            comments.addAll(page.items());
        }
        return comments;
    }

    // キャラクターコメントIDで削除
    public void deleteById(String id) {
        table.deleteItem(Key.builder().partitionValue(id).build());
    }
}

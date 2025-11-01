package com.example.repository.dynamodb;

import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.example.domain.CharacterComment;
import com.example.entity.dynamodb.DynamoCharacterComment;
import com.example.repository.CharacterCommentRepository;
import com.example.service.SequenceService;

@Repository
@ConditionalOnProperty(name = "db.type", havingValue = "dynamodb")
public class DynamoDBCharacterCommentRepository implements CharacterCommentRepository {

    private final DynamoDbTable<DynamoCharacterComment> table;
    private final SequenceService sequenceService;

    // コンストラクタ
    public DynamoDBCharacterCommentRepository(DynamoDbEnhancedClient enhancedClient, SequenceService sequenceService) {
        this.table = enhancedClient.table("characterComments", TableSchema.fromBean(DynamoCharacterComment.class));
        this.sequenceService = sequenceService;
    }

    @Override
    public List<CharacterComment> selectAll() {
        // 実装内容を書く
        return new ArrayList<>(); // とりあえず仮実装
    }

    // キャラクターIDで取得
    @Override
    public List<CharacterComment> selectByCharacterId(String characterId) {
        DynamoDbIndex<DynamoCharacterComment> index = table.index("GSI3_Character");
        QueryConditional condition = QueryConditional.keyEqualTo(Key.builder().partitionValue(characterId).build());
        SdkIterable<Page<DynamoCharacterComment>> results = index.query(r -> r.queryConditional(condition));
        List<CharacterComment> comments = new java.util.ArrayList<>();
        for (Page<DynamoCharacterComment> page : results) {
            page.items().forEach(dynamoComment -> {
                CharacterComment comment = new CharacterComment();
                comment.setId(dynamoComment.getCommentId());
                comment.setAnilistId(dynamoComment.getCharacterId());
                comment.setUserId(dynamoComment.getUserId());
                comment.setComment(dynamoComment.getCommentText());
                comments.add(comment);
            });
        }
        return comments;

    }

    // ユーザーIDで取得
    @Override
    public List<CharacterComment> selectByUserId(String userId) {
        DynamoDbIndex<DynamoCharacterComment> index = table.index("GSI2_User");
        QueryConditional condition = QueryConditional.keyEqualTo(Key.builder().partitionValue(userId).build());
        SdkIterable<Page<DynamoCharacterComment>> results = index.query(r -> r.queryConditional(condition));
        List<CharacterComment> comments = new java.util.ArrayList<>();
        for (Page<DynamoCharacterComment> page : results) {
            page.items().forEach(dynamoComment -> {
                CharacterComment comment = new CharacterComment();
                comment.setId(dynamoComment.getCommentId());
                comment.setAnilistId(dynamoComment.getCharacterId());
                comment.setUserId(dynamoComment.getUserId());
                comment.setComment(dynamoComment.getCommentText());
                comments.add(comment);
            });
        }
        return comments;
    }

    // キャラクターIDとユーザーIDで取得
    @Override
    public List<CharacterComment> selectByCharacterIdAndUserId(String characterId, String userId) {
        String gsiPk = characterId + "#" + userId;
        DynamoDbIndex<DynamoCharacterComment> index = table.index("GSI1_CharacterUser");
        QueryConditional condition = QueryConditional.keyEqualTo(Key.builder().partitionValue(gsiPk).build());
        SdkIterable<Page<DynamoCharacterComment>> results = index.query(r -> r.queryConditional(condition));
        List<CharacterComment> comments = new java.util.ArrayList<>();
        for (Page<DynamoCharacterComment> page : results) {
            page.items().forEach(dynamoComment -> {
                CharacterComment comment = new CharacterComment();
                comment.setId(dynamoComment.getCommentId());
                comment.setAnilistId(dynamoComment.getCharacterId());
                comment.setUserId(dynamoComment.getUserId());
                comment.setComment(dynamoComment.getCommentText());
                comments.add(comment);
            });
        }
        return comments;
    }

    // コメントの追加・更新
    @Override
    public void save(CharacterComment comment) {
        // コメントシーケンス取得 UUIDにするか検討中 comment.setCommentId(UUID.randomUUID().toString());
        sequenceService.getNextSequence("commentId");
        DynamoCharacterComment dynamoComment = new DynamoCharacterComment();
        dynamoComment.setCommentId(sequenceService.getNextSequence("commentId"));
        dynamoComment.setCharacterId(comment.getAnilistId());
        dynamoComment.setUserId(comment.getUserId());
        dynamoComment.setCommentText(comment.getComment());
        dynamoComment.setGsiPk(comment.getAnilistId() + "#" + comment.getUserId());
        table.putItem(dynamoComment);

    }

    // キャラクターコメントIDで削除
    @Override
    public void deleteById(Long id) {
        table.deleteItem(Key.builder().partitionValue(id.toString()).build());
    }
}

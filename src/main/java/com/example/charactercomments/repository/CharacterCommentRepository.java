package com.example.charactercomments.repository;

import com.example.charactercomments.model.CharacterComment;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import org.springframework.stereotype.Repository;

@Repository
public class CharacterCommentRepository  {
    
    private final DynamoDbTable<CharacterComment> table;

    //コンストラクタ
    public CharacterCommentRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("characterComments", TableSchema.fromBean(CharacterComment.class));
    }

    //保存・更新
    public CharacterComment save(CharacterComment comment) {
        table.putItem(comment);
        return comment;
    }

    //キャラクターIDで取得
    public CharacterComment findById(String id) {
        return table.getItem(Key.builder().partitionValue(id).build());
    }

    //キャラクターコメントIDで削除
    public void deleteById(String id) {
        table.deleteItem(Key.builder().partitionValue(id).build());
    }
}

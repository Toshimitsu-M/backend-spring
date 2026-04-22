package com.example.repository.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.domain.Favorite;
import com.example.entity.dynamodb.DynamoFavorite;
import com.example.repository.FavoriteRepository;

@Repository
public class DynamoDBFavoriteRepository implements FavoriteRepository {

    private final DynamoDbTable<DynamoFavorite> table;

    // コンストラクタ
    public DynamoDBFavoriteRepository(DynamoDbTable<DynamoFavorite> table) {
        this.table = table;
    }

    // お気に入り追加
    @Override
    public void save(String userId, String characterId, String characterName, String characterImage) {
        DynamoFavorite favorite = new DynamoFavorite();
        favorite.setUserId(userId);
        favorite.setCharacterId(characterId);
        favorite.setCharacterName(characterName);
        favorite.setCharacterImage(characterImage);
        favorite.setCreatedAt(Instant.now().toString());
        table.putItem(favorite);
    }

    // お気に入り削除
    @Override
    public void deleteById(String userId, Long characterId) {
        Key key = Key.builder()
                .partitionValue(userId)
                .sortValue(String.valueOf(characterId))
                .build();
        table.deleteItem(key);
    }

    // ユーザーIDでお気に入り一覧取得
    @Override
    public List<Favorite> findByUserId(String userId) {
        QueryConditional condition = QueryConditional.keyEqualTo(Key.builder().partitionValue(userId).build());
        List<Favorite> favorites = new ArrayList<>();
        table.query(r -> r.queryConditional(condition))
                .items()
                .forEach(dynamoFavorite -> {
                    Favorite favorite = new Favorite();
                    favorite.setUserId(dynamoFavorite.getUserId());
                    favorite.setCharacterId(dynamoFavorite.getCharacterId());
                    favorite.setCharacterName(dynamoFavorite.getCharacterName());
                    favorite.setCharacterImage(dynamoFavorite.getCharacterImage());
                    favorite.setCreatedAt(dynamoFavorite.getCreatedAt());
                    favorites.add(favorite);
                });
        return favorites;
    }
}

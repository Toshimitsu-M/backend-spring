package com.example.service;

import com.example.domain.Favorite;
import com.example.repository.FavoriteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    // DI
    private final FavoriteRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);

    public FavoriteService(FavoriteRepository repository) {
        this.repository = repository;
    }

    // お気に入り追加
    public void addFavorite(String userId, Long characterId, String characterName, String characterImage) {
        logger.info("Adding favorite for userId: {}, characterId: {}", userId, characterId);
        repository.save(userId, String.valueOf(characterId), characterName, characterImage);
    }

    // お気に入り削除
    public void removeFavorite(String userId, Long characterId) {
        logger.info("Removing favorite for userId: {}, characterId: {}", userId, characterId);
        repository.deleteById(userId, characterId);
    }

    // お気に入り一覧取得
    public List<Favorite> getFavorites(String userId) {
        logger.info("Fetching favorites for userId: {}", userId);
        return repository.findByUserId(userId);
    }
}

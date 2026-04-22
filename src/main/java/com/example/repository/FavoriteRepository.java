package com.example.repository;

import java.util.List;

import com.example.domain.Favorite;

public interface FavoriteRepository {
    void save(String userId, String characterId, String characterName, String characterImage);
    void deleteById(String userId, Long characterId);
    List<Favorite> findByUserId(String userId);
}

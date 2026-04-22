package com.example.controller;

import com.example.domain.Favorite;
import com.example.service.FavoriteService;
import com.google.firebase.auth.FirebaseToken;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * FavoriteController
 * ユーザーのキャラクターお気に入り管理API
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/favorite")
@CrossOrigin(origins = "*") // CORS設定
public class FavoriteController {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    // DI
    private final FavoriteService service;

    record AddFavoriteRequest(String characterName, String characterImage) {}

    // お気に入り追加
    @PostMapping("/{characterId}")
    public ResponseEntity<?> addFavorite(@PathVariable Long characterId,
                                         @RequestBody AddFavoriteRequest body,
                                         HttpServletRequest request) {
        FirebaseToken firebaseUser = (FirebaseToken) request.getAttribute("firebaseUser");
        String userId = firebaseUser.getUid();
        logger.info("お気に入り追加リクエスト userId: {}, characterId: {}", userId, characterId);
        service.addFavorite(userId, characterId, body.characterName(), body.characterImage());
        return ResponseEntity.ok("Favorite added successfully");
    }

    // お気に入り削除
    @DeleteMapping("/{characterId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Long characterId, HttpServletRequest request) {
        FirebaseToken firebaseUser = (FirebaseToken) request.getAttribute("firebaseUser");
        String userId = firebaseUser.getUid();
        logger.info("お気に入り削除リクエスト userId: {}, characterId: {}", userId, characterId);
        service.removeFavorite(userId, characterId);
        return ResponseEntity.ok("Favorite removed successfully");
    }

    // お気に入り一覧取得
    @GetMapping
    public List<Favorite> getFavorites(HttpServletRequest request) {
        FirebaseToken firebaseUser = (FirebaseToken) request.getAttribute("firebaseUser");
        String userId = firebaseUser.getUid();
        logger.info("お気に入り一覧取得リクエスト userId: {}", userId);
        return service.getFavorites(userId);
    }
}

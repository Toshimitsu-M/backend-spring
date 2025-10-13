package com.example.service;

import com.example.domain.CharacterComment;
import com.example.repository.CharacterCommentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterCommentService {

    // DI
    private final CharacterCommentRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(CharacterCommentService.class);

    public CharacterCommentService(CharacterCommentRepository repository) {
        this.repository = repository;
    }

    // キャラクターIDでコメントリスト取得
    public List<CharacterComment> selectByCharacterId(String characterId) {
        logger.info("Fetching comments for characterId: {}", characterId);
        return repository.selectByCharacterId(characterId);
    }

    // コメントの追加、編集
    public void save(CharacterComment comment) {
        repository.save(comment);
    }

    // コメント削除
    public void deleteByPrimaryKey(Long id) {
        repository.deleteById(id);
    }

}

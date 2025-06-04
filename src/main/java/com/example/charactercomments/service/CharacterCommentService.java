package com.example.charactercomments.service;

import com.example.charactercomments.controller.CharacterCommentController;
import com.example.charactercomments.model.CharacterComment;
import com.example.charactercomments.repository.CharacterCommentRepository;
import com.example.sequence.service.SequenceService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CharacterCommentService {
    // DI
    private final CharacterCommentRepository repository;
    private final SequenceService sequenceService;

    private static final Logger logger = LoggerFactory.getLogger(CharacterCommentController.class);

    // キャラクターIDでコメントリスト取得
    public List<CharacterComment> selectByCharacterId(String characterId) {

        logger.info("Fetching comments for characterId: {}", characterId);
        CharacterComment characterComment = new CharacterComment();
        characterComment.setCharacterId(characterId);
        return repository.findByCharacterId(characterComment);
    }

    // コメントの追加、編集
    public CharacterComment save(CharacterComment comment) {
        // コメントシーケンス取得　UUIDにするか検討中 comment.setCommentId(UUID.randomUUID().toString());
        comment.setCommentId(sequenceService.getNextSequence("commentId"));
        comment.setUpdatedAt(Instant.now().toString());
        comment.setGsiPk(comment.getCharacterId() + "#" + comment.getUserId());
        return repository.save(comment);
    }

    // コメント削除
    public void deleteByPrimaryKey(Long id) {
        
        repository.deleteById(id.toString());
    }

}

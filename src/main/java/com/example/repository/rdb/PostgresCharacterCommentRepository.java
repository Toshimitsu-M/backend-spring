package com.example.repository.rdb;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.CharacterComment;
import com.example.entity.rdb.RdbCharacterComment;
import com.example.mapper.postgres.mapper.PostgresCharacterCommentMapper;
import com.example.repository.CharacterCommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
@ConditionalOnProperty(name = "db.type", havingValue = "postgresql")
public class PostgresCharacterCommentRepository implements CharacterCommentRepository {
    private final PostgresCharacterCommentMapper mapper;

    // キャラクターIDで取得
    @Override
    public List<CharacterComment> selectByCharacterId(String characterId) {
        List<RdbCharacterComment> comments = mapper.selectByCharacterId(characterId);
        return comments.stream().map(rdbComment -> {
            CharacterComment comment = new CharacterComment();
            comment.setId(rdbComment.getId());
            comment.setAnilistId(rdbComment.getAnilistId());
            comment.setUserId(rdbComment.getUserId());
            comment.setComment(rdbComment.getComment());
            return comment;
        }).toList();

    }

    // ユーザーIDで取得
    @Override
    public List<CharacterComment> selectByUserId(String userId) {
        List<RdbCharacterComment> comments = mapper.selectByUserId(userId);
        return comments.stream().map(rdbComment -> {
            CharacterComment comment = new CharacterComment();
            comment.setId(rdbComment.getId());
            comment.setAnilistId(rdbComment.getAnilistId());
            comment.setUserId(rdbComment.getUserId());
            comment.setComment(rdbComment.getComment());
            return comment;
        }).toList();
    }

    // キャラクターIDとユーザーIDで取得
    @Override
    public List<CharacterComment> selectByCharacterIdAndUserId(String characterId, String userId) {
        List<RdbCharacterComment> comments = mapper.selectByCharacterIdAndUserId(characterId, userId);
        return comments.stream().map(rdbComment -> {
            CharacterComment comment = new CharacterComment();
            comment.setId(rdbComment.getId());
            comment.setAnilistId(rdbComment.getAnilistId());
            comment.setUserId(rdbComment.getUserId());
            comment.setComment(rdbComment.getComment());
            return comment;
        }).toList();
    }

    // 全件取得
    public List<CharacterComment> selectAll() {
        List<RdbCharacterComment> comments = mapper.selectAll();
        return comments.stream().map(rdbComment -> {
            CharacterComment comment = new CharacterComment();
            comment.setId(rdbComment.getId());
            comment.setAnilistId(rdbComment.getAnilistId());
            comment.setUserId(rdbComment.getUserId());
            comment.setComment(rdbComment.getComment());
            return comment;
        }).toList();
    }

    // コメントの追加・更新
    @Override
    public void save(CharacterComment comment) {
        RdbCharacterComment rdbComment = new RdbCharacterComment();
        rdbComment.setId(comment.getId());
        rdbComment.setAnilistId(comment.getAnilistId());
        rdbComment.setUserId(comment.getUserId());
        rdbComment.setComment(comment.getComment());
        // コメントID情報取得
        int count = mapper.selectByCommentId(rdbComment.getId());

        if (count == 0) {
            mapper.insert(rdbComment);
        } else {
            mapper.updateByPrimaryKey(rdbComment);
        }
    }

    // キャラクターコメントIDで削除
    @Override
    public void deleteById(Long id) {
        mapper.deleteByPrimaryKey(id);
    }
}

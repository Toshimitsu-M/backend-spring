package com.example.charactercomments.service;

import com.example.charactercomments.model.CharacterComment;
import com.example.charactercomments.repository.CharacterCommentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CharacterCommentService {
    private final CharacterCommentRepository repository;

    public CharacterCommentService(CharacterCommentRepository repository) {
        this.repository = repository;
    }

    public CharacterComment createComment(CharacterComment comment) {
        comment.setUpdatedAt(Instant.now().toString());
        comment.setGsiPk(comment.getCharacterId() + "#" + comment.getUserId());
        return repository.save(comment);
    }

    public List<CharacterComment> getCommentsByCharacterAndUser(String characterId, String userId) {
        return repository.findByGsiPk(characterId + "#" + userId);
    }

    public Iterable<CharacterComment> getAll() {
        return repository.findAll();
    }
}

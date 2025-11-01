package com.example.repository;

import java.util.List;

import com.example.domain.CharacterComment;

public interface CharacterCommentRepository {
    public List<CharacterComment> selectByCharacterId(String characterId);
    public List<CharacterComment> selectByUserId(String userId);
    public List<CharacterComment> selectByCharacterIdAndUserId(String characterId, String userId);
    public List<CharacterComment> selectAll();
    // public Integer getNextSequence();
    public void save(CharacterComment characterComment);
    public void deleteById(Long id);
}

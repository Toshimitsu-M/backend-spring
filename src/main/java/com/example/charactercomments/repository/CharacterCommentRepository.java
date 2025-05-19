package com.example.charactercomments.repository;

import com.example.charactercomments.model.CharacterComment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface CharacterCommentRepository extends CrudRepository<CharacterComment, String> {
    List<CharacterComment> findByGsiPk(String gsiPk);
}


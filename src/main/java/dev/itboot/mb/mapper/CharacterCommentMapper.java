package dev.itboot.mb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import dev.itboot.mb.model.CharacterComment;

@Mapper
public interface CharacterCommentMapper {
    List<CharacterComment> selectByCharacterId(String anilistId);

    Integer getNextSequence();

    int selectByCommentId(Long id);
    
    int insert(CharacterComment record);

    int updateByPrimaryKey(CharacterComment record);

    int deleteByPrimaryKey(Long id);
}

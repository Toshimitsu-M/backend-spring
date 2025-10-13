package com.example.mapper.postgres.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.entity.rdb.RdbCharacterComment;

@Mapper
public interface PostgresCharacterCommentMapper {
    List<RdbCharacterComment> selectByCharacterId(String anilistId);

    List<RdbCharacterComment> selectByUserId(String userId);
    List<RdbCharacterComment> selectByCharacterIdAndUserId(String characterId, String userId);
    
    Integer getNextSequence();

    int selectByCommentId(Long id);
    
    int insert(RdbCharacterComment record);

    int updateByPrimaryKey(RdbCharacterComment record);

    int deleteByPrimaryKey(Long id);
}

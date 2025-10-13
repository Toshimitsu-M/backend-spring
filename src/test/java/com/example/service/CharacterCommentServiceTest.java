package com.example.service;

import com.example.domain.CharacterComment;
import com.example.repository.CharacterCommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CharacterCommentServiceTest {

    private CharacterCommentRepository repository;
    private CharacterCommentService service;

    @BeforeEach
    void setUp() {
        repository = mock(CharacterCommentRepository.class);
        service = new CharacterCommentService(repository);
    }

    @Test
    void testSelectByCharacterId() {
        String characterId = "123";
        List<CharacterComment> mockList = Arrays.asList(new CharacterComment(), new CharacterComment());
        when(repository.selectByCharacterId(characterId)).thenReturn(mockList);

        List<CharacterComment> result = service.selectByCharacterId(characterId);

        assertEquals(2, result.size());
        verify(repository).selectByCharacterId(characterId);
    }

    @Test
    void testSave() {
        CharacterComment comment = new CharacterComment();
        service.save(comment);
        verify(repository).save(comment);
    }

    @Test
    void testDeleteByPrimaryKey() {
        Long id = 1L;
        service.deleteByPrimaryKey(id);
        verify(repository).deleteById(id);
    }
}
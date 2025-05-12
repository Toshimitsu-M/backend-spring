package com.example.charactercomments.controller;

import com.example.charactercomments.model.CharacterComment;
import com.example.charactercomments.service.CharacterCommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CharacterCommentController {

    private final CharacterCommentService service;

    public CharacterCommentController(CharacterCommentService service) {
        this.service = service;
    }

    // コメントシーケンス取得
	@GetMapping("/next-sequence")
	public ResponseEntity<Integer> getNextSequence() {
		return ResponseEntity.ok(sequenceService.getNextSequence("comment_id"));
	}


    @PostMapping
    public CharacterComment postComment(@RequestBody CharacterComment comment) {
        return service.createComment(comment);
    }

    @GetMapping("/by-character")
    public List<CharacterComment> getByCharacterAndUser(
            @RequestParam String characterId,
            @RequestParam String userId) {
        return service.getCommentsByCharacterAndUser(characterId, userId);
    }

    @GetMapping
    public Iterable<CharacterComment> getAll() {
        return service.getAll();
    }
}


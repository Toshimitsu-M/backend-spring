package com.example.charactercomments.controller;

import com.example.charactercomments.model.CharacterComment;
import com.example.charactercomments.service.CharacterCommentService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CharacterCommentController
 * キャラクターに対するコメント投稿API
 * @author 
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/characterComment")
@CrossOrigin(origins = "*") // CORS設定
public class CharacterCommentController {

    private static final Logger logger = LoggerFactory.getLogger(CharacterCommentController.class);
    
    // DI
    private final CharacterCommentService service;

    // キャラクターIDでコメントリスト取得
    @GetMapping("/all/{characterId}")
    public List<CharacterComment> getByCharacter(@PathVariable String characterId) {
        return service.selectByCharacterId(characterId);
    }

    // コメントの追加、編集
	@PostMapping("/process")
	public ResponseEntity<?> process(@RequestBody CharacterComment comment) {
		System.out.println("保存コントローラ、コンソールログだよ");
		logger.info("保存コントローラデバッグだよ");

		service.save(comment);
		return ResponseEntity.ok("Saved successfully");
	}
    

    // コメント削除
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
		service.deleteByPrimaryKey(id);
		return ResponseEntity.ok("Deleted successfully");

	}
}


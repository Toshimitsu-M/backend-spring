package dev.itboot.mb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.itboot.mb.model.CharacterComment;
import dev.itboot.mb.service.CharacterCommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/characterComment")
@CrossOrigin(origins = "http://localhost:5173") // フロントエンドのURLを指定
public class CharacterCommentController {

	private static final Logger logger = LoggerFactory.getLogger(CharacterCommentController.class);

	// DI
	private final CharacterCommentService service;

	// キャラクターコメントリスト取得
	@GetMapping("/all/{id}")
	public List<CharacterComment> getAllCharacterComments(@PathVariable String id, Model model) {
		return service.selectByCharacterId(id);
	}

	// コメントシーケンス取得
	@GetMapping("/next-sequence")
	public ResponseEntity<Integer> getNextSequence() {
		return ResponseEntity.ok(service.getNextSequence());
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
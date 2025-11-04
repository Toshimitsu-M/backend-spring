package com.example.controller;

import com.example.domain.CharacterComment;
import com.example.service.CharacterCommentService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
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

    // コメント全件取得
    @GetMapping("/all")
    public List<CharacterComment> getAllComments() {
        return service.selectAll();
    }

    // コメントCSV出力
    @GetMapping(value = "/export", produces = "text/csv")
    public ResponseEntity<InputStreamResource> exportCommentsCsv() {
        List<CharacterComment> comments = service.selectAll();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("id,anilistId,userId,comment\n");
        comments.forEach(comment -> csvBuilder
                .append(escapeCsv(comment.getId() == null ? "" : comment.getId().toString()))
                .append(',')
                .append(escapeCsv(comment.getAnilistId()))
                .append(',')
                .append(escapeCsv(comment.getUserId()))
                .append(',')
                .append(escapeCsv(comment.getComment()))
                .append('\n'));

        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(csvBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=character_comments.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(csvBytes.length)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(inputStream));
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\n") || escaped.contains("\r") || escaped.contains("\"")) {
            return '\"' + escaped + '\"';
        }
        return escaped;
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


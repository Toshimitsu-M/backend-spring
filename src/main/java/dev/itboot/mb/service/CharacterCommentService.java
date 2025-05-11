package dev.itboot.mb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.itboot.mb.mapper.CharacterCommentMapper;
import dev.itboot.mb.model.CharacterComment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CharacterCommentService {
	private final CharacterCommentMapper mapper;
	
    //キャラクターIDに紐づくコメントを取得
	public List<CharacterComment> selectByCharacterId(String anilistId) {
		return mapper.selectByCharacterId(anilistId);
	}

	//コメントシーケンス取得
	public Integer getNextSequence() {
		System.out.println("デバッグだよ");
		return mapper.getNextSequence();
	}
	
    //コメントの登録、編集保存
	public void save(CharacterComment CharacterComment) {
		System.out.println("保存デバッグだよ");
		System.out.println("Id:" + CharacterComment.getId());
		//コメントID情報取得
		int count = mapper.selectByCommentId(CharacterComment.getId());

		if (count == 0) {
			System.out.println("インサートだよ");
			mapper.insert(CharacterComment);
		} else {
			System.out.println("アップデートだよ");
			mapper.updateByPrimaryKey(CharacterComment);
		}
	}
	
    //コメントの削除
	public void deleteByPrimaryKey(Long id) {
		mapper.deleteByPrimaryKey(id);
	}
}
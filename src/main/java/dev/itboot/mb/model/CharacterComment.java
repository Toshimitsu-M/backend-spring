package dev.itboot.mb.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CharacterComment {
    // コメントID
    private Long id;

    // AnilistキャラID
	@NotBlank
	@Size(max = 60)
	private String anilistId;

    // ユーザID
    @NotBlank
	@Size(max = 60)
	private String userId;
	
    // コメント
    @NotBlank
	@Size(max = 254)
	private String comment;
}

package com.example.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
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

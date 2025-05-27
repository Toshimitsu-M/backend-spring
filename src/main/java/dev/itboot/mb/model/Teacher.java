package dev.itboot.mb.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Teacher {
	private Long id;
	
	@NotBlank
	@Size(max = 60)
	private String userName;
	
	@NotBlank
	@Email
	@Size(max = 254)
	private String email;



}
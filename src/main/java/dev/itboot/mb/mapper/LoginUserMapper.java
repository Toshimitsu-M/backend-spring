package dev.itboot.mb.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import dev.itboot.mb.model.LoginUser;

@Mapper
public interface LoginUserMapper {
	
	Optional<LoginUser> selectByEmail(String email);
	
}
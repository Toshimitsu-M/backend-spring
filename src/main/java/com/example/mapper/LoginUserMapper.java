package com.example.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.LoginUser;

@Mapper
public interface LoginUserMapper {
	
	Optional<LoginUser> selectByEmail(String email);
	
}
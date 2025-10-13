package com.example.mapper.oracle.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.entity.rdb.LoginUser;

@Mapper
public interface LoginUserMapper {
	
	Optional<LoginUser> selectByEmail(String email);
	
}
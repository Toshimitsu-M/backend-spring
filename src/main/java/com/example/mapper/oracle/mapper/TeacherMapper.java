package com.example.mapper.oracle.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.example.entity.rdb.Teacher;

@Mapper
public interface TeacherMapper {
	Long count();
	
	List<Teacher> selectAll(RowBounds rowBounds);
	
	List<Teacher> selectAll();
	
	Teacher selectByPrimaryKey(Long id);
	
	
	int insert(Teacher record);
	
	
	int updateByPrimaryKey(Teacher record);
	
	
	int deleteByPrimaryKey(Long id);
}
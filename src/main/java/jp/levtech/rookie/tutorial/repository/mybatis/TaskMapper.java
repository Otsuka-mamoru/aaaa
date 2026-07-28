package jp.levtech.rookie.tutorial.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.levtech.rookie.tutorial.model.Todo;

@Mapper
public interface TaskMapper {
	List<Todo> findByDate(String date);
	
	List<Todo> search(String keyword);

	Todo findById(int id);

	void register(Todo task);

	void update(Todo task);

	void delete(int id);
	
}
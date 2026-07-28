package jp.levtech.rookie.tutorial.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jp.levtech.rookie.tutorial.model.Todo;
import jp.levtech.rookie.tutorial.repository.mybatis.TaskMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskMapper taskMapper;

    @Override
    public List<Todo> findByDate(String date) {
        return taskMapper.findByDate(date);
    }

    @Override
    public Todo findById(int id) {
        return taskMapper.findById(id);
    }

    @Override
    public void register(Todo todo) {
        taskMapper.register(todo);
    }

    @Override
    public void update(Todo todo) {
        taskMapper.update(todo);
    }

    @Override
    public void delete(int id) {
        taskMapper.delete(id);
    }
    @Override
    public List<Todo> search(String keyword) {
        return taskMapper.search(keyword);
    }
}
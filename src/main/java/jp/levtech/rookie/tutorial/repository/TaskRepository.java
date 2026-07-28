package jp.levtech.rookie.tutorial.repository;

import java.util.List;

import jp.levtech.rookie.tutorial.model.Todo;

public interface TaskRepository {
    List<Todo> findByDate(String date);
    List<Todo> search(String keyword);
    Todo findById(int id);
    void register(Todo todo);
    void update(Todo todo);
    void delete(int id);
}
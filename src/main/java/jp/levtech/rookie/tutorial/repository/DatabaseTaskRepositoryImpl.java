package jp.levtech.rookie.tutorial.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import jp.levtech.rookie.tutorial.model.Todo;
import jp.levtech.rookie.tutorial.repository.mybatis.TaskMapper;

/**
 * タスクをデータベースで管理するリポジトリ
 */
@Repository
@Primary
public class DatabaseTaskRepositoryImpl implements TaskRepository {
    /**
     * タスクのマッパー
     */
    private final TaskMapper taskMapper;

    /**
     * タスクをデータベースで管理するリポジトリのコンストラクタ
     *
     * @param taskMapper タスクのマッパー
     */


    public DatabaseTaskRepositoryImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    /**
     * 日付からデータベース上のタスクを検索する。
     */
    @Override
    public List<Todo> findByDate(String date) {
        return taskMapper.findByDate(date);
    }

    /**
     * タスクIDからデータベース上のタスクを検索する。
     */
    @Override
    public Todo findById(int id) {
        return taskMapper.findById(id);
    }
    /**
     * タスクをデータベースに登録する。
     */
    @Override
    public void register(Todo todo) {
        taskMapper.register(todo);
    }
    /**
     * データベース上のタスクを更新する。
     */
    @Override
    public void update(Todo todo) {
        taskMapper.update(todo);
    }
    /**
     * データベース上のタスクを削除する。
     */
    @Override
    public void delete(int id) {
        taskMapper.delete(id);
    }
    /**
     * キーワードで検索する。
     */
    @Override
    public List<Todo> search(String keyword) {
        return taskMapper.search(keyword);
        
    }
    @Override
    public List<Todo> findByTagId(int tagId) {
        return taskMapper.findByTagId(tagId);
    }
}
package jp.levtech.rookie.tutorial.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jp.levtech.rookie.tutorial.model.Reminder;
import jp.levtech.rookie.tutorial.repository.mybatis.ReminderMapper;

/**
 * リマインドメモをデータベースで管理するリポジトリ
 */
@Repository
public class DatabaseReminderRepositoryImpl implements ReminderRepository {

    private final ReminderMapper reminderMapper;

    public DatabaseReminderRepositoryImpl(ReminderMapper reminderMapper) {
        this.reminderMapper = reminderMapper;
    }

    @Override
    public List<Reminder> findAll() {
        return reminderMapper.findAll();
    }

    @Override
    public Reminder findById(int id) {
        return reminderMapper.findById(id);
    }

    @Override
    public void register(Reminder reminder) {
        reminderMapper.register(reminder);
    }

    @Override
    public void update(Reminder reminder) {
        reminderMapper.update(reminder);
    }

    @Override
    public void delete(int id) {
        reminderMapper.delete(id);
    }
}
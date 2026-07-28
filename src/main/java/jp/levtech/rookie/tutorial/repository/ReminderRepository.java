package jp.levtech.rookie.tutorial.repository;

import java.util.List;

import jp.levtech.rookie.tutorial.model.Reminder;

public interface ReminderRepository {
    List<Reminder> findAll();
    Reminder findById(int id);
    void register(Reminder reminder);
    void update(Reminder reminder);
    void delete(int id);
}
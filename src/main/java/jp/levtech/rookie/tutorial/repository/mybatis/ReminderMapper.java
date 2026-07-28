package jp.levtech.rookie.tutorial.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.levtech.rookie.tutorial.model.Reminder;

@Mapper
public interface ReminderMapper {
    List<Reminder> findAll();
    Reminder findById(int id);
    void register(Reminder reminder);
    void update(Reminder reminder);
    void delete(int id);
}
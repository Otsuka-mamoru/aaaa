package jp.levtech.rookie.tutorial.repository;

import java.util.List;

import jp.levtech.rookie.tutorial.model.Tag;

public interface TagRepository {
    List<Tag> findAll();
    Tag findById(int id);
    void update(Tag tag);
}
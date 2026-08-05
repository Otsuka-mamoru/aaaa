package jp.levtech.rookie.tutorial.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jp.levtech.rookie.tutorial.model.Tag;
import jp.levtech.rookie.tutorial.repository.mybatis.TagMapper;

/**
 * タグをデータベースで管理するリポジトリ
 */
@Repository
public class DatabaseTagRepositoryImpl implements TagRepository {

    private final TagMapper tagMapper;

    public DatabaseTagRepositoryImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public List<Tag> findAll() {
        return tagMapper.findAll();
    }

    @Override
    public Tag findById(int id) {
        return tagMapper.findById(id);
    }

    @Override
    public void update(Tag tag) {
        tagMapper.update(tag);
    }
 
}
package jp.levtech.rookie.tutorial.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.levtech.rookie.tutorial.model.Tag;

@Mapper
public interface TagMapper {
    List<Tag> findAll();
    Tag findById(int id);
    void update(Tag tag);
}
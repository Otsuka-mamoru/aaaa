package jp.levtech.rookie.tutorial.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.levtech.rookie.tutorial.model.Tag;
import jp.levtech.rookie.tutorial.repository.TagRepository;

@Controller
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // タグ管理画面
    @GetMapping("/tag")
    public String list(Model model) {
        List<Tag> tags = tagRepository.findAll();
        model.addAttribute("tags", tags);
        return "tag/list";
    }

    // タグ名更新処理
    @PostMapping("/tag/update")
    public String update(@RequestParam int id,
            @RequestParam(required = false) String name) {
        Tag tag = tagRepository.findById(id);
        tagRepository.update(new Tag(tag.getId(), name, tag.getColor()));
        return "redirect:/tag";
    }
}
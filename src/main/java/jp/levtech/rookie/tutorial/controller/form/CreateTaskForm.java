package jp.levtech.rookie.tutorial.controller.form;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateTaskForm {

    @NotBlank(message = "タイトルは必須です")
    //バリデーション
    private String title;

    private String memo;

    private String date;
    // タグ
    private Integer tagId;
}
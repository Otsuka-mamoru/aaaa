package jp.levtech.rookie.tutorial.controller.form;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReminderForm {

    @NotBlank(message = "タイトルは必須です")
    private String title;

    private String memo;

    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;
}
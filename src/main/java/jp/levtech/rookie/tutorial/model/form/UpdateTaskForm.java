package jp.levtech.rookie.tutorial.model.form;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskForm {

    @NotBlank(message = "タイトルは必須です")
    // バリデーション
    private String title;

    private String memo;

    private String date;
    // タグ
    private Integer tagId;
    
    private Integer notifyHour;
    
    private Integer notifyMinute;
    
 // リマイどアラーム
    private Integer reminderId;
}
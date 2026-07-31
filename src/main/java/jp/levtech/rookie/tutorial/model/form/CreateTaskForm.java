package jp.levtech.rookie.tutorial.model.form;

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
	//時間設定
	private Integer notifyHour;
	
	private Integer notifyMinute;
	
	private Integer reminderId;
}
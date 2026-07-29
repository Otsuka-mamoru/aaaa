package jp.levtech.rookie.tutorial.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    private int id;
    private String date;
    private String title;
    private String memo;
    private Integer tagId;
}
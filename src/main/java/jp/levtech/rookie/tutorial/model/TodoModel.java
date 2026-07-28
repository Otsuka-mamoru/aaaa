package jp.levtech.rookie.tutorial.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoModel {
    private String date;
    private List<Todo> todos;
}
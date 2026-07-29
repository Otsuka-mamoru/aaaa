package jp.levtech.rookie.tutorial.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CalendarModel {
    private int year;
    private int month;
    private int prevYear;
    private int prevMonth;
    private int nextYear;
    private int nextMonth;
    private List<List<String>> weeks;
    private String todayStr;
    private List<Todo> todayTodos;
    private Integer selectedTagId;
}
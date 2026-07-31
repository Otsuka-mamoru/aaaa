package jp.levtech.rookie.tutorial.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reminder {
    private int id;
    private String title;
    private String memo;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;
 // 時間設定用
    private Integer notifyHour;
    private Integer notifyMinute;
}
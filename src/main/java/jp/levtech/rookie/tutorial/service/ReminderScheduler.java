package jp.levtech.rookie.tutorial.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jp.levtech.rookie.tutorial.model.Reminder;
import jp.levtech.rookie.tutorial.model.Todo;
import jp.levtech.rookie.tutorial.repository.ReminderRepository;
import jp.levtech.rookie.tutorial.repository.TaskRepository;

@Component
public class ReminderScheduler {

	private final ReminderRepository reminderRepository;
	private final TaskRepository taskRepository;
	private final DiscordNotification discordService;

	public ReminderScheduler(ReminderRepository reminderRepository,
			TaskRepository taskRepository,
			DiscordNotification discordService) {
		this.reminderRepository = reminderRepository;
		this.taskRepository = taskRepository;
		this.discordService = discordService;
	}

	// 毎分チェック
	@Scheduled(cron = "0 * * * * *")
	public void checkReminders() {
		LocalDate today = LocalDate.now();
		LocalTime now = LocalTime.now();
		DayOfWeek dow = today.getDayOfWeek();

		String todayStr = String.format("%04d-%02d-%02d",
				today.getYear(), today.getMonthValue(), today.getDayOfMonth());

		List<Reminder> reminders = reminderRepository.findAll();

		for (Reminder reminder : reminders) {
			// 時間が設定されていない場合はスキップ
			if (reminder.getNotifyHour() == null
					|| reminder.getNotifyMinute() == null)
				continue;

			// 時間と一致するか確認
			if (now.getHour() != reminder.getNotifyHour()
					|| now.getMinute() != reminder.getNotifyMinute())
				continue;

			// 今日の曜日に対応しているか確認
			boolean shouldRegister = switch (dow) {
			case MONDAY -> reminder.isMon();
			case TUESDAY -> reminder.isTue();
			case WEDNESDAY -> reminder.isWed();
			case THURSDAY -> reminder.isThu();
			case FRIDAY -> reminder.isFri();
			case SATURDAY -> reminder.isSat();
			case SUNDAY -> reminder.isSun();
			};

			// Todoの通知時間チェック
			List<Todo> todayTodos = taskRepository.findByDate(todayStr);
			for (Todo todo : todayTodos) {
				if (todo.getNotifyHour() == null
						|| todo.getNotifyMinute() == null)
					continue;
				if (now.getHour() == todo.getNotifyHour()
						&& now.getMinute() == todo.getNotifyMinute())
					

				{
					// 通知ログをコンソールに出力
					System.out.println("ToDoリマインド: " + todo.getTitle());
				}
			}
			if (!shouldRegister)
				continue;
			

			// 重複チェック
			List<Todo> existing = taskRepository.findByDate(todayStr);
			boolean alreadyExists = existing.stream()
					.anyMatch(t -> t.getTitle().equals(reminder.getTitle()));
			//のちほどチェック

			if (!alreadyExists) {
				taskRepository.register(new Todo(0, todayStr,
						reminder.getTitle(), reminder.getMemo(), null, null, null, reminder.getId()));
			}
			
			//discord通知設定
			discordService.send("🔔 リマインド: " + reminder.getTitle()
            + " (" + reminder.getNotifyHour() + "時"
            + reminder.getNotifyMinute() + "分)");

		}
	}
}
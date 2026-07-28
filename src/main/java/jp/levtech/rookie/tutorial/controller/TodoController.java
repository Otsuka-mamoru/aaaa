package jp.levtech.rookie.tutorial.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.levtech.rookie.tutorial.controller.form.CreateTaskForm;
import jp.levtech.rookie.tutorial.controller.form.UpdateTaskForm;
import jp.levtech.rookie.tutorial.model.CalendarModel;
import jp.levtech.rookie.tutorial.model.Reminder;
import jp.levtech.rookie.tutorial.model.Todo;
import jp.levtech.rookie.tutorial.model.TodoModel;
import jp.levtech.rookie.tutorial.repository.ReminderRepository;
import jp.levtech.rookie.tutorial.repository.TaskRepository;

@Controller
public class TodoController {

    private final TaskRepository taskRepository;
    private final ReminderRepository reminderRepository;
    /**
     * リポジトリ
     */
    public TodoController(TaskRepository taskRepository,
            ReminderRepository reminderRepository) {
        this.taskRepository = taskRepository;
        this.reminderRepository = reminderRepository;
    }

    @GetMapping("/")
    /**
     * カレンダー（基本画面）設定
     */
    public String calender(
            @RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "0") int month,
            Model model) {

        LocalDate today = LocalDate.now();
        if (year == 0) year = today.getYear();
        if (month == 0) month = today.getMonthValue();

        registerReminders(today);

        YearMonth current = YearMonth.of(year, month);
        YearMonth prev = current.minusMonths(1);
        YearMonth next = current.plusMonths(1);

        String todayStr = String.format("%04d-%02d-%02d",
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());

        List<Todo> todayTodos = taskRepository.findByDate(todayStr);

        CalendarModel calendarModel = new CalendarModel(
                year, month,
                prev.getYear(), prev.getMonthValue(),
                next.getYear(), next.getMonthValue(),
                buildCalendar(year, month),
                todayStr, todayTodos
        );

        model.addAttribute("calendarModel", calendarModel);
        return "Calender";
    }
 // 
    @GetMapping("/todo")
    public String todo(@RequestParam String date,
            @RequestParam(required = false) String keyword,
            Model model) {

        List<Todo> todos;
        if (keyword != null && !keyword.isEmpty()) {
            todos = taskRepository.search("%" + keyword + "%");
        } else {
            todos = taskRepository.findByDate(date);
        }

        TodoModel todoModel = new TodoModel(date, todos);
        model.addAttribute("todoModel", todoModel);
        model.addAttribute("keyword", keyword);
        return "todo";
    }
    @GetMapping("/todo/new")
    public String newTodo(@RequestParam String date, Model model) {
        CreateTaskForm createTaskForm = new CreateTaskForm();
        createTaskForm.setDate(date);
        model.addAttribute("createTaskForm", createTaskForm);
        return "create";
    }

    @PostMapping("/todo/create")
    public String createTodo(@Valid CreateTaskForm createTaskForm,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create";
        }
        taskRepository.register(new Todo(0, createTaskForm.getDate(),
                createTaskForm.getTitle(), createTaskForm.getMemo(), null));
        return "redirect:/todo?date=" + createTaskForm.getDate();
    }
    @GetMapping("/todo/detail")
    public String detail(@RequestParam int id, Model model) {
        Todo todo = taskRepository.findById(id);
        model.addAttribute("todo", todo);
        return "detail";
    }

    @GetMapping("/todo/delete-confirm")
    public String deleteConfirm(@RequestParam int id, Model model) {
        Todo todo = taskRepository.findById(id);
        model.addAttribute("todo", todo);
        return "delete-confirm";
    }

    @PostMapping("/todo/delete")
    public String delete(@RequestParam int id, @RequestParam String date) {
        taskRepository.delete(id);
        return "redirect:/todo/deleted?date=" + date;
    }

    @GetMapping("/todo/deleted")
    public String deleted(@RequestParam String date, Model model) {
        model.addAttribute("date", date);
        return "delete";
    }

    @GetMapping("/todo/edit")
    public String edit(@RequestParam int id, Model model) {
        Todo todo = taskRepository.findById(id);
        UpdateTaskForm updateTaskForm = new UpdateTaskForm(
                todo.getTitle(), todo.getMemo(), todo.getDate());
        model.addAttribute("updateTaskForm", updateTaskForm);
        model.addAttribute("id", id);
        return "edit";
    }

    @PostMapping("/todo/update")
    public String update(@RequestParam int id,
            @Valid UpdateTaskForm updateTaskForm,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "edit";
        }
        taskRepository.update(new Todo(id, updateTaskForm.getDate(),
                updateTaskForm.getTitle(), updateTaskForm.getMemo(), null));
        return "redirect:/todo?date=" + updateTaskForm.getDate();
    }

    private void registerReminders(LocalDate today) {
        String todayStr = String.format("%04d-%02d-%02d",
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());

        DayOfWeek dow = today.getDayOfWeek();
        List<Reminder> reminders = reminderRepository.findAll();

        for (Reminder reminder : reminders) {
            boolean shouldRegister = switch (dow) {
                case MONDAY    -> reminder.isMon();
                case TUESDAY   -> reminder.isTue();
                case WEDNESDAY -> reminder.isWed();
                case THURSDAY  -> reminder.isThu();
                case FRIDAY    -> reminder.isFri();
                case SATURDAY  -> reminder.isSat();
                case SUNDAY    -> reminder.isSun();
            };

            if (!shouldRegister) continue;

            List<Todo> existing = taskRepository.findByDate(todayStr);
            boolean alreadyExists = existing.stream()
                    .anyMatch(t -> t.getTitle().equals(reminder.getTitle()));

            if (!alreadyExists) {
                taskRepository.register(new Todo(0, todayStr,
                        reminder.getTitle(), reminder.getMemo(),null));
            }
        }
    }

    private List<List<String>> buildCalendar(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate firstDay = ym.atDay(1);
        int startDow = firstDay.getDayOfWeek().getValue() % 7;

        List<List<String>> weeks = new ArrayList<>();
        List<String> week = new ArrayList<>();

        for (int i = 0; i < startDow; i++) week.add(null);

        for (int d = 1; d <= ym.lengthOfMonth(); d++) {
            week.add(String.format("%04d-%02d-%02d", year, month, d));
            if (week.size() == 7) {
                weeks.add(week);
                week = new ArrayList<>();
            }
        }

        while (week.size() < 7) week.add(null);
        if (!week.isEmpty()) weeks.add(week);

        return weeks;
    }
}
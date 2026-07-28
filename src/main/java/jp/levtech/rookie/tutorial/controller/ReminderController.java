package jp.levtech.rookie.tutorial.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.levtech.rookie.tutorial.controller.form.ReminderForm;
import jp.levtech.rookie.tutorial.model.Reminder;
import jp.levtech.rookie.tutorial.repository.ReminderRepository;

@Controller
public class ReminderController {

    private final ReminderRepository reminderRepository;

    public ReminderController(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    // リマインド一覧画面
    @GetMapping("/reminder")
    public String list(Model model) {
        List<Reminder> reminders = reminderRepository.findAll();
        model.addAttribute("reminders", reminders);
        return "reminder/list";
    }

    // リマインド新規作成画面
    @GetMapping("/reminder/new")
    public String newReminder(Model model) {
        model.addAttribute("reminderForm", new ReminderForm());
        return "reminder/create";
    }

    // リマインドメモ登録処理
    @PostMapping("/reminder/create")
    public String create(@Valid ReminderForm reminderForm,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "reminder/create";
        }
        reminderRepository.register(new Reminder(
                0,
                reminderForm.getTitle(),
                reminderForm.getMemo(),
                reminderForm.isMon(),
                reminderForm.isTue(),
                reminderForm.isWed(),
                reminderForm.isThu(),
                reminderForm.isFri(),
                reminderForm.isSat(),
                reminderForm.isSun()
        ));
        return "redirect:/reminder";
    }

    // リマインドメモ編集画面
    @GetMapping("/reminder/edit")
    public String edit(@RequestParam int id, Model model) {
        Reminder reminder = reminderRepository.findById(id);
        ReminderForm reminderForm = new ReminderForm();
        reminderForm.setTitle(reminder.getTitle());
        reminderForm.setMemo(reminder.getMemo());
        reminderForm.setMon(reminder.isMon());
        reminderForm.setTue(reminder.isTue());
        reminderForm.setWed(reminder.isWed());
        reminderForm.setThu(reminder.isThu());
        reminderForm.setFri(reminder.isFri());
        reminderForm.setSat(reminder.isSat());
        reminderForm.setSun(reminder.isSun());
        model.addAttribute("reminderForm", reminderForm);
        model.addAttribute("id", id);
        return "reminder/edit";
    }

    // リマインドメモ更新処理
    @PostMapping("/reminder/update")
    public String update(@RequestParam int id,
            @Valid ReminderForm reminderForm,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "reminder/edit";
        }
        reminderRepository.update(new Reminder(
                id,
                reminderForm.getTitle(),
                reminderForm.getMemo(),
                reminderForm.isMon(),
                reminderForm.isTue(),
                reminderForm.isWed(),
                reminderForm.isThu(),
                reminderForm.isFri(),
                reminderForm.isSat(),
                reminderForm.isSun()
        ));
        return "redirect:/reminder";
    }

    // リマインドメモ削除処理
    @PostMapping("/reminder/delete")
    public String delete(@RequestParam int id) {
        reminderRepository.delete(id);
        return "redirect:/reminder";
    }
}
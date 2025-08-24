// QuestionAdminController.java
package com.huz.quizapp.controller;

import com.huz.quizapp.dao.CategoryDao;
import com.huz.quizapp.domain.Choice;
import com.huz.quizapp.domain.Question;
import com.huz.quizapp.domain.QuestionInfo;
import com.huz.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/questions")
public class QuestionAdminController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CategoryDao categoryDao;

    @GetMapping
    public String showQuestionManagementPage(@RequestParam(defaultValue = "1") int page,
                                             Model model) {
        int pageSize = 10;
        List<QuestionInfo> questionInfos = questionService.getQuestionsByPage2(page, pageSize);

        model.addAttribute("questions", questionInfos);
        model.addAttribute("totalPages", questionService.getTotalPages2(pageSize));
        model.addAttribute("currentPage", page);
        return "question_management";
    }


    @PostMapping("/toggle/{id}")
    public String toggleStatus(@PathVariable int id, @RequestParam int page) {
        questionService.toggleActiveStatus(id);
        return "redirect:/admin/questions?page=" + page;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("categories", categoryDao.findAll());
        List<Choice> emptyChoices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            emptyChoices.add(new Choice());
        }
        model.addAttribute("choices", emptyChoices);
        return "question_edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Question q = questionService.getById(id);
        model.addAttribute("question", q);
        model.addAttribute("choices", questionService.findChoicesByQuestionId(id));
        System.out.println("Loaded choices for question ID " + id + ":");
        List<Choice> choices = questionService.findChoicesByQuestionId(id);
        for (Choice c : choices) {
            System.out.println(c);
        }
        System.out.println("---------------------------");

        model.addAttribute("categories", categoryDao.findAll());
        return "question_edit";
    }

    @PostMapping("/save")
    public String saveOrUpdateQuestion(
            @ModelAttribute Question question,
            @RequestParam("choiceDescriptions") List<String> choiceDescriptions,
            @RequestParam("correctChoiceIndex") int correctIndex,
            Model model) {

        List<Choice> choices = new ArrayList<>();
        for (int i = 0; i < choiceDescriptions.size(); i++) {
            Choice choice = new Choice();
            choice.setDescription(choiceDescriptions.get(i));
            choice.setCorrect(i == correctIndex);
            choices.add(choice);
        }

        questionService.saveOrUpdateQuestionWithChoices(question, choices);

        return "redirect:/admin/questions";
    }


}

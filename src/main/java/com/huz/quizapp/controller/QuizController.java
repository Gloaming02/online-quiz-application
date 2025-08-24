
package com.huz.quizapp.controller;

import com.huz.quizapp.dao.QuizResultDao;
import com.huz.quizapp.domain.*;
import com.huz.quizapp.service.QuizService;
import com.huz.quizapp.service.QuestionService;
import com.huz.quizapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuizResultDao quizResultDao;


    @GetMapping("/start")
    public String startQuiz(@RequestParam("cid") int categoryId,
                            HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) return "redirect:/login";

        Quiz quiz = new Quiz();
        quiz.setUserId(user.getUserId());
        quiz.setCategoryId(categoryId);
        quiz.setName("Quiz - " + new Date().getTime());
        quiz.setTimeStart(new Timestamp(System.currentTimeMillis()));

        int quizId = quizService.createQuiz(quiz);

        List<Question> questions = questionService.getQuestionsWithChoicesByCategory(categoryId, 3);
        for (Question q : questions) {
            quizService.addQuizQuestion(quizId, q.getQuestionId());
        }

        return "redirect:/quiz/page?quizId=" + quizId + "&pageIndex=0";
    }


    @GetMapping("/page")
    public String showQuestion(@RequestParam int quizId,
                               @RequestParam int pageIndex,
                               Model model) {

        List<QuizQuestion> quizQuestions = quizService.getQuizQuestions(quizId);
        if (pageIndex < 0 || pageIndex >= quizQuestions.size()) {
            return "redirect:/quiz/result?quizId=" + quizId;
        }

        QuizQuestion currentQQ = quizQuestions.get(pageIndex);
        Question question = questionService.getQuestionWithChoicesById(currentQQ.getQuestionId());


        Integer selectedChoiceId = currentQQ.getUserChoiceId();

        model.addAttribute("quizId", quizId);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("totalQuestions", quizQuestions.size());
        model.addAttribute("question", question);
        model.addAttribute("selectedChoiceId", selectedChoiceId);
        return "quiz_page";
    }

    @PostMapping("/page")
    public String saveAnswerAndNavigate(HttpServletRequest request) {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        String nav = request.getParameter("nav");

        String choiceIdStr = request.getParameter("choiceId");
        if (choiceIdStr != null) {
            int choiceId = Integer.parseInt(choiceIdStr);
            List<QuizQuestion> quizQuestions = quizService.getQuizQuestions(quizId);
            for (QuizQuestion qq : quizQuestions) {
                if (qq.getQuestionId() == questionId) {
                    quizService.updateUserChoice(qq.getQqId(), choiceId);
                    break;
                }
            }
        }

        if ("prev".equals(nav)) {
            return "redirect:/quiz/page?quizId=" + quizId + "&pageIndex=" + (pageIndex - 1);
        } else if ("next".equals(nav)) {
            return "redirect:/quiz/page?quizId=" + quizId + "&pageIndex=" + (pageIndex + 1);
        } else if ("submit".equals(nav)) {
            quizService.finishQuiz(quizId);
            return "redirect:/quiz/result?quizId=" + quizId;
        }

        return "redirect:/quiz/page?quizId=" + quizId + "&pageIndex=" + pageIndex;
    }


    @GetMapping("/result")
    public String showResult(@RequestParam("quizId") int quizId, Model model) {
        List<QuizResultDetail> resultList = quizResultDao.getQuizResultDetails(quizId);
        QuizResultInfo info = quizResultDao.getQuizInfo(quizId);


        for (QuizResultDetail detail : resultList) {
            List<Choice> choices = questionService.findChoicesByQuestionId(detail.getQuestionId());
            detail.setChoices(choices); // 需要在 QuizResultDetail 中定义这个字段和 setter/getter
        }

        int correctCount = (int) resultList.stream().filter(QuizResultDetail::isCorrect).count();
        boolean passed = correctCount >= 2;

        model.addAttribute("resultList", resultList);
        model.addAttribute("quizInfo", info);
        model.addAttribute("passed", passed);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("total", resultList.size());

        return "result";
    }

}

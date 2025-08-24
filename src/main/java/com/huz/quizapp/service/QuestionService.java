package com.huz.quizapp.service;

import com.huz.quizapp.dao.ChoiceRowMapper;
import com.huz.quizapp.dao.QuestionDao;
import com.huz.quizapp.dao.QuestionRowMapper;
import com.huz.quizapp.domain.Choice;
import com.huz.quizapp.domain.Question;
import com.huz.quizapp.domain.QuestionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Question> getQuestionsWithChoicesByCategory(int categoryId, int limit) {
        String sql = "SELECT * FROM Question WHERE category_id = ? ORDER BY RAND() LIMIT ?";
        List<Question> questions = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class), categoryId, limit);

        for (Question q : questions) {
            q.setChoiceList(findChoicesByQuestionId(q.getQuestionId()));
        }

        return questions;
    }

    public List<Choice> findChoicesByQuestionId(int questionId) {
        String sql = "SELECT choice_id, question_id, description, is_correct FROM Choice WHERE question_id = ?";
        return jdbcTemplate.query(sql,new ChoiceRowMapper(), questionId);
    }

    public Question getQuestionWithChoicesById(int questionId) {
        String sql = "SELECT * FROM Question WHERE question_id = ?";
        Question question = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Question.class), questionId);
        if (question != null) {
            question.setChoiceList(findChoicesByQuestionId(questionId));
        }
        return question;
    }

    public List<Question> getQuestionsByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return questionDao.getQuestionsPaginated(offset, pageSize);
    }

    public int getTotalPages(int pageSize) {
        int count = questionDao.getTotalQuestionCount();
        return (int) Math.ceil((double) count / pageSize);
    }

    public void toggleActiveStatus(int questionId) {
        questionDao.toggleActive(questionId);
    }

    public Question getById(int questionId) {
        String sql = "SELECT * FROM Question WHERE question_id = ?";
        return jdbcTemplate.queryForObject(sql, new QuestionRowMapper(), questionId);
    }

    public void saveOrUpdateQuestionWithChoices(Question question, List<Choice> choices) {
        if (question.getQuestionId() == 0) {
            jdbcTemplate.update("INSERT INTO Question (category_id, description, is_active) VALUES (?, ?, ?)",
                    question.getCategoryId(), question.getDescription(), true);

            int questionId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            question.setQuestionId(questionId);

            for (Choice c : choices) {
                jdbcTemplate.update("INSERT INTO Choice (question_id, description, is_correct) VALUES (?, ?, ?)",
                        questionId, c.getDescription(), c.isCorrect());
            }

        } else {
            jdbcTemplate.update("UPDATE Question SET category_id = ?, description = ? WHERE question_id = ?",
                    question.getCategoryId(), question.getDescription(), question.getQuestionId());

            List<Choice> existingChoices = findChoicesByQuestionId(question.getQuestionId());

            for (int i = 0; i < choices.size(); i++) {
                Choice newChoice = choices.get(i);
                if (i < existingChoices.size()) {
                    // 更新已有选项
                    jdbcTemplate.update("UPDATE Choice SET description = ?, is_correct = ? WHERE choice_id = ?",
                            newChoice.getDescription(), newChoice.isCorrect(), existingChoices.get(i).getChoiceId());
                } else {
                    // 新增选项
                    jdbcTemplate.update("INSERT INTO Choice (question_id, description, is_correct) VALUES (?, ?, ?)",
                            question.getQuestionId(), newChoice.getDescription(), newChoice.isCorrect());
                }
            }

            for (int i = choices.size(); i < existingChoices.size(); i++) {
                jdbcTemplate.update("DELETE FROM Choice WHERE choice_id = ?", existingChoices.get(i).getChoiceId());
            }
        }
    }


    public List<QuestionInfo> getQuestionsByPage2(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return questionDao.findAllWithCategory(offset, pageSize);
    }

    public int getTotalPages2(int pageSize) {
        int totalQuestions = questionDao.getTotalQuestionCount();
        return (int) Math.ceil((double) totalQuestions / pageSize);
    }

}

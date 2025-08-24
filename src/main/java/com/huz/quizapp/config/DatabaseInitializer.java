package com.huz.quizapp.config;

import com.huz.quizapp.domain.User;
import com.huz.quizapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.List;

@Component
public class DatabaseInitializer {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostConstruct
    public void addAdminUserIfMissing() {
        // 1. Admin user
        String adminEmail = "admin";
        String adminPassword = "admin";

        if (userService.getUserByEmail(adminEmail) == null) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(adminPassword);
            admin.setFirstname("admin");
            admin.setLastname("admin");
            admin.setActive(true);
            admin.setAdmin(true);
            userService.registerAdmin(admin);
            System.out.println("Admin user inserted: " + adminEmail);
        }

        // 2. Regular user with quiz results
        String regularEmail = "test1";
        if (userService.getUserByEmail(regularEmail) == null) {
            User user = new User();
            user.setEmail(regularEmail);
            user.setPassword("test1");
            user.setFirstname("Regular");
            user.setLastname("User");
            userService.register(user,true,false);

            int userId = userService.getUserByEmail(regularEmail).getUserId();

            // insert 3 Quizzes（Math=1, Science=2, History=3）
            for (int categoryId = 1; categoryId <= 3; categoryId++) {
                String quizName = "Init Quiz - Cat " + categoryId;
                Timestamp now = new Timestamp(System.currentTimeMillis());

                jdbcTemplate.update(
                    "INSERT INTO Quiz (user_id, category_id, name, time_start, time_end) VALUES (?, ?, ?, ?, ?)",
                    userId, categoryId, quizName, now, now
                );

                Integer quizId = jdbcTemplate.queryForObject(
                        "SELECT quiz_id FROM Quiz WHERE user_id = ? AND category_id = ? ORDER BY quiz_id DESC LIMIT 1",
                        Integer.class, userId, categoryId);

                List<Integer> questionIds = jdbcTemplate.queryForList(
                        "SELECT question_id FROM Question WHERE category_id = ? LIMIT 3",
                        Integer.class, categoryId
                );

                for (int qid : questionIds) {
                    Integer choiceId = jdbcTemplate.queryForObject(
                        "SELECT choice_id FROM Choice WHERE question_id = ? ORDER BY RAND() LIMIT 1",
                        Integer.class, qid
                    );

                    jdbcTemplate.update(
                        "INSERT INTO QuizQuestion (quiz_id, question_id, user_choice_id) VALUES (?, ?, ?)",
                        quizId, qid, choiceId
                    );
                }
            }

            System.out.println("Regular user with 3 quiz results inserted.");

            // 3. Suspended user
            String suspendedEmail = "suspended";
            if (userService.getUserByEmail(suspendedEmail) == null) {
                User suspended = new User();
                suspended.setEmail(suspendedEmail);
                suspended.setPassword("suspended");
                suspended.setFirstname("Suspended");
                suspended.setLastname("User");
                userService.register(suspended, false, false);
                System.out.println("Suspended user inserted: " + suspendedEmail);
            }


        }

    }
}

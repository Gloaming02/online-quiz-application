DROP TABLE IF EXISTS QuizQuestion, Quiz, Choice, Question, Category, User, Contact;

CREATE TABLE Category (
category_id INT AUTO_INCREMENT PRIMARY KEY,
name        VARCHAR(100) NOT NULL
);

CREATE TABLE User (
    user_id     INT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(255) UNIQUE NOT NULL,
    password    VARCHAR(255) NOT NULL,
    firstname   VARCHAR(100),
    lastname    VARCHAR(100),
    is_active   BOOLEAN DEFAULT TRUE,
    is_admin    BOOLEAN DEFAULT FALSE
);

CREATE TABLE Quiz (
    quiz_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT,
    category_id INT,
    name        VARCHAR(100),
    time_start  TIMESTAMP,
    time_end    TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

CREATE TABLE Question (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    description TEXT NOT NULL,
    is_active   BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

CREATE TABLE Choice (
    choice_id   INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT,
    description VARCHAR(255),
    is_correct  BOOLEAN,
    FOREIGN KEY (question_id) REFERENCES Question(question_id)
);


CREATE TABLE QuizQuestion (
    qq_id           INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id         INT,
    question_id     INT,
    user_choice_id  INT,
    FOREIGN KEY (quiz_id) REFERENCES Quiz(quiz_id),
    FOREIGN KEY (question_id) REFERENCES Question(question_id),
    FOREIGN KEY (user_choice_id) REFERENCES Choice(choice_id)
);

CREATE TABLE Contact (
    contact_id  INT AUTO_INCREMENT PRIMARY KEY,
    subject     VARCHAR(255),
    message     TEXT,
    email       VARCHAR(255),
    time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO Category (name) VALUES
('Math'),
('Science'),
('History');

INSERT INTO Question (category_id, description) VALUES
(1, 'What is 5 * 6?'),
(1, 'What is the square of 4?'),
(1, 'What is the value of π (pi) approximately?');

INSERT INTO Question (category_id, description) VALUES
(2, 'Which part of the plant conducts photosynthesis?'),
(2, 'Which is the hottest planet in our solar system?'),
(2, 'What is H2O commonly known as?');

INSERT INTO Question (category_id, description) VALUES
(3, 'Who discovered America in 1492?'),
(3, 'Which war ended in 1945?'),
(3, 'Who was the 16th President of the USA?');

INSERT INTO Choice (question_id, description, is_correct) VALUES
(1, '11', false), (1, '30', true), (1, '56', false), (1, '25', false),
(2, '12', false), (2, '16', true), (2, '8', false), (2, '14', false),
(3, '2.71', false), (3, '3.14', true), (3, '3.00', false), (3, '4.13', false);

INSERT INTO Choice (question_id, description, is_correct) VALUES
(4, 'Leaf', true), (4, 'Root', false), (4, 'Stem', false), (4, 'Flower', false),
(5, 'Venus', true), (5, 'Mercury', false), (5, 'Earth', false), (5, 'Mars', false),
(6, 'Hydrogen', false), (6, 'Oxygen', false), (6, 'Water', true), (6, 'Salt', false);

INSERT INTO Choice (question_id, description, is_correct) VALUES
(7, 'Christopher Columbus', true), (7, 'George Washington', false), (7, 'Napoleon', false), (7, 'Marco Polo', false),
(8, 'World War I', false), (8, 'World War II', true), (8, 'Cold War', false), (8, 'Civil War', false),
(9, 'Abraham Lincoln', true), (9, 'John F. Kennedy', false), (9, 'Franklin D. Roosevelt', false), (9, 'Theodore Roosevelt', false);

-- Math 多加两道题
INSERT INTO Question (category_id, description) VALUES
(1, 'What is 12 + 8?'),
(1, 'What is 100 / 25?');

INSERT INTO Choice (question_id, description, is_correct) VALUES
(10, '21', false), (10, '20', true), (10, '18', false), (10, '24', false),
(11, '5', false), (11, '4', true), (11, '25', false), (11, '2', false);

-- Science 多加两道题
INSERT INTO Question (category_id, description) VALUES
(2, 'What gas do humans exhale?'),
(2, 'What is the boiling point of water in Celsius?');

INSERT INTO Choice (question_id, description, is_correct) VALUES
(12, 'Oxygen', false), (12, 'Carbon Dioxide', true), (12, 'Nitrogen', false), (12, 'Hydrogen', false),
(13, '90°C', false), (13, '100°C', true), (13, '80°C', false), (13, '120°C', false);

-- History 多加两道题
INSERT INTO Question (category_id, description) VALUES
(3, 'When did the Berlin Wall fall?'),
(3, 'Who wrote the Declaration of Independence?');

INSERT INTO Choice (question_id, description, is_correct) VALUES
(14, '1989', true), (14, '1991', false), (14, '1980', false), (14, '2000', false),
(15, 'Thomas Jefferson', true), (15, 'George Washington', false), (15, 'Benjamin Franklin', false), (15, 'John Adams', false);

DROP TABLE IF EXISTS user_course;
DROP TABLE IF EXISTS lesson;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS user_entity;

CREATE TABLE course
(
    id    BIGSERIAL,
    title VARCHAR(255),
    ---------------------------------------------
    CONSTRAINT course_id_pk PRIMARY KEY (id),
    CONSTRAINT title_nn CHECK (title IS NOT NULL)
);

CREATE TABLE lesson
(
    id        BIGSERIAL,
    name      VARCHAR(255),
    course_id BIGINT,
    ----------------------------------------------------------------------------------------
    CONSTRAINT lesson_id_pk PRIMARY KEY (id),
    CONSTRAINT name_nn CHECK (name IS NOT NULL),
    CONSTRAINT course_id_fk FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE,
    CONSTRAINT course_id_nn CHECK (course_id IS NOT NULL)
);

CREATE TABLE user_entity
(
    id   BIGSERIAL,
    name VARCHAR(255),
    ----------------------------------------------
    CONSTRAINT user_entity_id_pk PRIMARY KEY (id),
    CONSTRAINT name_nn CHECK (name IS NOT NULL)
);

CREATE TABLE user_course
(
    user_id   BIGINT,
    course_id BIGINT,
    ------------------------------------------------------------------------------------------
    CONSTRAINT user_course_pk PRIMARY KEY (user_id, course_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user_entity (id) ON DELETE CASCADE,
    CONSTRAINT course_id_fk FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE,
    CONSTRAINT user_id_nn CHECK (user_id IS NOT NULL),
    CONSTRAINT course_id_nn CHECK (course_id IS NOT NULL)
);

CREATE INDEX idx_lesson_course_id ON lesson (course_id);
CREATE INDEX idx_user_course_user_id ON user_course (user_id);
CREATE INDEX idx_user_course_course_id ON user_course (course_id);
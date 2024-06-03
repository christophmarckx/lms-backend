drop schema if exists lms cascade;
create schema lms;

CREATE TABLE lms.course
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description text
);

CREATE TABLE lms.classgroup
(
    id        UUID PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    course_id UUID  NOT NULL,
    FOREIGN KEY (course_id) REFERENCES lms.course (id)
);

CREATE TABLE lms.app_user
(
    id            UUID PRIMARY KEY,
    email_address VARCHAR(255) NOT NULL UNIQUE,
    display_name  VARCHAR(255) NOT NULL,
    role          VARCHAR(255) NOT NULL
);

CREATE TABLE lms.classgroup_app_user
(
    id            UUID PRIMARY KEY,
    classgroup_id UUID NOT NULL,
    app_user_id       UUID NOT NULL,
    FOREIGN KEY (classgroup_id) REFERENCES lms.classgroup (id),
    FOREIGN KEY (app_user_id) REFERENCES lms.app_user (id)
);


CREATE TABLE lms.module
(
    id        UUID PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    parent_id UUID,
    FOREIGN KEY (parent_id) REFERENCES lms.module (id)
);

CREATE TABLE lms.course_module
(
    id        UUID PRIMARY KEY,
    course_id UUID NOT NULL,
    module_id UUID NOT NULL,
    FOREIGN KEY (course_id) REFERENCES lms.course (id),
    FOREIGN KEY (module_id) REFERENCES lms.module (id)
);

CREATE TABLE lms.codelab
(
    id        UUID PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    description      text,
    module_id UUID  NOT NULL,
    FOREIGN KEY (module_id) REFERENCES lms.module (id)
);

CREATE TABLE lms.comment
(
    id         UUID PRIMARY KEY,
    codelab_id UUID NOT NULL,
    app_user_id    UUID NOT NULL,
    comment    text,
    FOREIGN KEY (codelab_id) REFERENCES lms.codelab (id),
    FOREIGN KEY (app_user_id) REFERENCES lms.app_user (id)
);

CREATE TABLE lms.progress
(
    id         UUID PRIMARY KEY,
    codelab_id UUID NOT NULL,
    app_user_id    UUID NOT NULL,
    status     VARCHAR(50),
    FOREIGN KEY (codelab_id) REFERENCES lms.codelab (id),
    FOREIGN KEY (app_user_id) REFERENCES lms.app_user (id)
);

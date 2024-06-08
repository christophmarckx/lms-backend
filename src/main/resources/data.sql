-- Inserting data into course table
INSERT INTO lms.course (id, name, description)
VALUES ('e0e8b080-df45-11ec-9d64-0242ac120002', 'Computer Science 101', 'Introduction to Computer Science'),
       ('e0e8b081-df45-11ec-9d64-0242ac120002', 'Advanced Mathematics', 'Advanced topics in Mathematics'),
       ('f953c154-36f2-4b79-8992-b6f5d4dd24a9', 'Java@Fin', 'The Java@Fin course');

-- Inserting data into classgroup table
INSERT INTO lms.classgroup (id, name, course_id)
VALUES ('e0e8b082-df45-11ec-9d64-0242ac120002', 'CS101 Group A', 'e0e8b080-df45-11ec-9d64-0242ac120002'),
       ('e0e8b083-df45-11ec-9d64-0242ac120002', 'CS101 Group B', 'e0e8b080-df45-11ec-9d64-0242ac120002'),
       ('e0e8b084-df45-11ec-9d64-0242ac120002', 'Math Advanced Group', 'e0e8b081-df45-11ec-9d64-0242ac120002');

-- Inserting data into app_user table
INSERT INTO lms.app_user (id, email_address, display_name, role)
VALUES ('1efd5bca-ce77-4f16-8d31-6f30205dd4e5', 'testing@student.com', 'Super Testing Student', 'STUDENT'),
       ('e0daecc3-2967-496c-a5d8-d25e727bdc74', 'testing@coach.com', 'Super Testing Coach', 'COACH'),
       ('e0e8b085-df45-11ec-9d64-0242ac120002', 'john.doe@example.com', 'John Doe', 'STUDENT'),
       ('e0e8b086-df45-11ec-9d64-0242ac120002', 'jane.smith@example.com', 'Jane Smith', 'STUDENT'),
       ('e0e8b087-df45-11ec-9d64-0242ac120002', 'admin@example.com', 'Admin User', 'COACH');
-- Inserting data into classgroup_app_user table
INSERT INTO lms.classgroup_app_user (classgroup_id, app_user_id)
VALUES ('e0e8b082-df45-11ec-9d64-0242ac120002', 'e0e8b085-df45-11ec-9d64-0242ac120002'),
       ('e0e8b083-df45-11ec-9d64-0242ac120002', 'e0e8b086-df45-11ec-9d64-0242ac120002'),
       ('e0e8b083-df45-11ec-9d64-0242ac120002', '1efd5bca-ce77-4f16-8d31-6f30205dd4e5');

--INSERT INTO classgroup_app_user (id, classgroup_id, app_user_id) VALUES
--('4a4da83b-e173-43b1-af54-735fde7d6a5e', '5547a8d1-e468-46db-b9c0-e20e56f16f5c', '74b51b9a-f6d8-4e12-849d-bf926d974441'),
--('1b72a824-cd30-45d0-8248-416ff14ec79f', '5547a8d1-e468-46db-b9c0-e20e56f16f5c', '6679126c-ec62-4e79-8333-18a91f165e0f');


-- Inserting data into module table
INSERT INTO lms.module (id, name, parent_id)
VALUES ('e0e8b090-df45-11ec-9d64-0242ac120002', 'Intro to Programming', NULL),
       ('e0e8b091-df45-11ec-9d64-0242ac120002', 'Algorithms', 'e0e8b090-df45-11ec-9d64-0242ac120002'),
       ('e0e8b092-df45-11ec-9d64-0242ac120002', 'Data Structures', 'e0e8b090-df45-11ec-9d64-0242ac120002');

-- Inserting data into course_module table
INSERT INTO lms.course_module (course_id, module_id)
VALUES ('f953c154-36f2-4b79-8992-b6f5d4dd24a9', 'e0e8b090-df45-11ec-9d64-0242ac120002'),
       ('f953c154-36f2-4b79-8992-b6f5d4dd24a9', 'e0e8b091-df45-11ec-9d64-0242ac120002'),
       ('f953c154-36f2-4b79-8992-b6f5d4dd24a9', 'e0e8b092-df45-11ec-9d64-0242ac120002');

-- Inserting data into codelab table
INSERT INTO lms.codelab (id, name, description, module_id)
VALUES ('e0e8b096-df45-11ec-9d64-0242ac120002', 'Hello World Lab', 'First Hello World Lab',
        'e0e8b090-df45-11ec-9d64-0242ac120002'),
       ('e0e8b097-df45-11ec-9d64-0242ac120002', 'Sorting Algorithms Lab',
        'Lab about complex algorithm for sorting (Like bubble sort, the best)', 'e0e8b091-df45-11ec-9d64-0242ac120002'),
       ('e0e8b098-df45-11ec-9d64-0242ac120002', 'Binary Trees Lab',
        'You love tree ? You love binary ? You will love Binary Tree !', 'e0e8b092-df45-11ec-9d64-0242ac120002');

-- Inserting data into comment table
INSERT INTO lms.comment (id, codelab_id, app_user_id, comment)
VALUES ('e0e8b099-df45-11ec-9d64-0242ac120002', 'e0e8b096-df45-11ec-9d64-0242ac120002',
        'e0e8b085-df45-11ec-9d64-0242ac120002', 'This lab was very informative!'),
       ('e0e8b100-df45-11ec-9d64-0242ac120002', 'e0e8b097-df45-11ec-9d64-0242ac120002',
        'e0e8b086-df45-11ec-9d64-0242ac120002', 'Sorting algorithms are tricky but fun.');

-- Inserting data into progress table
INSERT INTO lms.progress (id, codelab_id, app_user_id, status)
VALUES ('e0e8b101-df45-11ec-9d64-0242ac120002', 'e0e8b096-df45-11ec-9d64-0242ac120002',
        'e0e8b085-df45-11ec-9d64-0242ac120002', 'completed'),
       ('e0e8b102-df45-11ec-9d64-0242ac120002', 'e0e8b097-df45-11ec-9d64-0242ac120002',
        'e0e8b086-df45-11ec-9d64-0242ac120002', 'in_progress');

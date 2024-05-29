-- Inserting data into course table
INSERT INTO course (id, name, description) VALUES
    ('e0e8b080-df45-11ec-9d64-0242ac120002', 'Computer Science 101', 'Introduction to Computer Science'),
    ('e0e8b081-df45-11ec-9d64-0242ac120002', 'Advanced Mathematics', 'Advanced topics in Mathematics');

-- Inserting data into classgroup table
INSERT INTO classgroup (id, name, course_id) VALUES
     ('e0e8b082-df45-11ec-9d64-0242ac120002', 'CS101 Group A', 'e0e8b080-df45-11ec-9d64-0242ac120002'),
     ('e0e8b083-df45-11ec-9d64-0242ac120002', 'CS101 Group B', 'e0e8b080-df45-11ec-9d64-0242ac120002'),
     ('e0e8b084-df45-11ec-9d64-0242ac120002', 'Math Advanced Group', 'e0e8b081-df45-11ec-9d64-0242ac120002');

-- Inserting data into app_user table
INSERT INTO app_user (id, email_address, display_name, role) VALUES
     ('e0e8b085-df45-11ec-9d64-0242ac120002', 'john.doe@example.com', 'John Doe', 'STUDENT'),
     ('e0e8b086-df45-11ec-9d64-0242ac120002', 'jane.smith@example.com', 'Jane Smith', 'STUDENT'),
     ('e0e8b087-df45-11ec-9d64-0242ac120002', 'admin@example.com', 'Admin User', 'COACH');

-- Inserting data into classgroup_app_user table
INSERT INTO classgroup_app_user (id, classgroup_id, app_user_id) VALUES
     ('e0e8b088-df45-11ec-9d64-0242ac120002', 'e0e8b082-df45-11ec-9d64-0242ac120002', 'e0e8b085-df45-11ec-9d64-0242ac120002'),
     ('e0e8b089-df45-11ec-9d64-0242ac120002', 'e0e8b083-df45-11ec-9d64-0242ac120002', 'e0e8b086-df45-11ec-9d64-0242ac120002');

-- Inserting data into module table
INSERT INTO module (id, name, parent_id) VALUES
     ('e0e8b090-df45-11ec-9d64-0242ac120002', 'Intro to Programming', NULL),
     ('e0e8b091-df45-11ec-9d64-0242ac120002', 'Algorithms', 'e0e8b090-df45-11ec-9d64-0242ac120002'),
     ('e0e8b092-df45-11ec-9d64-0242ac120002', 'Data Structures', 'e0e8b090-df45-11ec-9d64-0242ac120002');

-- Inserting data into course_module table
INSERT INTO course_module (id, course_id, module_id) VALUES
     ('e0e8b093-df45-11ec-9d64-0242ac120002', 'e0e8b080-df45-11ec-9d64-0242ac120002', 'e0e8b090-df45-11ec-9d64-0242ac120002'),
     ('e0e8b094-df45-11ec-9d64-0242ac120002', 'e0e8b080-df45-11ec-9d64-0242ac120002', 'e0e8b091-df45-11ec-9d64-0242ac120002'),
     ('e0e8b095-df45-11ec-9d64-0242ac120002', 'e0e8b080-df45-11ec-9d64-0242ac120002', 'e0e8b092-df45-11ec-9d64-0242ac120002');

-- Inserting data into codelab table
INSERT INTO codelab (id, name, module_id) VALUES
      ('e0e8b096-df45-11ec-9d64-0242ac120002', 'Hello World Lab', 'e0e8b090-df45-11ec-9d64-0242ac120002'),
      ('e0e8b097-df45-11ec-9d64-0242ac120002', 'Sorting Algorithms Lab', 'e0e8b091-df45-11ec-9d64-0242ac120002'),
      ('e0e8b098-df45-11ec-9d64-0242ac120002', 'Binary Trees Lab', 'e0e8b092-df45-11ec-9d64-0242ac120002');

-- Inserting data into comment table
INSERT INTO comment (id, codelab_id, app_user_id, comment) VALUES
    ('e0e8b099-df45-11ec-9d64-0242ac120002', 'e0e8b096-df45-11ec-9d64-0242ac120002', 'e0e8b085-df45-11ec-9d64-0242ac120002', 'This lab was very informative!'),
    ('e0e8b100-df45-11ec-9d64-0242ac120002', 'e0e8b097-df45-11ec-9d64-0242ac120002', 'e0e8b086-df45-11ec-9d64-0242ac120002', 'Sorting algorithms are tricky but fun.');

-- Inserting data into progress table
INSERT INTO progress (id, codelab_id, app_user_id, status) VALUES
   ('e0e8b101-df45-11ec-9d64-0242ac120002', 'e0e8b096-df45-11ec-9d64-0242ac120002', 'e0e8b085-df45-11ec-9d64-0242ac120002', 'completed'),
   ('e0e8b102-df45-11ec-9d64-0242ac120002', 'e0e8b097-df45-11ec-9d64-0242ac120002', 'e0e8b086-df45-11ec-9d64-0242ac120002', 'in_progress');
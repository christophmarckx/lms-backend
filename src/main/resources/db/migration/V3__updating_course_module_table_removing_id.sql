BEGIN;

ALTER TABLE lms.course_module DROP CONSTRAINT IF EXISTS course_module_pkey;

ALTER TABLE lms.course_module DROP COLUMN IF EXISTS id;

COMMIT;

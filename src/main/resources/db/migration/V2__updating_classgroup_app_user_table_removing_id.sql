BEGIN;

ALTER TABLE lms.classgroup_app_user DROP CONSTRAINT IF EXISTS classgroup_app_user_pk;

ALTER TABLE lms.classgroup_app_user DROP COLUMN IF EXISTS id;

COMMIT;

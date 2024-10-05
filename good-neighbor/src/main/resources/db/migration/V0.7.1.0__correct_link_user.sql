-- Correct Table "global_link_user_authority" who has wrong references

/* NO ROLLBACK SCRIPT
 */

ALTER TABLE global_link_user_authority DROP CONSTRAINT IF EXISTS global_link_user_authority_user_authority_id_fkey;
ALTER TABLE global_link_user_authority DROP CONSTRAINT IF EXISTS global_link_user_authority_user_id_fkey;

ALTER TABLE global_link_user_authority ADD FOREIGN KEY (user_id) REFERENCES global_user(id);
ALTER TABLE global_link_user_authority ADD FOREIGN KEY (user_authority_id) REFERENCES global_user_authority(id);

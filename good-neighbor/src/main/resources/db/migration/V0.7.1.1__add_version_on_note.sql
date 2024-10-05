-- Add version on "postit_note" Table to block outdated update

/* ROLLBACK SCRIPT :
 * 
 * ALTER TABLE postit_note DROP COLUMN IF EXISTS version;
 *
 */

ALTER TABLE postit_note
    ADD COLUMN IF NOT EXISTS version bigint NOT NULL DEFAULT 0;
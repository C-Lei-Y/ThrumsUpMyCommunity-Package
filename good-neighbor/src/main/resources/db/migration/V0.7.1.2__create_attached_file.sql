-- Create "postit_attached_file" table and add column in Postit to reference it 

/* ROLLBACK SCRIPT :
 * 
 * ALTER TABLE postit_note DROP COLUMN IF EXISTS attached_file_id;
 * DROP TABLE IF EXISTS postit_attached_file_data;
 * DROP TABLE IF EXISTS postit_attached_file;
 * 
 */

CREATE TABLE IF NOT EXISTS postit_attached_file (
    id bigserial PRIMARY KEY,
    creation_date timestamp NOT NULL,
    update_date timestamp,
    filename varchar(128) NOT NULL,
    size bigint NOT NULL,
    mime_type varchar(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS postit_attached_file_data (
    id bigint PRIMARY KEY references postit_attached_file(id),
    data bytea NOT NULL
);

ALTER TABLE postit_note
    ADD COLUMN IF NOT EXISTS attached_file_id bigint references postit_attached_file(id);
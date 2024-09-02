-- Add column "order_num" in "postit_board"

/* ROLLBACK SCRIPT :
 * 
 * ALTER TABLE postit_board DROP COLUMN order_num;
 * 
 */


ALTER TABLE postit_board ADD COLUMN order_num integer;

UPDATE postit_board SET order_num = id;

-- Create new parameter 'upload.size.max'

/* ROLLBACK SCRIPT :
 * 
 * DELETE FROM global_parameter WHERE name = 'upload.size.max';
 * 
 */

INSERT INTO global_parameter (name, value, client_view) VALUES
    ('upload.size.max', '10485700', true);

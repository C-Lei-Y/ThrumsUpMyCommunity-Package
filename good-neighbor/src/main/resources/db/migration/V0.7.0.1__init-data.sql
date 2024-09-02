-- # GLOBAL # --

INSERT INTO global_parameter (name, value, client_view) VALUES
    ('general.status', 'true', false);

INSERT INTO global_parameter (name, value, client_view) VALUES
    ('like.max', '1000', false);

-- # POST IT # --

INSERT INTO postit_board
(name, creation_date, update_date) VALUES
    ('Io', NOW(), NOW()), ('Europa', NOW(), NOW()),
    ('Ganymede', NOW(), NOW()), ('Callisto', NOW(), NOW());
    
INSERT INTO global_parameter (name, value, client_view) VALUES
    ('board.max', '5', true);

INSERT INTO global_parameter (name, value, client_view) VALUES
    ('note.max', '40', true);

INSERT INTO postit_note
(creation_date, update_date, name, text_value, postit_board_id, update_text_date, color, order_num) VALUES
    (NOW(), NOW(), 'First Note', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 1, NOW(), 'yellow', 1),
    (NOW(), NOW(), 'Io', 'Io (Jupiter I) is the innermost of the four Galilean moons of the planet Jupiter. It is the fourth-largest moon, has the highest density of all the moons.', 1, NOW(), 'green', 2),
    (NOW(), NOW(), 'Europa', 'Europa (Jupiter II) is the smallest of the four Galilean moons orbiting Jupiter, and the sixth-closest to the planet.', 2, NOW(), 'blue', 1),
    (NOW(), NOW(), 'Ganymede', 'Ganymede (Jupiter III) is the largest and most massive moon of Jupiter and in the Solar System.', 3, NOW(), 'orange', 1),
    (NOW(), NOW(), 'Callisto', 'Callisto (Jupiter IV) is the second-largest moon of Jupiter, after Ganymede.', 4, NOW(), 'pink', 1);

-- # GLOBAL # --

INSERT INTO global_parameter (name, value, client_view) VALUES
    ('user.max', '10', true);

INSERT INTO global_user (username, password, enabled) VALUES
    ('admin', '$2a$10$DvPfBzqUbcoiC3wDA8FFP.Tuoz/MV/2T0Dn.bWWAAzoyashi5uh6K', true),
    ('superadmin', '$2a$10$pV/Wt0NnfjDJoOxZ2cqcn.OCj9qd.Xx0yTCs.PVPVs/9nPLlwbj8S', true);

INSERT INTO global_user_authority (authority) VALUES
    ('ROLE_BOARD_WRITE'), ('ROLE_USER_WRITE');

INSERT INTO global_link_user_authority (user_id, user_authority_id) VALUES
    (1, 1), (2, 1), (2, 2);

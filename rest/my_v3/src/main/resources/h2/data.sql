INSERT INTO `user` (`idx`, `id`, `pw`, `create_date`)
VALUES (1, 'admin', '$2a$12$s8lUT6v394zgZHQuaw097OckZQpxAHAyhS.tKRHdTOenCYX/43VR6', now()),
       (2, 'jaybon', '$2a$12$s8lUT6v394zgZHQuaw097OckZQpxAHAyhS.tKRHdTOenCYX/43VR6', now());


INSERT INTO `user_role` (`user_idx`, `role`, `create_date`)
VALUES (1, 'ADMIN', now()),
       (1, 'USER', now()),
       (2, 'USER', now());


INSERT INTO `todo` (`user_idx`, `content`, `done_yn`, `delete_yn`, `create_date`)
VALUES (2, '일어나기', 'Y', 'N', now()),
       (2, '양치하기', 'Y', 'N', now()),
       (2, '샤워하기', 'N', 'N', now()),
       (2, '출근하기', 'N', 'N', now()),
       (2, '퇴근하기', 'N', 'N', now());
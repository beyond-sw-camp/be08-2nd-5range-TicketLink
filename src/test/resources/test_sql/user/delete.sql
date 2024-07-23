set foreign_key_checks = false;


DELETE
FROM tb_user
WHERE userNo in (SELECT userNo
                 FROM tb_user
                 WHERE userNo LIKE 'DM_U_%');

set foreign_key_checks = true;

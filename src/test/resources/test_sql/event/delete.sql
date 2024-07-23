DELETE
FROM tb_event
WHERE eventNo in (SELECT eventNo
                  FROM tb_event
                  WHERE eventNo LIKE 'DM_E_%');

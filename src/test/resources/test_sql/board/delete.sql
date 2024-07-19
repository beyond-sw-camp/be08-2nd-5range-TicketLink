DELETE
FROM tb_board
WHERE boardNo in (SELECT boardNo
                  FROM tb_board
                  WHERE boardNo LIKE 'DM_B_%');
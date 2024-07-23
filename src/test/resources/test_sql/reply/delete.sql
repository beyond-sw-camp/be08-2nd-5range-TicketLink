DELETE
FROM tb_reply
WHERE replyNo in (SELECT replyNo
                 FROM tb_reply
                 WHERE replyNo LIKE 'DM_R_%');

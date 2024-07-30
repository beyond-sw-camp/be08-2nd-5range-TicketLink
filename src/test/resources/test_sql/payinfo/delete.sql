DELETE
FROM tb_payinfo
WHERE payNo IN (SELECT payNo
                FROM tb_payinfo
                WHERE payNo LIKE 'DM_P_%');

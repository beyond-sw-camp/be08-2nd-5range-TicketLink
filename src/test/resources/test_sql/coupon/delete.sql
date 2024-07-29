DELETE
FROM tb_coupon
WHERE couponNo IN (SELECT couponNo
                   FROM tb_coupon
                   WHERE couponNo LIKE 'DM_C_%');

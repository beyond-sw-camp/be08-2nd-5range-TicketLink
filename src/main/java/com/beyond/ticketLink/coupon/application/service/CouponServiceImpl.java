package com.beyond.ticketLink.coupon.application.service;

import com.beyond.ticketLink.autono.persistence.repository.AutoNoRepository;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.coupon.application.domain.Coupon;
import com.beyond.ticketLink.coupon.exception.CouponMessageType;
import com.beyond.ticketLink.coupon.persistence.dto.CouponCreateDto;
import com.beyond.ticketLink.coupon.persistence.dto.CouponUpdateDto;
import com.beyond.ticketLink.coupon.persistence.repository.CouponRepository;
import com.beyond.ticketLink.coupon.ui.requestbody.CouponCreateRequest;
import com.beyond.ticketLink.coupon.ui.requestbody.CouponUpdateRequest;
import com.beyond.ticketLink.user.exception.UserMessageType;
import com.beyond.ticketLink.user.persistence.mariadb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final UserRepository userRepository;

    private final AutoNoRepository autoNoRepository;

    private final CouponRepository couponRepository;


    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    // 쿠폰 생성
    @Override
    @Transactional
    public void createCoupon(CouponCreateRequest request) {

        String couponNo = generateCouponNo();

        String userNo = validateUserNo(request.getUserNo());

        String generatedCode = generateCouponCode();

        CouponCreateDto couponCreateDto = new CouponCreateDto(
                couponNo,
                generatedCode,
                request.getName(),
                request.getDcPercent(),
                LocalDate.now(),
                request.getExpireDate(),
                userNo
        );
        couponRepository.save(couponCreateDto);
    }

    // 쿠폰 번호로 쿠폰 조회
    @Override
    public Optional<Coupon> getCouponByNo(String couponNo) {
        return couponRepository.selectCouponByCouponNo(couponNo);
    }

    // 사용자 번호로 쿠폰 목록 조회
    @Override
    public List<Coupon> getCouponsByUserNo(String userNo, int page, int size) {
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);
        return couponRepository.selectCouponsByUserNo(userNo, rowBounds);
    }

    // 쿠폰 업데이트
    @Override
    @Transactional
    public void updateCoupon(String couponNo, CouponUpdateRequest request) {

        // 유효한 쿠폰에 대한 요청인지 확인
        Coupon coupon = retrieveCoupon(couponNo);

        CouponUpdateDto couponUpdateDto = new CouponUpdateDto(
                coupon.getCouponNo(),
                request.getName(),
                request.getDcPercent(),
                request.getExpireDate()
        );
        couponRepository.updateCoupon(couponUpdateDto);
    }

    // 쿠폰 삭제
    @Override
    @Transactional
    public void deleteCoupon(String couponNo) {

        // 유효한 쿠폰에 대한 요청인지 확인
        Coupon coupon = retrieveCoupon(couponNo);

        couponRepository.deleteCoupon(coupon.getCouponNo());
    }


    private String generateCouponNo() {
        final String TABLE_NAME = "tb_coupon";
        return autoNoRepository.getData(TABLE_NAME);
    }

    private String validateUserNo(String userNo) {
        return userRepository.selectUserByUserNo(userNo)
                .orElseThrow(() -> new TicketLinkException(UserMessageType.USER_NOT_FOUND))
                .getUserNo();
    }

    private Coupon retrieveCoupon(String couponNo) {
        return couponRepository.selectCouponByCouponNo(couponNo)
                .orElseThrow(() -> new TicketLinkException(CouponMessageType.COUPON_NOT_FOUND));
    }

    // 난수로 쿠폰 코드 생성
    private String generateCouponCode() {
        StringBuilder codeBuilder = new StringBuilder(8);
        for (int i = 0; i < 2; i++) {
            codeBuilder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeBuilder.append(random.nextInt(10));
        }
        return codeBuilder.toString();
    }
}

package com.beyond.ticketLink.reservation.application.service;

import com.beyond.ticketLink.autono.persistence.repository.AutoNoRepository;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.coupon.application.domain.Coupon;
import com.beyond.ticketLink.coupon.exception.CouponMessageType;
import com.beyond.ticketLink.coupon.persistence.repository.CouponRepository;
import com.beyond.ticketLink.couponhistory.persistence.dto.CouponUsedHistoryCreateDto;
import com.beyond.ticketLink.couponhistory.persistence.repository.CouponUsedHistoryRepository;
import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.event.persistence.repository.TicketRepository;
import com.beyond.ticketLink.notification.persistence.dto.NotificationDto;
import com.beyond.ticketLink.notification.persistence.repository.NotificationRepository;
import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.application.domain.Reservation;
import com.beyond.ticketLink.reservation.exception.ResMessageType;
import com.beyond.ticketLink.reservation.persistence.dto.PayDto;
import com.beyond.ticketLink.reservation.persistence.repository.PayRepository;
import com.beyond.ticketLink.reservation.persistence.repository.ResRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;

    private final ResRepository resRepository;

    private final AutoNoRepository autoNoRepository;

    private final CouponRepository couponRepository;

    private final TicketRepository ticketRepository;

    private final NotificationRepository notificationRepository;

    private final CouponUsedHistoryRepository couponUsedHistoryRepository;

    @Override
    public List<PayInfo> getList(String userNo) {
        return payRepository.getList(userNo);
    }

    @Override
    public Optional<PayInfo> getData(String payNo) {
        return payRepository.getData(payNo);
    }

    @Override
    @Transactional
    public PayInfo insData(String dayEventNo, PayDto dto, String userNo) {
        // 결제 관련 변수
        PayInfo payInfo = new PayInfo();
        String payNo = autoNoRepository.getData("tb_payinfo");
        // 예약 관련 변수
        Reservation reservation;
        String resNo = null;
        String status = "W";
        // 알림 관련 변수
        NotificationDto notiDto = new NotificationDto();
        String notiMsg = "";

        // 결제 정보 세팅
        payInfo.setPayNo(payNo);
        payInfo.setPayment(dto.getPayment());

        // 결제 수단이 무통장입금(T)이 아닐경우 결제완료(S)로 세팅
        if (dto.getPayment() != 'T') {
            payInfo.setPayDate(LocalDate.now());
            status = "S";
        }

        payInfo.setStatus(status.charAt(0));

        // 쿠폰을 사용했을 경우 할인가 적용하기
        Long price = dto.getPrice();
        Coupon validCoupon = null;
        if (dto.getCouponNo() != null) {
            //log.info("쿠폰 포함");
            String couponNo = dto.getCouponNo();

            // 쿠폰을 조회 및 쿠폰 유효성 검증
            validCoupon = validateCoupon(couponNo, userNo);
            //log.info("유효한 쿠폰 = {}", validCoupon);

            // 할인된 가격 계산
            price = calculateDiscountPrice(validCoupon, price);
            //log.info("쿠폰 적용 가격 = {}", price);
        }

        payInfo.setPrice(price);
        payInfo.setFee(dto.getFee());
        payInfo.setDeliveryCost(dto.getDeliveryCost());
        payInfo.setDiscount(dto.getDiscount());
        payInfo.setTotalAmt(dto.getTotalAmt());
        payInfo.setUserNo(userNo);

        payRepository.insData(payInfo);
        //log.info("payInfo = {}", payInfo);
        if (couponUsed(validCoupon)) {
            // 쿠폰 사용기록 생성
            // log.info("쿠폰 사용기록 생성");
            createCouponUsedHistory(validCoupon, payNo);
        }

        List<Ticket> tickets = dto.getTicketList();
        String ticketNo;

        // 예약 정보 생성 로직
        for (Ticket ticket : tickets) {
            ticketNo = ticket.getTicketNo();

            // 티켓정보가 없는 상시상품일 경우 티켓정보도 동시 생성
            if (ticketNo == null) {
                ticketNo = autoNoRepository.getData("tb_ticket");
                ticket.setTicketNo(ticketNo);
                ticket.setDayEventNo(dayEventNo);

                ticketRepository.insData(ticket);
            }

            // 중복 예약 체크
            int chk = resRepository.getChkRes(ticketNo);

            if (chk > 0) {
                throw new TicketLinkException(ResMessageType.RES_OPERATION_DUPLICATE);
            }

            // 중복이 아닐 경우 예약 정보 세팅
            reservation = new Reservation();
            resNo = autoNoRepository.getData("tb_reservation");
            reservation.setResNo(resNo);

            if (status != null) {
                reservation.setStatus(status.charAt(0));
            }

            reservation.setTicketNo(ticketNo);
            reservation.setPayNo(payNo);

            resRepository.insData(reservation);
        }

        // 알림 정보 생성 로직
        notiDto.setNotiNo(autoNoRepository.getData("tb_notification"));
        payInfo = payRepository.getData(payInfo.getPayNo()).get();

        // 메세지 문구 설정
        if (dto.getPayment() == 'T') {
            notiMsg += "입금 요청\n\n";
        } else {
            notiMsg += "예매 완료\n\n";
        }

        notiMsg += "상품명 : " + payInfo.getReservations().getFirst().getTicket().getDailyEvent().getEvent().getName() + "\n";
        notiMsg += "예매번호 : " + resNo + " [총" + tickets.size() + "장]\n";

        if (dto.getPayment() == 'T') {
            notiMsg += "입금기한 : " + LocalDate.now().plusDays(1) + " (" + LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN)
                    + ") 23시 59분";
        }
        notiDto.setMessage(notiMsg);
        notiDto.setPayNo(payNo);
        notificationRepository.insertNoti(notiDto);

        return payInfo;
    }

    @Override
    @Transactional
    public PayInfo updateData(String payNo) {
        PayInfo payInfo = payRepository.getData(payNo).get();
        List<Reservation> reservations = payInfo.getReservations();
        NotificationDto notiDto = new NotificationDto();

        payRepository.uptData(payNo);
        resRepository.uptData(payNo);

        // 알림 정보 생성 로직
        notiDto.setNotiNo(autoNoRepository.getData("tb_notification"));
        notiDto.setMessage("예매 취소\n\n상품명 : " + reservations.get(0).getTicket().getDailyEvent().getEvent().getName()
                + "\n예약번호 : " + reservations.get(reservations.size() - 1).getResNo());
        notiDto.setPayNo(payNo);
        notificationRepository.insertNoti(notiDto);

        return payRepository.getData(payNo).get();
    }

    private boolean couponUsed(Coupon validCoupon) {
        return validCoupon != null;
    }

    private Coupon validateCoupon(String couponNo, String userNo) {

        Coupon coupon = couponRepository.selectCouponByCouponNo(couponNo)
                .orElseThrow(() -> new TicketLinkException(CouponMessageType.COUPON_NOT_FOUND));

        // 쿠폰 소유여부 확인
        if (invalidCouponOwnership(userNo, coupon)) {
            log.error("쿠폰 소유자 불일치 userNo = {} coupon = {}", userNo, coupon);
            throw new TicketLinkException(CouponMessageType.INVALID_COUPON_OWNERSHIP);
        }

        // 쿠폰 유효기간 체크
        if (couponHasExpired(coupon)) {
            log.error("쿠폰 만료 coupon = {}", coupon);
            throw new TicketLinkException(CouponMessageType.COUPON_HAS_EXPIRED);
        }

        return coupon;
    }


    private Long calculateDiscountPrice(Coupon coupon, Long price) {

        Integer disCountPercentage = coupon.getDcPercent();

        if (disCountPercentage == null) {
            log.error("쿠폰 할인율 데이터 이상 = {}", coupon);
            throw new TicketLinkException(CouponMessageType.INVALID_COUPON_DISCOUNT_PERCENTAGE);
        }
        log.info("price ={}", price);

        int discountAmount = (int) (price * ((double) disCountPercentage / 100));
        log.info("discountPerventage = {}", disCountPercentage);
        log.info("discountAmount = {}", discountAmount);
        price -= discountAmount;
        return price;
    }

    private void createCouponUsedHistory(Coupon coupon, String payNo) {
        String useHistoryNo = autoNoRepository.getData("tb_couponUsedHistory");
        couponUsedHistoryRepository.saveCouponUsedHistory(
                new CouponUsedHistoryCreateDto(
                        useHistoryNo,
                        null,
                        coupon.getCouponNo(),
                        payNo
                )
        );
    }

    private boolean invalidCouponOwnership(String userNo, Coupon coupon) {
        return !userNo.equals(coupon.getUserNo());
    }

    private boolean couponHasExpired(Coupon coupon) {
        return coupon.getExpireDate().isBefore(LocalDate.now());
    }
}
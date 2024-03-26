package com.example.ticketsystem.service;


import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.orders.request.CreateOrderRequest;
import com.example.ticketsystem.dto.orders.request.UpdateOrderRequest;
import com.example.ticketsystem.dto.orders.response.OrderResponse;
import com.example.ticketsystem.dto.voucher.response.VoucherResponse;
import com.example.ticketsystem.entity.Film;
import com.example.ticketsystem.entity.Order;
import com.example.ticketsystem.entity.User;
import com.example.ticketsystem.entity.Voucher;
import com.example.ticketsystem.enums.ResponseCode;
import com.example.ticketsystem.enums.StatusOrder;
import com.example.ticketsystem.exceptions.BusinessException;
import com.example.ticketsystem.repository.FilmRepository;
import com.example.ticketsystem.repository.OrderRepository;
import com.example.ticketsystem.repository.UserRepository;
import com.example.ticketsystem.repository.VoucherRepository;
import com.example.ticketsystem.service.interfaces.OrdersService;
import com.example.ticketsystem.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrdersService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;
    private final FilmRepository filmRepository;

    @Override
    public ApiResponse<OrderResponse> addNew(CreateOrderRequest request) {
        try {

            User user = userRepository.findById(UUID.fromString(request.getUserId())).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            Film film = filmRepository.findById(UUID.fromString(request.getFilmId())).orElseThrow(
                    () -> new BusinessException(ResponseCode.FILM_NOT_FOUND)
            );

            Voucher voucher = null;
            if(StringUtils.hasText(request.getVoucherId())){
                voucher = voucherRepository.findById(UUID.fromString(request.getVoucherId())).orElseThrow(
                        () -> new BusinessException(ResponseCode.VOUCHER_NOT_FOUND)
                );
                if(voucher.isUsed()) {
                    throw new BusinessException(ResponseCode.VOUCHER_USED);
                }
                User userVoucher = voucher.getUser();
                if(Objects.isNull(userVoucher) || !userVoucher.getId().equals(user.getId())) {
                    throw new BusinessException(ResponseCode.VOUCHER_USER_DO_NOT_PERMISSION);
                }
            }
            long discount = Objects.isNull(voucher) ? 0 : voucher.getDiscount();
            Long total = ((long) request.getQuality() * film.getPrice()) *  (100 - discount)/ 100L;

            Order order = new Order()
                    .setFilm(film)
                    .setUser(user)
                    .setVoucher(voucher)
                    .setQuality(request.getQuality())
                    .setTotal(total)
                    .setStatusOrder(StatusOrder.CREATED)
                    .setCreatedAt(LocalDateTime.now());

            order = orderRepository.save(order);

            return new ApiResponse<OrderResponse>().ok(new OrderResponse(order));

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<OrderResponse>> getList() {
        try {

            List<Order> orders = orderRepository.findAll();
            List<OrderResponse> orderResponseList = orders.stream().map(OrderResponse::new).collect(Collectors.toList());
            return new ApiResponse<List<OrderResponse>>().ok(orderResponseList);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<OrderResponse> getDetail(String id) {
        try {

            Order order = orderRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new BusinessException(ResponseCode.ORDERS_NOT_FOUND)
            );
            return new ApiResponse<OrderResponse>().ok(new OrderResponse(order));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<OrderResponse> update(UpdateOrderRequest request) {
        try {


            Order order = orderRepository.findById(UUID.fromString(request.getId())).orElseThrow(
                    () -> new BusinessException(ResponseCode.ORDERS_NOT_FOUND)
            );


            if(StringUtils.hasText(request.getVoucherId())){
                Voucher voucher = voucherRepository.findById(UUID.fromString(request.getId())).orElseThrow(
                        () -> new BusinessException(ResponseCode.VOUCHER_NOT_FOUND)
                );

                if(voucher.isUsed()) {
                    throw new BusinessException(ResponseCode.VOUCHER_USED);
                }
                User userVoucher = voucher.getUser();
                if(Objects.isNull(userVoucher) || !userVoucher.getId().equals(order.getUser().getId())) {
                    throw new BusinessException(ResponseCode.VOUCHER_USER_DO_NOT_PERMISSION);
                }
                order.setVoucher(voucher);
            }

            if(!Objects.equals(order.getQuality(), request.getQuality())) {
                order.setQuality(request.getQuality());
            }

            int dis = order.getVoucher().getDiscount();
            int price = order.getFilm().getPrice();
            Long total = ((long) order.getQuality() * price) *  (100 - dis )/ 100L;
            order.setTotal(total);
            orderRepository.save(order);
            return new ApiResponse<OrderResponse>().ok(new OrderResponse(order));

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<CommonStatusResponse> delete(String id) {
        try {

            Order order = orderRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new BusinessException(ResponseCode.ORDERS_NOT_FOUND)
            );

            orderRepository.delete(order);
            return new ApiResponse<CommonStatusResponse>().ok(new CommonStatusResponse(true));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.example.ticketsystem.service;


import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.orders.request.CallbackPayment;
import com.example.ticketsystem.dto.orders.request.CreateOrderRequest;
import com.example.ticketsystem.dto.orders.request.PaymentOrderRequest;
import com.example.ticketsystem.dto.orders.request.UpdateOrderRequest;
import com.example.ticketsystem.dto.orders.response.CallbackResponse;
import com.example.ticketsystem.dto.orders.response.OrderResponse;
import com.example.ticketsystem.dto.orders.response.PaymentOrderResponse;
import com.example.ticketsystem.entity.*;
import com.example.ticketsystem.enums.Method;
import com.example.ticketsystem.enums.ResponseCode;
import com.example.ticketsystem.enums.StatusOrder;
import com.example.ticketsystem.exceptions.BusinessException;
import com.example.ticketsystem.repository.*;
import com.example.ticketsystem.service.interfaces.OrdersService;
import com.example.ticketsystem.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrdersService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;
    private final FilmRepository filmRepository;
    private final TransactionRepository transactionRepository;


    @Value("${vnpay.website}")
    private String websiteCode;

    @Value("${vnpay.host}")
    private String hostUrl;

    @Value("${vnpay.secret}")
    private String secret;

    @Value("${vnpay.return}")
    private String urlReturn;

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
            if (StringUtils.hasText(request.getVoucherId())) {
                voucher = voucherRepository.findById(UUID.fromString(request.getVoucherId())).orElseThrow(
                        () -> new BusinessException(ResponseCode.VOUCHER_NOT_FOUND)
                );
                if (voucher.isUsed()) {
                    throw new BusinessException(ResponseCode.VOUCHER_USED);
                }
                User userVoucher = voucher.getUser();
                if (Objects.isNull(userVoucher) || !userVoucher.getId().equals(user.getId())) {
                    throw new BusinessException(ResponseCode.VOUCHER_USER_DO_NOT_PERMISSION);
                }
            }
            long discount = Objects.isNull(voucher) ? 0 : voucher.getDiscount();
            Long total = ((long) request.getQuality() * film.getPrice()) * (100 - discount) / 100L;

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


            if (StringUtils.hasText(request.getVoucherId())) {
                Voucher voucher = voucherRepository.findById(UUID.fromString(request.getId())).orElseThrow(
                        () -> new BusinessException(ResponseCode.VOUCHER_NOT_FOUND)
                );

                if (voucher.isUsed()) {
                    throw new BusinessException(ResponseCode.VOUCHER_USED);
                }
                User userVoucher = voucher.getUser();
                if (Objects.isNull(userVoucher) || !userVoucher.getId().equals(order.getUser().getId())) {
                    throw new BusinessException(ResponseCode.VOUCHER_USER_DO_NOT_PERMISSION);
                }
                order.setVoucher(voucher);
            }

            if (!Objects.equals(order.getQuality(), request.getQuality())) {
                order.setQuality(request.getQuality());
            }

            int dis = Objects.isNull(order.getVoucher()) ? 0 : order.getVoucher().getDiscount();
            int price = order.getFilm().getPrice();
            Long total = ((long) order.getQuality() * price) * (100 - dis) / 100L;
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


    @Override
    public ApiResponse<PaymentOrderResponse> payment(PaymentOrderRequest request) {
        try {

            Order order = orderRepository.findById(UUID.fromString(request.getOrderId())).orElseThrow(
                    () -> new BusinessException(ResponseCode.ORDERS_NOT_FOUND)
            );

            if (!StatusOrder.CREATED.equals(order.getStatusOrder()) && !StatusOrder.FAILED.equals(order.getStatusOrder())) {
                throw new BusinessException(ResponseCode.ORDERS_PROCESS_INVALID);
            }


            // todo: Build vnpay link

            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String vnp_OrderInfo = "Thanh toan cho don hang: " + order.getId().toString();
            String orderType = "190001";
            String vnp_TxnRef = order.getId().toString();
            String vnp_IpAddr = "127.0.0.1";
            String vnp_TmnCode = websiteCode;

            int amount = order.getTotal().intValue() * 100;
            Map vnp_Params = new HashMap();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            String bank_code = "";
            if (bank_code != null && !bank_code.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bank_code);
            }
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
            vnp_Params.put("vnp_OrderType", orderType);

            String locate = "";
            if (locate != null && !locate.isEmpty()) {
                vnp_Params.put("vnp_Locale", locate);
            } else {
                vnp_Params.put("vnp_Locale", "vn");
            }
            vnp_Params.put("vnp_ReturnUrl", urlReturn);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());

            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            //Add Params of 2.1.0 Version
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            //Billing
//            vnp_Params.put("vnp_Bill_Mobile", req.getParameter("txt_billing_mobile"));
//            vnp_Params.put("vnp_Bill_Email", req.getParameter("txt_billing_email"));
//            String fullName = (req.getParameter("txt_billing_fullname")).trim();
//            if (fullName != null && !fullName.isEmpty()) {
//                int idx = fullName.indexOf(' ');
//                String firstName = fullName.substring(0, idx);
//                String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
//                vnp_Params.put("vnp_Bill_FirstName", firstName);
//                vnp_Params.put("vnp_Bill_LastName", lastName);
//
//            }
//            vnp_Params.put("vnp_Bill_Address", req.getParameter("txt_inv_addr1"));
//            vnp_Params.put("vnp_Bill_City", req.getParameter("txt_bill_city"));
//            vnp_Params.put("vnp_Bill_Country", req.getParameter("txt_bill_country"));
//            if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state").isEmpty()) {
//                vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
//            }
//            // Invoice
//            vnp_Params.put("vnp_Inv_Phone", req.getParameter("txt_inv_mobile"));
//            vnp_Params.put("vnp_Inv_Email", req.getParameter("txt_inv_email"));
//            vnp_Params.put("vnp_Inv_Customer", req.getParameter("txt_inv_customer"));
//            vnp_Params.put("vnp_Inv_Address", req.getParameter("txt_inv_addr1"));
//            vnp_Params.put("vnp_Inv_Company", req.getParameter("txt_inv_company"));
//            vnp_Params.put("vnp_Inv_Taxcode", req.getParameter("txt_inv_taxcode"));
//            vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
            //Build data to hash and querystring
            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = CommonUtils.getSecureHash(secret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash.toLowerCase();
            String paymentUrl = hostUrl + "?" + queryUrl;

            order.setStatusOrder(StatusOrder.PAID);
            orderRepository.save(order);

            return new ApiResponse<PaymentOrderResponse>().ok(new PaymentOrderResponse(paymentUrl));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public CallbackResponse callback(CallbackPayment request) {
        try {

            Order order = orderRepository.findById(UUID.fromString(request.getVnp_TxnRef())).orElse(null);
            if (Objects.isNull(order)) {
                return new CallbackResponse("01", "Order not found");
            }

            int pricePayment = (order.getTotal().intValue() * 100) - Integer.parseInt(request.getVnp_Amount());
            if (pricePayment != 0) {
                return new CallbackResponse("04", "Invalid Amount");
            }


            if (StatusOrder.COMPLETED.equals(order.getStatusOrder())) {
                return new CallbackResponse("02", "Order already confirmed");
            }


            if (!request.getVnp_TransactionStatus().equals("00")) {
                return new CallbackResponse("99", "Unknow error");
            }

            Transaction transaction = new Transaction()
                    .setOrder(order)
                    .setUser(order.getUser())
                    .setMethod(Objects.equals("ATM", request.getVnp_CardType()) ? Method.CARD : Method.QR)
                    .setCreatedAt(LocalDateTime.now())
                    .setTransactionIdVnp(request.getVnp_TransactionNo());

            transaction = transactionRepository.save(transaction);
            order.setTransaction(transaction);
            order.setStatusOrder(StatusOrder.COMPLETED);
            orderRepository.save(order);


            return new CallbackResponse("00", "Confirm Success");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return new CallbackResponse("99", "Unknow error");
        }
    }
}

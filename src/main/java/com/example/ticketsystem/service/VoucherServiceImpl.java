package com.example.ticketsystem.service;

import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.voucher.request.CreateVoucherRequest;
import com.example.ticketsystem.dto.voucher.request.GiveVoucherRequest;
import com.example.ticketsystem.dto.voucher.request.UpdateVoucherRequest;
import com.example.ticketsystem.dto.voucher.response.VoucherResponse;
import com.example.ticketsystem.entity.User;
import com.example.ticketsystem.entity.Voucher;
import com.example.ticketsystem.enums.ResponseCode;
import com.example.ticketsystem.exceptions.BusinessException;
import com.example.ticketsystem.repository.UserRepository;
import com.example.ticketsystem.repository.VoucherRepository;
import com.example.ticketsystem.service.interfaces.VoucherService;
import com.example.ticketsystem.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<VoucherResponse> addNew(CreateVoucherRequest request) {
        try {

            Voucher voucher = new Voucher()
                    .setName(request.getName())
                    .setExpired(CommonUtils.parseDateTime(request.getExpiredDate()))
                    .setCreatedAt(LocalDateTime.now())
                    .setStart(CommonUtils.parseDateTime(request.getStartDate()))
                    .setDiscount(request.getDiscount())
                    .setUser(null)
                    .setCode(CommonUtils.getCodeVoucher())
                    .setUsed(false)
                    .setOrder(null);
            voucher = voucherRepository.save(voucher);
            return new ApiResponse<VoucherResponse>().ok(new VoucherResponse(voucher));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<VoucherResponse>> getList() {
        try {

            List<Voucher> vouchers = voucherRepository.findAll();
            List<VoucherResponse> voucherResponseList = vouchers.stream().map(VoucherResponse::new).collect(Collectors.toList());
            return new ApiResponse<List<VoucherResponse>>().ok(voucherResponseList);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<VoucherResponse> getDetail(String id) {
        try {

            Voucher voucher = voucherRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new BusinessException(ResponseCode.VOUCHER_NOT_FOUND)
            );
            return new ApiResponse<VoucherResponse>().ok(new VoucherResponse(voucher));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<VoucherResponse> update(UpdateVoucherRequest request) {
        try {

            Voucher voucher = voucherRepository.findById(UUID.fromString(request.getId())).orElseThrow(
                    () -> new BusinessException(ResponseCode.VOUCHER_NOT_FOUND)
            );

            if (StringUtils.hasText(request.getName())) {
                voucher.setName(request.getName());
            }

            if (StringUtils.hasText(request.getExpiredDate())) {
                voucher.setExpired(CommonUtils.parseDateTime(request.getExpiredDate()));
            }

            if (StringUtils.hasText(request.getStartDate())) {
                voucher.setStart(CommonUtils.parseDateTime(request.getStartDate()));
            }

            if (Objects.nonNull(request.getDiscount()) && request.getDiscount() > 0) {
                voucher.setDiscount(request.getDiscount());
            }

            voucher = voucherRepository.save(voucher);
            return new ApiResponse<VoucherResponse>().ok(new VoucherResponse(voucher));
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

            Voucher voucher = voucherRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new BusinessException(ResponseCode.VOUCHER_NOT_FOUND)
            );
            voucherRepository.delete(voucher);
            return new ApiResponse<CommonStatusResponse>().ok(new CommonStatusResponse(true));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ApiResponse<VoucherResponse> giveVoucher(GiveVoucherRequest request) {
        try {

            Voucher voucher = voucherRepository.findById(UUID.fromString(request.getId())).orElseThrow(
                    () -> new BusinessException(ResponseCode.VOUCHER_NOT_FOUND)
            );

            User user = userRepository.findById(UUID.fromString(request.getUserId())).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            voucher.setUser(user);
            voucher = voucherRepository.save(voucher);
            return new ApiResponse<VoucherResponse>().ok(new VoucherResponse(voucher));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}

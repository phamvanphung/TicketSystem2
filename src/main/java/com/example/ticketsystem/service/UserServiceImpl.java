package com.example.ticketsystem.service;

import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.user.request.UserRegisterRequest;
import com.example.ticketsystem.dto.user.request.UserUpdateRequest;
import com.example.ticketsystem.dto.user.request.UserVerifyRequest;
import com.example.ticketsystem.dto.user.response.UserResponse;
import com.example.ticketsystem.entity.User;
import com.example.ticketsystem.enums.ResponseCode;
import com.example.ticketsystem.enums.Role;
import com.example.ticketsystem.exceptions.BusinessException;
import com.example.ticketsystem.mail.dto.MailData;
import com.example.ticketsystem.mail.service.EmailService;
import com.example.ticketsystem.repository.UserRepository;
import com.example.ticketsystem.service.interfaces.UserService;
import com.example.ticketsystem.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private EmailService mailService;



    @Override
    public ApiResponse<UserResponse> register(UserRegisterRequest request) {
        try {
            User user = userRepository.findUserByEmail(request.getEmail()).orElse(null);
            if(Objects.nonNull(user)){
                throw new BusinessException(ResponseCode.USER_EMAIL_EXISTED);
            }
            user = new User();
            user.setEmail(request.getEmail());
            user.setAddress(request.getAddress());
            user.setCreatedAt(LocalDateTime.now());
            user.setFullname(request.getFullname());
            user.setGender(request.isGender());
            user.setRole(Role.USER);
            user.setPassword(request.getPassword());
            user.setPhone(request.getPhone());
            user.setDob(CommonUtils.parseDateTime(request.getDob()));
            user.setInactive(true);
            String otp = CommonUtils.getOtp();
            user.setOtp(otp);

            user = userRepository.save(user);


            // TODO:send email to verify

            log.info("Begin to send email");
            MailData mailData = new MailData();
            Map<String,Object> map = new HashMap<>();
            map.put("name",user.getFullname());
            map.put("otp", otp);


            mailData.setSubject("OTP to verify account!");
            mailData.setTemplateName("otptemplate.html");
            mailData.setTo(user.getEmail());
            mailData.setPros(map);
            mailData.setFrom(from);
            mailService.sendEmail(mailData);
            //
            return new ApiResponse<UserResponse>().ok(new UserResponse(user));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<UserResponse> getInfo(String id) {
        try {
            User user = userRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );
            return new ApiResponse<UserResponse>().ok(new UserResponse(user));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<UserResponse> update(UserUpdateRequest request) {
        try {
            User user = userRepository.findById(UUID.fromString(request.getId())).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            if(StringUtils.hasText(request.getFullname())){
                user.setFullname(request.getFullname());
            }

            if(StringUtils.hasText(request.getPhone())){
                user.setPhone(request.getPhone());
            }


            if(StringUtils.hasText(request.getAddress())){
                user.setAddress(request.getAddress());
            }

            if(StringUtils.hasText(request.getDob())){
                user.setDob(CommonUtils.parseDateTime(request.getDob()));
            }

            if(Objects.nonNull(request.getGender())){
                user.setGender(request.getGender());
            }
            user = userRepository.save(user);
            return new ApiResponse<UserResponse>().ok(new UserResponse(user));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<CommonStatusResponse> changeStatus(String id) {
        try {
            User user = userRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            user.setInactive(!user.isInactive());
            user = userRepository.save(user);
            return new ApiResponse<CommonStatusResponse>().ok(new CommonStatusResponse(true));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ApiResponse<CommonStatusResponse> verifyOtpRegister(UserVerifyRequest request) {
        try {
            User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            if(!Objects.equals(user.getOtp(), request.getOtp())) {
                throw new BusinessException(ResponseCode.USER_OTP_NOT_MATCH);
            }
            user.setOtp("");
            user.setInactive(false);
            user = userRepository.save(user);
            return new ApiResponse<CommonStatusResponse>().ok(new CommonStatusResponse(true));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.api.member.domain.phoneAuthentication.service

import com.api.member.domain.phoneAuthentication.domain.PhoneNumberAuthenticationRepository
import com.api.member.domain.phoneAuthentication.dto.AuthenticationCodeAuthenticateRequestDto
import com.api.member.domain.phoneAuthentication.dto.AuthenticationCodeRequestDto
import com.api.member.domain.phoneAuthentication.dto.AuthenticationCodeResponseDto
import com.api.member.domain.phoneAuthentication.exception.AuthenticationExpiredDateException
import com.api.member.domain.phoneAuthentication.exception.AuthenticationInvalidCodeException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service

class PhoneNumberAuthenticationService(
    private val phoneNumberAuthenticationRepository: PhoneNumberAuthenticationRepository,
) {

    @Value("\${sms.authKeyLength}")
    private val smsAuthKeyLength = 0

    // 인증코드 발송
    @Transactional
    fun request(requestDto: AuthenticationCodeRequestDto): AuthenticationCodeResponseDto {

        // 인증 코드 발급
        val authenticationCode = randomGenerate(smsAuthKeyLength)
        // 인증가능시간 3분
        val expireDate = LocalDateTime.now().plusMinutes(3)

        val phoneNumberAuthentication = phoneNumberAuthenticationRepository.save(
            requestDto.toEntity(authenticationCode, expireDate)
        )

        return AuthenticationCodeResponseDto(phoneNumberAuthentication)
    }

    // 휴대폰번호 인증
    @Transactional
    fun authenticate(requestDto: AuthenticationCodeAuthenticateRequestDto) {
        val phoneNumber = requestDto.phoneNumber
        val authenticationCode = requestDto.authenticationCode

        // 인증 여부 확인
        val phoneNumberAuthentication = phoneNumberAuthenticationRepository
            .findFirstByPhoneNumberAndAuthenticationCodeAndIsAuthenticatedOrderByIdDesc(
                phoneNumber,
                authenticationCode,
                false
            ) ?: throw AuthenticationInvalidCodeException(phoneNumber)

        // 인증 유효시간
        val expiredDate = phoneNumberAuthentication.expiredDate

        // 만료 체크
        if(expiredDate?.isBefore(LocalDateTime.now()) == true){
            throw AuthenticationExpiredDateException(phoneNumber)
        }

        // 인증 완료 처리
        phoneNumberAuthentication.complete()
    }

    // 인증코드 랜덤 생성
    fun randomGenerate(len: Int = 6): String {
        val valRange = 0..9 // 1
        var result = ""

        for(i in 0 until len){
            result += valRange.random().toString()
        }

        return result;
    }
}
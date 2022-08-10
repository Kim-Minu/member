package com.api.member.domain.phoneAuthentication.dto

import com.api.member.domain.phoneAuthentication.domain.PhoneNumberAuthentication
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class AuthenticationCodeRequestDto(
    @field:Pattern(regexp = "010\\d{4}\\d{4}", message = "올바른 형식의 전화번호여야 합니다")
    @field:NotBlank
    val phoneNumber: String,
) {
    fun toEntity(authenticationCode: String, expiredDate: LocalDateTime): PhoneNumberAuthentication {
        return PhoneNumberAuthentication(
            phoneNumber = phoneNumber,
            authenticationCode = authenticationCode,
            expiredDate = expiredDate
        )
    }
}

class AuthenticationCodeAuthenticateRequestDto(
    @field:NotBlank
    val authenticationCode: String,

    @field:Pattern(regexp = "010\\d{4}\\d{4}", message = "올바른 형식의 전화번호여야 합니다")
    @field:NotBlank
    val phoneNumber: String
)
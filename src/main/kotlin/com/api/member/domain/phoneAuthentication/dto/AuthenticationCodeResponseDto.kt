package com.api.member.domain.phoneAuthentication.dto

import com.api.member.domain.phoneAuthentication.domain.PhoneNumberAuthentication
import java.time.LocalDateTime
import java.util.*

data class AuthenticationCodeResponseDto(
    val authenticationCode: String,
    val phoneNumber: String,
    val expiredDate: LocalDateTime?
) {
    constructor(phoneNumberAuthentication : PhoneNumberAuthentication): this (
        phoneNumberAuthentication.authenticationCode,
        phoneNumberAuthentication.phoneNumber,
        phoneNumberAuthentication.expiredDate
    )
}

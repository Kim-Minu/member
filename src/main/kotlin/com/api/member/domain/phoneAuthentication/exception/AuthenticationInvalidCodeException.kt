package com.api.member.domain.phoneAuthentication.exception

import com.api.member.global.exception.ErrorCode
import com.api.member.global.exception.InvalidValueException

class AuthenticationInvalidCodeException(phoneNumber: String): InvalidValueException(
    phoneNumber,
    ErrorCode.AUTH_INVALID
)